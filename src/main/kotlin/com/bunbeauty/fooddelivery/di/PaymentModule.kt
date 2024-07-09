package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.service.payment.IPaymentService
import com.bunbeauty.fooddelivery.service.payment.PaymentService
import org.koin.dsl.module

val paymentModule = module(createdAtStart = true) {
    single<IPaymentService> {
        PaymentService(
            clientUserRepository = get()
        )
    }
}