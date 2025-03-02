package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.feature.company.usecase.GetWorkInfoByCompanyUseCase
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.toUuid
import com.bunbeauty.fooddelivery.fake.FakeCompany
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetWorkInfoByCafeUseCaseTest {
    private val companyRepository: CompanyRepository = mockk()
    private val getWorkInfoByCompanyUseCase: GetWorkInfoByCompanyUseCase = GetWorkInfoByCompanyUseCase(companyRepository)

    @Test
    fun `invoke should return WorkInfo with CLOSED workType when company is not open`() = runTest {
        // Arrange
        val companyUuid = "123e4567-e89b-12d3-a456-426614174000"
        val company = FakeCompany.create(
            forFreeDelivery = 500,
            deliveryCost = 100,
            isOpen = false,
            workType = WorkType.DELIVERY_AND_PICKUP
        )
        coEvery { companyRepository.getCompanyByUuid(companyUuid.toUuid()) } returns company

        // Act
        val result = getWorkInfoByCompanyUseCase(companyUuid)

        // Assert
        assertEquals(WorkType.CLOSED, result.workType)
    }

    @Test
    fun `invoke should return WorkInfo with company workType when company is open`() = runTest {
        // Arrange
        val companyUuid = "123e4567-e89b-12d3-a456-426614174000"
        val company = FakeCompany.create(
            forFreeDelivery = 500,
            deliveryCost = 100,
            isOpen = true,
            workType = WorkType.DELIVERY_AND_PICKUP
        )
        coEvery { companyRepository.getCompanyByUuid(companyUuid.toUuid()) } returns company

        // Act
        val result = getWorkInfoByCompanyUseCase(companyUuid)

        // Assert
        assertEquals(WorkType.DELIVERY_AND_PICKUP, result.workType)
    }
}
