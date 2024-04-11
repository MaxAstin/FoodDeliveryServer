package com.bunbeauty.fooddelivery.data.features.cafe

import com.bunbeauty.fooddelivery.data.DatabaseFactory.query
import com.bunbeauty.fooddelivery.data.entity.cafe.CafeEntity
import com.bunbeauty.fooddelivery.data.entity.cafe.DeliveryZoneEntity
import com.bunbeauty.fooddelivery.data.entity.cafe.DeliveryZonePointEntity
import com.bunbeauty.fooddelivery.data.features.cafe.mapper.mapDeliveryZoneEntity
import com.bunbeauty.fooddelivery.data.table.cafe.DeliveryZoneTable
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.DeliveryZone
import com.bunbeauty.fooddelivery.domain.feature.cafe.model.deliveryzone.InsertDeliveryZone
import java.util.*

class DeliveryZoneRepository {

    suspend fun insertDeliveryZone(insertDeliveryZone: InsertDeliveryZone): DeliveryZone = query {
        val deliveryZoneEntity = DeliveryZoneEntity.new {
            name = insertDeliveryZone.name
            minOrderCost = insertDeliveryZone.minOrderCost
            normalDeliveryCost = insertDeliveryZone.normalDeliveryCost
            forLowDeliveryCost = insertDeliveryZone.forLowDeliveryCost
            lowDeliveryCost = insertDeliveryZone.lowDeliveryCost
            isVisible = insertDeliveryZone.isVisible

            cafe = CafeEntity[insertDeliveryZone.cafeUuid]
        }
        insertDeliveryZone.points.onEach { insertPoint ->
            DeliveryZonePointEntity.new {
                order = insertPoint.order
                latitude = insertPoint.latitude
                longitude = insertPoint.latitude
                isVisible = insertPoint.isVisible

                zone = deliveryZoneEntity
            }
        }

        deliveryZoneEntity.mapDeliveryZoneEntity()
    }

    suspend fun getDeliveryZoneListByCafeUuid(cafeUuid: UUID): List<DeliveryZone> = query {
        DeliveryZoneEntity.find {
            DeliveryZoneTable.cafe eq cafeUuid
        }.map(mapDeliveryZoneEntity)
    }

}