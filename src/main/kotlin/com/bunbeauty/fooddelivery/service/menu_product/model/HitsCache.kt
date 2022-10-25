package com.bunbeauty.fooddelivery.service.menu_product.model

import org.joda.time.DateTime

class HitsCache(
    val hitMenuProductUuidList: List<String>,
    private val dateTime: DateTime
) {

    fun isActual(): Boolean {
        val startOfToday = DateTime.now().withTimeAtStartOfDay()
        return startOfToday.isBefore(dateTime)
    }
}
