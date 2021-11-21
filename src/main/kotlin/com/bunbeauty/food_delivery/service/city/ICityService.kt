package com.bunbeauty.food_delivery.service.city

import com.bunbeauty.food_delivery.data.model.city.GetCity

interface ICityService {

    suspend fun getCityList(): List<GetCity>
}