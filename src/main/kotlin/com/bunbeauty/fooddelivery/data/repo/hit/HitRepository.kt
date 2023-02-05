package com.bunbeauty.fooddelivery.data.repo.hit

class HitRepository : IHitRepository {

    private val hitsCache: MutableMap<String, List<String>> = mutableMapOf()

    override fun updateHits(companyUuid: String, menuProductUuidList: List<String>) {
        hitsCache[companyUuid] = menuProductUuidList
    }

    override fun getHitsByCompanyUuid(companyUuid: String): List<String> {
        return hitsCache[companyUuid] ?: emptyList()
    }


}