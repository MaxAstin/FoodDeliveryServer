package com.bunbeauty.fooddelivery.data.repo.hit

interface IHitRepository {

    fun updateHits(companyUuid: String, menuProductUuidList: List<String>)
    fun getHitsByCompanyUuid(companyUuid: String): List<String>
}