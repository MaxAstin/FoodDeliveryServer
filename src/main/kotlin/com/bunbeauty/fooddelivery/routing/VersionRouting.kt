package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.routing.extension.respondBad
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import com.bunbeauty.fooddelivery.service.delivery.IDeliveryService
import com.bunbeauty.fooddelivery.service.version.IVersionService
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureVersionRouting() {

    routing {
        getForceUpdateVersion()
    }
}

fun Routing.getForceUpdateVersion() {

    val versionService: IVersionService by inject()

    get("/force_update_version") {
        safely(COMPANY_UUID_PARAMETER) { parameterMap ->
            val companyUuid = parameterMap[COMPANY_UUID_PARAMETER]!!
            val forceUpdateVersion = versionService.getForceUpdateVersionByCompanyUuid(companyUuid)
            if (forceUpdateVersion == null) {
                call.respondBad("No forceUpdateVersion with companyUuid = $companyUuid")
            } else {
                call.respondOk(forceUpdateVersion)
            }
        }
    }
}