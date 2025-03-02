package com.bunbeauty.fooddelivery.domain.model.cafe.work_info

/**
 * Class describes company work state
 * @DELIVERY - user can make only delivery order
 * @PICKUP - user can make only delivery pickup
 * @DELIVERY_AND_PICKUP - user can make delivery and pickup
 */
enum class WorkType {
    DELIVERY,
    PICKUP,
    DELIVERY_AND_PICKUP,
    CLOSED
}
