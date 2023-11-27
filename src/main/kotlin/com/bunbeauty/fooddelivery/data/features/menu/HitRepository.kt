package com.bunbeauty.fooddelivery.data.features.menu

class HitRepository {

    private val hitsCache: MutableMap<String, List<String>> = mutableMapOf()

    fun updateHits(companyUuid: String, menuProductUuidList: List<String>) {
        hitsCache[companyUuid] = menuProductUuidList
    }

   fun getHitProductUuidListByCompanyUuid(companyUuid: String): List<String> {
        return hitsCache[companyUuid] ?: emptyList()
    }


}