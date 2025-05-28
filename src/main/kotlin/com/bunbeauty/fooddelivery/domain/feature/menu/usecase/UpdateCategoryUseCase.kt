package com.bunbeauty.fooddelivery.domain.feature.menu.usecase

import com.bunbeauty.fooddelivery.data.features.menu.CategoryRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.Category
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.UpdateCategory
import com.bunbeauty.fooddelivery.domain.toUuid

class UpdateCategoryUseCase(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(
        uuid: String,
        updateCategory: UpdateCategory
    ): Category {
        return categoryRepository.updateCategory(categoryUuid = uuid.toUuid(), category = updateCategory)
            .orThrowNotFoundByUuidError(uuid = uuid)
    }
}
