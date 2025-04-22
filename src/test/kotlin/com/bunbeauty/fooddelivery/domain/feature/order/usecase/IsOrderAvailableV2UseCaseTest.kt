package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.toUuid
import com.bunbeauty.fooddelivery.fake.FakeCafe
import com.bunbeauty.fooddelivery.fake.FakeCompany
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class IsOrderAvailableV2UseCaseTest {
    private val companyRepository: CompanyRepository = mockk()
    private val cafeRepository: CafeRepository = mockk()
    private val useCase: IsOrderAvailableV2UseCase = IsOrderAvailableV2UseCase(companyRepository, cafeRepository)

    @Test
    fun `invoke should return true when both company and cafe are available`() = runTest {
        // Arrange
        val companyUuid = UUID.randomUUID().toString()
        val cafeUuid = UUID.randomUUID().toString()
        val company = FakeCompany.create(
            uuid = companyUuid,
            isOpen = true,
            workType = WorkType.DELIVERY_AND_PICKUP
        )
        val cafe = FakeCafe.create(
            uuid = cafeUuid,
            workType = WorkType.DELIVERY_AND_PICKUP
        )

        coEvery { companyRepository.getCompanyByUuid(companyUuid.toUuid()) } returns company
        coEvery { cafeRepository.getCafeByUuid(cafeUuid.toUuid()) } returns cafe

        // Act
        val result = useCase(companyUuid, cafeUuid)

        // Assert
        assertEquals(true, result)
    }

    @Test
    fun `invoke should return false when company is closed`() = runTest {
        // Arrange
        val companyUuid = UUID.randomUUID().toString()
        val cafeUuid = UUID.randomUUID().toString()
        val company = FakeCompany.create(
            uuid = companyUuid,
            isOpen = false,
            workType = WorkType.DELIVERY_AND_PICKUP
        )
        val cafe = FakeCafe.create(
            uuid = cafeUuid,
            workType = WorkType.DELIVERY_AND_PICKUP
        )

        coEvery { companyRepository.getCompanyByUuid(companyUuid.toUuid()) } returns company
        coEvery { cafeRepository.getCafeByUuid(cafeUuid.toUuid()) } returns cafe

        // Act
        val result = useCase(companyUuid, cafeUuid)

        // Assert
        assertEquals(false, result)
    }

    @Test
    fun `invoke should return false when company workType is CLOSED`() = runTest {
        // Arrange
        val companyUuid = UUID.randomUUID().toString()
        val cafeUuid = UUID.randomUUID().toString()
        val company = FakeCompany.create(
            uuid = companyUuid,
            isOpen = true,
            workType = WorkType.CLOSED
        )
        val cafe = FakeCafe.create(
            uuid = cafeUuid,
            workType = WorkType.DELIVERY_AND_PICKUP
        )

        coEvery { companyRepository.getCompanyByUuid(companyUuid.toUuid()) } returns company
        coEvery { cafeRepository.getCafeByUuid(cafeUuid.toUuid()) } returns cafe

        // Act
        val result = useCase(companyUuid, cafeUuid)

        // Assert
        assertEquals(false, result)
    }

    @Test
    fun `invoke should return false when cafe workType is CLOSED`() = runTest {
        // Arrange
        val companyUuid = UUID.randomUUID().toString()
        val cafeUuid = UUID.randomUUID().toString()
        val company = FakeCompany.create(
            uuid = companyUuid,
            isOpen = true,
            workType = WorkType.DELIVERY_AND_PICKUP
        )
        val cafe = FakeCafe.create(
            uuid = cafeUuid,
            workType = WorkType.CLOSED
        )

        coEvery { companyRepository.getCompanyByUuid(companyUuid.toUuid()) } returns company
        coEvery { cafeRepository.getCafeByUuid(cafeUuid.toUuid()) } returns cafe

        // Act
        val result = useCase(companyUuid, cafeUuid)

        // Assert
        assertEquals(false, result)
    }
}
