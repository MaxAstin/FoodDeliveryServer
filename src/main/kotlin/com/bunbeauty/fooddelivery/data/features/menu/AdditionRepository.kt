package com.bunbeauty.fooddelivery.data.features.menu

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.company.CompanyEntity
import com.bunbeauty.fooddelivery.data.entity.menu.*
import com.bunbeauty.fooddelivery.data.features.menu.mapper.mapMenuProductEntity
import com.bunbeauty.fooddelivery.data.features.menu.mapper.mapToAddition
import com.bunbeauty.fooddelivery.data.features.menu.mapper.mapToAdditionGroup
import com.bunbeauty.fooddelivery.data.table.menu.AdditionGroupTable
import com.bunbeauty.fooddelivery.data.table.menu.AdditionTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductToAdditionGroupTable
import com.bunbeauty.fooddelivery.data.table.menu.MenuProductToAdditionGroupToAdditionTable
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.*
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.MenuProduct
import com.bunbeauty.fooddelivery.domain.toUuid
import org.jetbrains.exposed.sql.and
import java.util.*

class AdditionRepository {

    suspend fun insertAdditionGroup(insertAdditionGroup: InsertAdditionGroup) = query {
        val additionGroupEntity = AdditionGroupEntity.new {
            name = insertAdditionGroup.groupName
            singleChoice = insertAdditionGroup.singleChoice
            priority = insertAdditionGroup.groupPriority
            isVisible = true
            company = CompanyEntity[insertAdditionGroup.companyUuid]
        }
        insertAdditionGroup.menuProductUuids.map { menuProductUuid ->
            MenuProductWithAdditionGroupEntity.new {
                menuProduct = MenuProductEntity[menuProductUuid]
                additionGroup = additionGroupEntity
            }
        }.forEach { menuProductWithAdditionGroupEntity ->
            insertAdditionGroup.additionUuids.forEach { additionUuid ->
                MenuProductWithAdditionGroupWithAdditionEntity.new {
                    menuProductWithAdditionGroup = menuProductWithAdditionGroupEntity
                    addition = AdditionEntity[additionUuid]
                    isSelected = false
                    isVisible = true
                }
            }
        }
    }

    suspend fun getAdditionGroupListByCompanyUuid(companyUuid: UUID): List<AdditionGroup> = query {
        AdditionGroupEntity.find {
            AdditionGroupTable.company eq companyUuid
        }.map(mapToAdditionGroup)
    }

    suspend fun getAdditionGroupByUuid(uuid: UUID): AdditionGroup? = query {
        AdditionGroupEntity.findById(uuid)?.mapToAdditionGroup()
    }

    suspend fun insertAddition(insertAddition: InsertAddition): Addition = query {
        AdditionEntity.new {
            name = insertAddition.name
            fullName = insertAddition.fullName
            price = insertAddition.price
            photoLink = insertAddition.photoLink
            priority = insertAddition.priority
            isVisible = insertAddition.isVisible
            company = CompanyEntity[insertAddition.companyUuid]
        }.mapToAddition()
    }

    suspend fun insertAdditionToGroup(insertAdditionToGroup: InsertAdditionToGroup): List<MenuProduct> = query {
        MenuProductWithAdditionGroupEntity.find {
            MenuProductToAdditionGroupTable.additionGroup eq insertAdditionToGroup.additionGroupUuid
        }.map { menuProductWithAdditionGroupEntity ->
            val menuProductWithAdditionGroupWithAdditionEntity = MenuProductWithAdditionGroupWithAdditionEntity.find {
                (MenuProductToAdditionGroupToAdditionTable.menuProductToAdditionGroup eq
                        menuProductWithAdditionGroupEntity.uuid.toUuid()) and
                        (MenuProductToAdditionGroupToAdditionTable.addition eq
                                insertAdditionToGroup.additionUuid)
            }

            if (menuProductWithAdditionGroupWithAdditionEntity.empty()) {
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

    suspend fun getAdditionByUuid(uuid: UUID): Addition? = query {
        AdditionEntity.findById(uuid)?.mapToAddition()
    }

    suspend fun getAdditionListByCompanyUuid(companyUuid: UUID): List<Addition> = query {
        AdditionEntity.find {
            AdditionTable.company eq companyUuid
        }.map(mapToAddition)
    }

}