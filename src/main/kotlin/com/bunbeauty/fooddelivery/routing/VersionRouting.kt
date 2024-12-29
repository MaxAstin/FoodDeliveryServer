package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants.COMPANY_UUID_PARAMETER
import com.bunbeauty.fooddelivery.domain.feature.version.VersionService
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.routing.extension.respondOk
import com.bunbeauty.fooddelivery.routing.extension.safely
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureVersionRouting() {
    routing {
        getForceUpdateVersion()
    }
}

private fun Routing.getForceUpdateVersion() {
    val versionService: VersionService by inject()

    get("/force_update_version") {
        safely {
            val companyUuid = call.getParameter(COMPANY_UUID_PARAMETER)
            val forceUpdateVersion = versionService.getForceUpdateVersionByCompanyUuid(companyUuid = companyUuid)
            call.respondOk(forceUpdateVersion)
        }
    }
}
