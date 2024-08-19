package com.bunbeauty.fooddelivery.domain.feature.menu.service

import com.bunbeauty.fooddelivery.data.features.menu.AdditionRepository
import com.bunbeauty.fooddelivery.data.features.menu.MenuProductRepository
import com.bunbeauty.fooddelivery.data.features.user.UserRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUserUuidError
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.menu.mapper.*
import com.bunbeauty.fooddelivery.domain.feature.menu.model.addition.*
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.GetMenuProduct
import com.bunbeauty.fooddelivery.domain.toUuid

class AdditionService(
    private val userRepository: UserRepository,
    private val additionRepository: AdditionRepository,
    private val menuProductRepository: MenuProductRepository,
) {

    suspend fun createAdditionGroup(postAdditionGroup: PostAdditionGroup, creatorUuid: String): GetAdditionGroup {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid
            .toUuid()

        val additionGroup = additionRepository.getAdditionGroupByName(
            name = postAdditionGroup.name,
            companyUuid = companyUuid,
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
        creatorUuid: String,
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
        patchAdditionGroup: PatchAdditionGroup,
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
                companyUuid = companyUuid.toUuid(),
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

        val additionGroup = additionRepository.getAdditionByName(
            name = postAddition.name,
            companyUuid = companyUuid
        )
        if (additionGroup != null) {
            additionAlreadyExistsError(name = additionGroup.name)
        }

        return additionRepository.insertAddition(
            insertAddition = postAddition.mapPostAddition(companyUuid)
        ).mapAddition()
    }

    suspend fun addAdditionToAdditionGroup(
        postAdditionToGroup: PostAdditionToGroup,
        creatorUuid: String,
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
            .map(mapAddition)
    }

    suspend fun patchAddition(
        creatorUuid: String,
        additionUuid: String,
        patchAddition: PatchAddition,
    ): GetAddition {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid

        checkAdditionsAvailability(
            additionUuids = listOf(additionUuid),
            companyUuid = companyUuid
        )
        if (patchAddition.name != null) {
            val addition = additionRepository.getAdditionByName(
                name = patchAddition.name,
                companyUuid = companyUuid.toUuid(),
            )
            if (addition != null && additionUuid != addition.uuid) {
                additionAlreadyExistsError(name = patchAddition.name)
            }
        }

        return additionRepository.updateAddition(
            additionUuid = additionUuid.toUuid(),
            updateAddition = patchAddition.mapPatchAddition()
        ).orThrowNotFoundByUuidError(additionUuid)
            .mapAddition()
    }

    private suspend fun checkMenuProductsAvailability(
        menuProductUuids: List<String>,
        companyUuid: String,
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
        companyUuid: String,
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
        companyUuid: String,
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

    private fun additionAlreadyExistsError(name: String): Nothing {
        error("Addition with name \"$name\" already exists")
    }

}