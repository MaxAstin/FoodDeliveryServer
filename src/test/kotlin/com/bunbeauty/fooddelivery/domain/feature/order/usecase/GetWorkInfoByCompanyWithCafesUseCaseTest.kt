package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.domain.feature.cafe.GetWorkInfoByCafeUseCase
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkInfo
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.Workload
import com.bunbeauty.fooddelivery.domain.toUuid
import com.bunbeauty.fooddelivery.fake.FakeCafe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

class GetWorkInfoByCompanyWithCafesUseCaseTest {
    private val cafeRepository: CafeRepository = mockk()
    private val getWorkInfoByCafeUseCase: GetWorkInfoByCafeUseCase = GetWorkInfoByCafeUseCase(cafeRepository)

    @Test
    fun `invoke should return WorkInfo with correct workType and workload when cafe is found`() = runTest {
        // Arrange
        val cafeUuid = UUID.randomUUID().toString()
        val cafe = FakeCafe.create(
            uuid = cafeUuid,
            workType = WorkType.DELIVERY_AND_PICKUP,
            workload = Workload.LOW
        )
        val expectedWorkInfo = WorkInfo(
            workType = WorkType.DELIVERY_AND_PICKUP,
            workload = Workload.LOW
        )

        coEvery { cafeRepository.getCafeByUuid(cafeUuid.toUuid()) } returns cafe

        // Act
        val result = getWorkInfoByCafeUseCase(cafeUuid)

        // Assert
        assertEquals(expectedWorkInfo, result)
    }
}
