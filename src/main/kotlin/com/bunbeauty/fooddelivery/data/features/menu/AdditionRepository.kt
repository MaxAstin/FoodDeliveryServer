package com.bunbeauty.fooddelivery.data.features.menu

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.menu.AdditionEntity
import com.bunbeauty.fooddelivery.data.entity.menu.AdditionGroupEntity
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductEntity
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductWithAdditionGroupEntity
import com.bunbeauty.fooddelivery.data.entity.menu.MenuProductWithAdditionGroupWithAdditionEntity
import com.bunbeauty.fooddelivery.data.features.menu.cache.MenuProductCatch
import com.bunbeauty.fooddelivery.data.features.menu.mapper.mapMenuProductEntity
import com.bunbeauty.fooddelivery.data.features.menu.mapper.mapToAdditionGroup
import com.bunbeauty.fooddelivery.data.features.menu.mapper.toAddition
import com.bunbeauty.fooddelivery.data.table.menu.AdditionGroupTable
import com.bunbeauty.fooddelivery.data.table.menu.AdditionTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductToAdditionGroupTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductToAdditionGroupToAdditionTable
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.Addition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.AdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.InsertAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.InsertAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.InsertAdditionGroupToMenuProducts
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.InsertAdditionToGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.UpdateAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.UpdateAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.MenuProduct
import com.bunbeauty.fooddelivery.domain.toUuid
import org.jetbrains.exposed.sql.and
import java.util.UUID

