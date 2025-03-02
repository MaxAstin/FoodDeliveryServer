package com.bunbeauty.fooddelivery.data.table.cafe

import com.bunbeauty.fooddelivery.data.table.CityTable
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.WorkType
import com.bunbeauty.fooddelivery.domain.model.cafe.work_info.Workload
import org.jetbrains.exposed.dao.id.UUIDTable

object CafeTable : UUIDTable() {

    val fromTime = integer("fromTime")
    val toTime = integer("toTime")
    val offset = integer("offset").default(0)
    val phoneNumber = varchar("phone", 512)
    val latitude = double("latitude")
    val longitude = double("longitude")
    val address = varchar("address", 512)
    val codeCounter = integer("codeCounter").default(0)
    val isVisible = bool("isVisible")
    val workType = enumeration("workType", WorkType::class).default(WorkType.DELIVERY_AND_PICKUP)
    val workload = enumeration("workload", Workload::class).default(Workload.LOW)

    val city = reference("city", CityTable)
}
