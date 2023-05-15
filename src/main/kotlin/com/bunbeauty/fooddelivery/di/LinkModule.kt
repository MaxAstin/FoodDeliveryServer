package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.LinkRepository
import com.bunbeauty.fooddelivery.data.repo.payment_method.IPaymentMethodRepository
import com.bunbeauty.fooddelivery.data.repo.payment_method.PaymentMethodRepository
import com.bunbeauty.fooddelivery.service.LinkService
import com.bunbeauty.fooddelivery.service.payment_method.IPaymentMethodService
import com.bunbeauty.fooddelivery.service.payment_method.PaymentMethodService
import org.koin.dsl.module

val linkModule = module(createdAtStart = true) {

    factory {
        LinkService(
            linkRepository = get()
        )
    }
    single { LinkRepository() }
}