class AdditionRepository(
    private val menuProductCatch: MenuProductCatch
) {

    suspend fun insertAdditionGroup(insertAdditionGroup: InsertAdditionGroup): AdditionGroup {
        return query {
            AdditionGroupEntity.new {
                name = insertAdditionGroup.name
                singleChoice = insertAdditionGroup.singleChoice
                priority = insertAdditionGroup.priority
                isVisible = insertAdditionGroup.isVisible
                company = CompanyEntity[insertAdditionGroup.companyUuid]
            }.mapToAdditionGroup()
        }
    }

    suspend fun insertAdditionGroupToMenuProducts(insertAdditionGroupToMenuProducts: InsertAdditionGroupToMenuProducts) {
        query {
            insertAdditionGroupToMenuProducts.menuProductUuids.map { menuProductUuid ->
                MenuProductWithAdditionGroupEntity.find {
                    (MenuProductToAdditionGroupTable.menuProduct eq menuProductUuid) and
                        (MenuProductToAdditionGroupTable.additionGroup eq insertAdditionGroupToMenuProducts.additionGroupUuid)
                }.firstOrNull()
                    ?: MenuProductWithAdditionGroupEntity.new {
                        menuProduct = MenuProductEntity[menuProductUuid]
                        additionGroup = AdditionGroupEntity[insertAdditionGroupToMenuProducts.additionGroupUuid]
                    }
            }.forEach { menuProductWithAdditionGroupEntity ->
                val companyUuid = menuProductWithAdditionGroupEntity.menuProduct.company.id.value
                menuProductCatch.clearCache(key = companyUuid)

                insertAdditionGroupToMenuProducts.additionUuids.forEach { additionUuid ->
                    MenuProductWithAdditionGroupWithAdditionEntity.new {
                        menuProductWithAdditionGroup = menuProductWithAdditionGroupEntity
                        addition = AdditionEntity[additionUuid]
                        isSelected = false
                        isVisible = true
                    }
                }
            }
        }
    }

    suspend fun getAdditionGroupListByCompanyUuid(companyUuid: UUID): List<AdditionGroup> {
        return query {
            AdditionGroupEntity.find {
                AdditionGroupTable.company eq companyUuid
            }.map(mapToAdditionGroup)
        }
    }

    suspend fun getAdditionGroupByUuid(uuid: UUID): AdditionGroup? {
        return query {
            AdditionGroupEntity.findById(uuid)?.mapToAdditionGroup()
        }
    }

    suspend fun getAdditionGroupByName(name: String, companyUuid: UUID): AdditionGroup? {
        return query {
            AdditionGroupEntity.find {
                (AdditionGroupTable.name eq name) and
                    (AdditionGroupTable.company eq companyUuid)
            }.firstOrNull()?.mapToAdditionGroup()
        }
    }

    suspend fun updateAdditionGroup(
        additionGroupUuid: UUID,
        updateAdditionGroup: UpdateAdditionGroup
    ): AdditionGroup? {
        return query {
            AdditionGroupEntity.findById(additionGroupUuid)
                ?.apply {
                    name = updateAdditionGroup.name ?: name
                    singleChoice = updateAdditionGroup.singleChoice ?: singleChoice
                    priority = updateAdditionGroup.priority ?: priority
                    isVisible = updateAdditionGroup.isVisible ?: isVisible
                }?.mapToAdditionGroup()
                ?.also { additionGroup ->
                    val companyUuid = additionGroup.companyUuid.toUuid()
                    menuProductCatch.clearCache(key = companyUuid)
                }
        }
    }

    suspend fun insertAddition(insertAddition: InsertAddition): Addition {
        return query {
            AdditionEntity.new {
                name = insertAddition.name
                fullName = insertAddition.fullName
                price = insertAddition.price
                photoLink = insertAddition.photoLink
                tag = insertAddition.tag
                priority = insertAddition.priority
                isVisible = insertAddition.isVisible
                company = CompanyEntity[insertAddition.companyUuid]
            }.toAddition()
        }
    }

    suspend fun insertAdditionToGroup(insertAdditionToGroup: InsertAdditionToGroup): List<MenuProduct> {
        return query {
            MenuProductWithAdditionGroupEntity.find {
                MenuProductToAdditionGroupTable.additionGroup eq insertAdditionToGroup.additionGroupUuid
            }.map { menuProductWithAdditionGroupEntity ->
                val menuProductWithAdditionGroupWithAdditionEntity =
                    MenuProductWithAdditionGroupWithAdditionEntity.find {
                        (
                            MenuProductToAdditionGroupToAdditionTable.menuProductToAdditionGroup eq
                                menuProductWithAdditionGroupEntity.uuid.toUuid()
                            ) and
                            (
                                MenuProductToAdditionGroupToAdditionTable.addition eq
                                    insertAdditionToGroup.additionUuid
                                )
                    }

                if (menuProductWithAdditionGroupWithAdditionEntity.empty()) {
                    val companyUuid = menuProductWithAdditionGroupEntity.menuProduct.company.id.value
                    menuProductCatch.clearCache(key = companyUuid)

                    MenuProductWithAdditionGroupWithAdditionEntity.new {
                        menuProductWithAdditionGroup = menuProductWithAdditionGroupEntity
                        addition = AdditionEntity[insertAdditionToGroup.additionUuid]
                        isSelected = insertAdditionToGroup.isSelected
                        isVisible = insertAdditionToGroup.isVisible
                    }
                }

                menuProductWithAdditionGroupEntity.menuProduct.mapMenuProductEntity()
            }
        }
    }

    suspend fun getAdditionByUuid(uuid: UUID): Addition? {
        return query {
            AdditionEntity.findById(uuid)?.toAddition()
        }
    }

    suspend fun getAdditionByNameAndTag(
        name: String,
        tag: String?,
        companyUuid: UUID
    ): Addition? {
        return query {
            AdditionEntity.find {
                (AdditionTable.name eq name) and
                    (AdditionTable.tag eq tag) and
                    (AdditionTable.company eq companyUuid)
            }.firstOrNull()
                ?.toAddition()
        }
    }

    suspend fun getAdditionListByCompanyUuid(companyUuid: UUID): List<Addition> {
        return query {
            AdditionEntity.find {
                AdditionTable.company eq companyUuid
            }.map(AdditionEntity::toAddition)
        }
    }

    suspend fun updateAddition(
        additionUuid: UUID,
        updateAddition: UpdateAddition
    ): Addition? {
        return query {
            AdditionEntity.findById(additionUuid)
                ?.apply {
                    name = updateAddition.name ?: name
                    fullName = (updateAddition.fullName ?: fullName)?.takeIf { fullName ->
                        fullName.isNotEmpty()
                    }
                    price = (updateAddition.price ?: price)?.takeIf { price ->
                        price != 0
                    }
                    photoLink = updateAddition.photoLink ?: photoLink
                    priority = updateAddition.priority ?: priority
                    tag = updateAddition.tag ?: tag
                    isVisible = updateAddition.isVisible ?: isVisible
                }?.toAddition()
                ?.also { addition ->
                    val companyUuid = addition.companyUuid.toUuid()
                    menuProductCatch.clearCache(key = companyUuid)
                }
        }
    }
}
