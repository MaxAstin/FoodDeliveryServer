package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.repo.payment_method.IPaymentMethodRepository
import com.bunbeauty.fooddelivery.data.repo.payment_method.PaymentMethodRepository
import com.bunbeauty.fooddelivery.service.payment_method.IPaymentMethodService
import com.bunbeauty.fooddelivery.service.payment_method.PaymentMethodService
import org.koin.dsl.module

val paymentMethodModule = module(createdAtStart = true) {

    single<IPaymentMethodService> {
        PaymentMethodService(
            paymentMethodRepository = get()
        )
    }
    single<IPaymentMethodRepository> { PaymentMethodRepository() }
}