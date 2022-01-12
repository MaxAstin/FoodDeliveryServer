package com.bunbeauty.fooddelivery.service.init

import com.bunbeauty.fooddelivery.data.init.InitCompany

interface IInitService {
    suspend fun initDataBase()
    suspend fun initNewCompany(initCompany: InitCompany)
}