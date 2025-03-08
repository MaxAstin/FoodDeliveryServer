package com.bunbeauty.fooddelivery.domain.feature.cafe

import com.bunbeauty.fooddelivery.data.features.cafe.CafeRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkInfo
import com.bunbeauty.fooddelivery.domain.toUuid

class GetWorkInfoByCafeUseCase(
    private val cafeRepository: CafeRepository
) {
    /**
     * Для обартной совместимости есть проверка на isOpen
     * В версии 3.0 удалить
     * */
    suspend operator fun invoke(cafeUuid: String): WorkInfo {
        val cafe = cafeRepository.getCafeByUuid(uuid = cafeUuid.toUuid())
            .orThrowNotFoundByUuidError(uuid = cafeUuid)

        return WorkInfo(
            workType = cafe.workType,
            workload = cafe.workload
        )
    }
}
