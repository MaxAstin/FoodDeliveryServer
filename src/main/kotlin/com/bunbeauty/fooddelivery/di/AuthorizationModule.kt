package com.bunbeauty.fooddelivery.di

import com.bunbeauty.fooddelivery.data.features.auth.AuthorizationNetworkDataSource
import com.bunbeauty.fooddelivery.data.features.auth.AuthorizationRepository
import com.bunbeauty.fooddelivery.service.AuthorizationService
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.basicAuth
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val AUTH_API_EMAIL_KEY = "AUTH_API_EMAIL_KEY"
private const val AUTH_API_KEY = "AUTH_API_KEY"
private const val AUTH_HTTP_CLIENT = "AUTH_HTTP_CLIENT"

private val authApiUsername = System.getenv(AUTH_API_EMAIL_KEY)
private val authApiPassword = System.getenv(AUTH_API_KEY)

val authorizationModule = module(createdAtStart = true) {

    single { AuthorizationRepository() }

    factory {
        AuthorizationNetworkDataSource(get(named(AUTH_HTTP_CLIENT)))
    }

    single(named(AUTH_HTTP_CLIENT)) {
        HttpClient(OkHttp.create()) {
            install(ContentNegotiation) {
                json(get())
            }

            install(Logging) {
                level = LogLevel.ALL
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
            authorizationNetworkDataSource = get(),
            clientUserRepository = get(),
            jwtService = get()
        )
    }
}
