package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.feature.company.usecase.GetWorkInfoUseCase
import com.bunbeauty.fooddelivery.domain.model.company.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.toUuid
import com.bunbeauty.fooddelivery.fake.FakeCompany
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetWorkInfoUseCaseTest {
    private val companyRepository: CompanyRepository = mockk()
    private val getWorkInfoUseCase: GetWorkInfoUseCase = GetWorkInfoUseCase(companyRepository)

    @Test
    fun `returns work info when company exists`() = runTest {
        // Given
        val companyUuid = "123e4567-e89b-12d3-a456-426614174000"
        val company = FakeCompany.create(
            forFreeDelivery = 500,
            deliveryCost = 100,
            workType = WorkType.DELIVERY_AND_PICKUP
        )

        coEvery { companyRepository.getCompanyByUuid(companyUuid.toUuid()) } returns company

        // When
        val result = getWorkInfoUseCase.invoke(companyUuid)

        // Then
        assertEquals(WorkType.DELIVERY_AND_PICKUP, result.workType)
    }
}
