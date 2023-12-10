package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.auth.AuthNetworkDataSource
import com.bunbeauty.fooddelivery.data.features.auth.AuthorizationRepository
import com.bunbeauty.fooddelivery.service.AuthorizationService
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val AUTH_API_EMAIL_KEY = "AUTH_API_EMAIL_KEY"
private const val AUTH_API_KEY = "AUTH_API_KEY"
private const val AUTH_HTTP_CLIENT = "AUTH_HTTP_CLIENT"

private val authApiUsername = System.getenv(AUTH_API_EMAIL_KEY)
private val authApiPassword = System.getenv(AUTH_API_KEY)

val module =  module(createdAtStart = true) {
    factory {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        }
    }
}

val authorizationModule = module(createdAtStart = true) {

    single { AuthorizationRepository() }

    factory {  AuthNetworkDataSource(get(named(AUTH_HTTP_CLIENT))) }

    single(named(AUTH_HTTP_CLIENT)) {
        HttpClient(OkHttp.create()) {
            install(ContentNegotiation) {
                json(get())
            }

            install(DefaultRequest) {
                host = "gate.smsaero.ru/v2"
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                basicAuth(authApiUsername, authApiPassword)

                url {
                    protocol = URLProtocol.HTTPS
                }
            }
        }
    }

    factory {
        AuthorizationService(
            requestService = get(),
            authorizationRepository = get(),
            companyRepository = get(),
            authNetworkDataSource = get(),
            clientUserRepository = get(),
            jwtService = get(),
        )
    }

}