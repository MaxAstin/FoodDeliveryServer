package com.bunbeauty.food_delivery.data.ext

import com.bunbeauty.food_delivery.data.model.ListWrapper

fun <T : Any> List<T>.toListWrapper(): ListWrapper<T> {
    return ListWrapper(this.size, this)
}