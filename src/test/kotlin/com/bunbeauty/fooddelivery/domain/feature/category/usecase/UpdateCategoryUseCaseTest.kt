package com.bunbeauty.fooddelivery.domain.feature.category.usecase

import com.bunbeauty.fooddelivery.data.features.menu.CategoryRepository
import com.bunbeauty.fooddelivery.domain.feature.menu.model.category.UpdateCategory
import com.bunbeauty.fooddelivery.domain.feature.menu.usecase.UpdateCategoryUseCase
import com.bunbeauty.fooddelivery.domain.toUuid
import com.bunbeauty.fooddelivery.fake.FakeCategory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateCategoryUseCaseTest {
    private val categoryRepository: CategoryRepository = mockk()
    private var useCase: UpdateCategoryUseCase = UpdateCategoryUseCase(categoryRepository)

    @Test
    fun `invoke should return updated category when repository call is successful`() = runTest {
        // Arrange
        val uuid = "123e4567-e89b-12d3-a456-426614174000"
        val updateCategory = UpdateCategory(name = "New Name", priority = 1)
        val expectedCategory = FakeCategory.create(
            uuid = uuid,
            name = "New Name",
            priority = 1
        )

        coEvery {
            categoryRepository.updateCategory(
                categoryUuid = uuid.toUuid(),
                category = updateCategory
            )
        } returns expectedCategory

        // Act
        val result = useCase(uuid, updateCategory)

        // Assert
        assertEquals(expectedCategory, result)
    }

    @Test
    fun `invoke should throw IllegalStateException when category is not found`() = runTest {
        // Arrange
        val uuid = "123e4567-e89b-12d3-a456-426614174000"
        val updateCategory = UpdateCategory(name = "New Name", priority = 1)

        coEvery {
            categoryRepository.updateCategory(
                categoryUuid = uuid.toUuid(),
                category = updateCategory
            )
        } returns null

        // Act & Assert
        assertThrows(IllegalStateException::class.java) {
            runTest { useCase(uuid, updateCategory) }
        }
    }
}
