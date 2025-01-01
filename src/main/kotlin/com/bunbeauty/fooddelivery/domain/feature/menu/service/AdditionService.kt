package com.bunbeauty.fooddelivery.domain.feature.menu.service

import com.bunbeauty.fooddelivery.data.features.menu.AdditionRepository
import com.bunbeauty.fooddelivery.data.features.menu.MenuProductRepository
import com.bunbeauty.fooddelivery.data.features.user.UserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUserUuidError
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.mapAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.mapMenuProduct
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.mapPatchAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.mapPostAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.mapPostAdditionGroupToMenuProducts
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.mapPostAdditionToGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.toGetAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.toInsertAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.toUpdateAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.Addition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.GetAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.GetAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PatchAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PatchAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PostAddition
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PostAdditionGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PostAdditionGroupToMenuProducts
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.PostAdditionToGroup
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.GetMenuProduct
import com.bunbeauty.fooddelivery.domain.toUuid

class AdditionService(
    private val userRepository: UserRepository,
    private val additionRepository: AdditionRepository,
    private val menuProductRepository: MenuProductRepository
) {

    suspend fun createAdditionGroup(postAdditionGroup: PostAdditionGroup, creatorUuid: String): GetAdditionGroup {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid
            .toUuid()

        val additionGroup = additionRepository.getAdditionGroupByName(
            name = postAdditionGroup.name,
            companyUuid = companyUuid
        )
        if (additionGroup != null) {
            additionGroupAlreadyExistsError(name = postAdditionGroup.name)
        }

        return additionRepository.insertAdditionGroup(
            insertAdditionGroup = postAdditionGroup.mapPostAdditionGroup(companyUuid)
        ).mapAdditionGroup()
    }

    suspend fun addAdditionGroupToMenuProducts(
        postAdditionGroupToMenuProducts: PostAdditionGroupToMenuProducts,
        creatorUuid: String
    ): List<GetMenuProduct> {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid

        checkMenuProductsAvailability(
            menuProductUuids = postAdditionGroupToMenuProducts.menuProductUuids,
            companyUuid = companyUuid
        )
        checkAdditionsAvailability(
            additionUuids = postAdditionGroupToMenuProducts.additionUuids,
            companyUuid = companyUuid
        )

        additionRepository.insertAdditionGroupToMenuProducts(
            insertAdditionGroupToMenuProducts = postAdditionGroupToMenuProducts.mapPostAdditionGroupToMenuProducts()
        )

        return postAdditionGroupToMenuProducts.menuProductUuids.map { menuProductUuid ->
            menuProductRepository.getMenuProductWithAdditionListByUuid(
                companyUuid = companyUuid,
                uuid = menuProductUuid
            )
                .orThrowNotFoundByUuidError(menuProductUuid)
                .mapMenuProduct()
        }
    }

    suspend fun getAdditionGroups(creatorUuid: String): List<GetAdditionGroup> {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid
            .toUuid()
        return additionRepository.getAdditionGroupListByCompanyUuid(companyUuid = companyUuid)
            .map(mapAdditionGroup)
    }

    suspend fun patchAdditionGroup(
        creatorUuid: String,
        additionGroupUuid: String,
        patchAdditionGroup: PatchAdditionGroup
    ): GetAdditionGroup {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid

        checkAdditionGroupAvailability(
            additionGroupUuid = additionGroupUuid,
            companyUuid = companyUuid
        )
        if (patchAdditionGroup.name != null) {
            val additionGroup = additionRepository.getAdditionGroupByName(
                name = patchAdditionGroup.name,
                companyUuid = companyUuid.toUuid()
            )
            if (additionGroup != null && additionGroupUuid != additionGroup.uuid) {
                additionGroupAlreadyExistsError(name = patchAdditionGroup.name)
            }
        }

        return additionRepository.updateAdditionGroup(
            additionGroupUuid = additionGroupUuid.toUuid(),
            updateAdditionGroup = patchAdditionGroup.mapPatchAdditionGroup()
        ).orThrowNotFoundByUuidError(additionGroupUuid)
            .mapAdditionGroup()
    }

    suspend fun createAddition(postAddition: PostAddition, creatorUuid: String): GetAddition {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid
            .toUuid()

        val addition = additionRepository.getAdditionByNameAndTag(
            name = postAddition.name,
            tag = postAddition.tag,
            companyUuid = companyUuid
        )
        if (addition != null) {
            additionAlreadyExistsError(name = addition.name, tag = addition.tag)
        }
        return additionRepository.insertAddition(
            insertAddition = postAddition.toInsertAddition(companyUuid = companyUuid)
        ).toGetAddition()
    }

    suspend fun addAdditionToAdditionGroup(
        postAdditionToGroup: PostAdditionToGroup,
        creatorUuid: String
    ): List<GetMenuProduct> {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid
        checkAdditionsAvailability(
            additionUuids = listOf(postAdditionToGroup.additionUuid),
            companyUuid = companyUuid
        )
        checkAdditionGroupAvailability(
            additionGroupUuid = postAdditionToGroup.groupUuid,
            companyUuid = companyUuid
        )

        return additionRepository.insertAdditionToGroup(
            insertAdditionToGroup = postAdditionToGroup.mapPostAdditionToGroup()
        ).mapNotNull { menuProduct ->
            menuProductRepository.getMenuProductWithAdditionListByUuid(
                companyUuid = companyUuid,
                uuid = menuProduct.uuid
            )?.mapMenuProduct()
        }
    }

    suspend fun getAddition(creatorUuid: String): List<GetAddition> {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid
            .toUuid()
        return additionRepository.getAdditionListByCompanyUuid(companyUuid = companyUuid)
            .map(Addition::toGetAddition)
    }

    suspend fun patchAddition(
        creatorUuid: String,
        additionUuid: String,
        patchAddition: PatchAddition
    ): GetAddition {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid

        checkAdditionsAvailability(
            additionUuids = listOf(additionUuid),
            companyUuid = companyUuid
        )

        val addition = additionRepository.getAdditionByUuid(
            uuid = additionUuid.toUuid()
        ).orThrowNotFoundByUuidError(uuid = additionUuid)
        if ((patchAddition.name != null && patchAddition.name != addition.name) ||
            (patchAddition.tag != null && patchAddition.tag != addition.tag)
        ) {
            val conflictingAddition = additionRepository.getAdditionByNameAndTag(
                name = patchAddition.name ?: addition.name,
                tag = patchAddition.tag ?: addition.tag,
                companyUuid = companyUuid.toUuid()
            )
            if (conflictingAddition != null) {
                additionAlreadyExistsError(
                    name = conflictingAddition.name,
                    tag = conflictingAddition.tag
                )
            }
        }
        return additionRepository.updateAddition(
            additionUuid = additionUuid.toUuid(),
            updateAddition = patchAddition.toUpdateAddition()
        ).orThrowNotFoundByUuidError(uuid = additionUuid)
            .toGetAddition()
    }

    private suspend fun checkMenuProductsAvailability(
        menuProductUuids: List<String>,
        companyUuid: String
    ) {
        menuProductUuids.forEach { menuProductUuid ->
            val menuProduct = menuProductRepository.getMenuProductByUuid(
                companyUuid = companyUuid,
                uuid = menuProductUuid
            ).orThrowNotFoundByUuidError(uuid = menuProductUuid)
            if (menuProduct.companyUuid != companyUuid) {
                noAccessToMenuProductError(menuProductUuid)
            }
        }
    }

    private suspend fun checkAdditionsAvailability(
        additionUuids: List<String>,
        companyUuid: String
    ) {
        additionUuids.forEach { additionUuid ->
            val addition = additionRepository.getAdditionByUuid(uuid = additionUuid.toUuid())
                .orThrowNotFoundByUuidError(uuid = additionUuid)
            if (addition.companyUuid != companyUuid) {
                noAccessToAdditionError(additionUuid)
            }
        }
    }

    private suspend fun checkAdditionGroupAvailability(
        additionGroupUuid: String,
        companyUuid: String
    ) {
        val additionGroup = additionRepository.getAdditionGroupByUuid(uuid = additionGroupUuid.toUuid())
            .orThrowNotFoundByUuidError(uuid = additionGroupUuid)
        if (additionGroup.companyUuid != companyUuid) {
            noAccessToAdditionGroupError(additionGroupUuid)
        }
    }

    private fun noAccessToMenuProductError(menuProductUuid: String): Nothing {
        error("User doesn't has access to this menu product - $menuProductUuid")
    }

    private fun noAccessToAdditionError(additionUuid: String): Nothing {
        error("User doesn't has access to this addition - $additionUuid")
    }

    private fun noAccessToAdditionGroupError(additionGroupUuid: String): Nothing {
        error("User doesn't has access to this addition group - $additionGroupUuid")
    }

    private fun additionGroupAlreadyExistsError(name: String): Nothing {
        error("Addition group with name \"$name\" already exists")
    }

    private fun additionAlreadyExistsError(name: String, tag: String?): Nothing {
        error("Addition with name \"$name\" and tag \"$tag\" already exists")
    }
}
