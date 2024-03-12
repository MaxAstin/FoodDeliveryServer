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

    suspend fun createAdditionGroup(postAdditionGroup: PostAdditionGroup, creatorUuid: String): List<GetMenuProduct> {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid

        checkMenuProductsAvailability(
            menuProductUuids = postAdditionGroup.menuProductUuids,
            companyUuid = companyUuid
        )
        checkAdditionsAvailability(
            additionUuids = postAdditionGroup.additionUuids,
            companyUuid = companyUuid
        )

        additionRepository.insertAdditionGroup(
            insertAdditionGroup = postAdditionGroup.mapPostAdditionGroup(companyUuid.toUuid())
        )

        return postAdditionGroup.menuProductUuids.map { menuProductUuid ->
            menuProductRepository.getMenuProductWithAdditionListByUuid(uuid = menuProductUuid.toUuid())
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

    suspend fun createAddition(postAddition: PostAddition, creatorUuid: String): GetAddition {
        val companyUuid = userRepository.getCompanyByUserUuid(creatorUuid.toUuid())
            .orThrowNotFoundByUserUuidError(creatorUuid)
            .uuid
            .toUuid()

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
                uuid = menuProduct.uuid.toUuid()
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

    private suspend fun checkMenuProductsAvailability(
        menuProductUuids: List<String>,
        companyUuid: String,
    ) {
        menuProductUuids.forEach { menuProductUuid ->
            val menuProduct = menuProductRepository.getMenuProductByUuid(uuid = menuProductUuid.toUuid())
                .orThrowNotFoundByUuidError(uuid = menuProductUuid)
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

}