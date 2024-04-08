package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.address.AddressNetworkDataSource
import com.bunbeauty.fooddelivery.data.features.address.AddressRepository
import com.bunbeauty.fooddelivery.domain.feature.address.AddressService
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val ADDRESS_HTTP_CLIENT = "ADDRESS_HTTP_CLIENT"
private const val ADDRESS_AUTH_TOKEN_KEY = "ADDRESS_AUTH_TOKEN_KEY"

private val addressAuthToken = System.getenv(ADDRESS_AUTH_TOKEN_KEY)

val addressModule = module(createdAtStart = true) {

    single(named(ADDRESS_HTTP_CLIENT)) {
        HttpClient(OkHttp.create()) {
            install(ContentNegotiation) {
                json(get())
            }

            install(Logging) {
                level = LogLevel.ALL
            }

            install(DefaultRequest) {
                host = "suggestions.dadata.ru/suggestions/api/4_1/rs"
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Authorization, addressAuthToken)

                url {
                    protocol = URLProtocol.HTTPS
                }
            }
        }
    }

    factory {
        AddressNetworkDataSource(
            client = get(named(ADDRESS_HTTP_CLIENT))
        )
    }

    single {
        AddressRepository(
            addressNetworkDataSource = get(),
        )
    }

    factory {
        AddressService(
            addressRepository = get(),
            streetRepository = get(),
            clientUserRepository = get(),
            cityRepository = get(),
            cafeRepository = get(),
            checkIsPointInPolygonUseCase = get(),
        )
    }
}