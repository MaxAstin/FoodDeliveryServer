package com.bunbeauty.fooddelivery.domain.feature.company.usecase

import com.bunbeauty.fooddelivery.data.repo.CompanyRepository
import com.bunbeauty.fooddelivery.domain.error.orThrowNotFoundByUuidError
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkInfo
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.Workload
import com.bunbeauty.fooddelivery.domain.toUuid

@Deprecated("Use GetWorkInfoByCafeUseCase, now workType belong to cafe")
class GetWorkInfoByCompanyUseCase(
    private val companyRepository: CompanyRepository
) {
    /**
     * Для обартной совместимости есть проверка на isOpen
     * В версии 3.0 удалить
     * */
    suspend operator fun invoke(companyUuid: String): WorkInfo {
        val company = companyRepository.getCompanyByUuid(uuid = companyUuid.toUuid())
            .orThrowNotFoundByUuidError(uuid = companyUuid)

        return WorkInfo(
            workType = if (company.isOpen) {
                company.workType
            } else {
                WorkType.CLOSED
            },
            workload = Workload.LOW
        )
    }
}
