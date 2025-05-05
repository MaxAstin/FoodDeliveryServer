package com.bunbeauty.fooddelivery.routing

import com.bunbeauty.fooddelivery.data.Constants
import com.bunbeauty.fooddelivery.routing.extension.getListResult
import com.bunbeauty.fooddelivery.routing.extension.getParameter
import com.bunbeauty.fooddelivery.service.LinkService
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.html.respondHtml
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.li
import kotlinx.html.p
import kotlinx.html.style
import kotlinx.html.title
import kotlinx.html.ul
import org.koin.ktor.ext.inject

fun Application.configureLinkRouting() {
    routing {
        getLinks()
        getMainPage()
    }
}

private fun Routing.getLinks() {
    val linkService: LinkService by inject()

    get("/link") {
        getListResult {
            val companyUuid = call.getParameter(Constants.COMPANY_UUID_PARAMETER)
            linkService.getLinksByCompanyUuid(companyUuid = companyUuid)
        }
    }
}

private fun Routing.getMainPage() {
    get("/main_page") {
        call.respondHtml {
            head {
                title { +"BunBeauty" }
                style {
                    """
                        body {
                            font-family: Arial, sans-serif;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            height: 100vh;
                            margin: 0;
                            background-color: #f5f5f5;
                        }
                        .container {
                            text-align: center;
                            padding: 40px;
                            background: white;
                            border-radius: 10px;
                            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                        }
                        h1 {
                            color: #ff6b6b;
                            font-size: 3em;
                            margin-bottom: 10px;
                        }
                        .slogan {
                            color: #666;
                            font-size: 1.2em;
                            margin-bottom: 30px;
                            font-style: italic;
                        }
                        .restaurant-list {
                            list-style-type: none;
                            padding: 0;
                        }
                        .restaurant-list a {
                            display: block;
                            margin: 15px 0;
                            color: #333;
                            text-decoration: none;
                            font-size: 1.2em;
                            transition: color 0.3s;
                        }
                        .restaurant-list a:hover {
                            color: #ff6b6b;
                        }
                    """.trimIndent()
                }
            }
            body {
                div("container") {
                    h1 { +"BunBeauty" }
                    p("slogan") { +"Еда в приложении. Комфорт в жизни." }
                    ul("restaurant-list") {
                        li { a(href = "https://apps.apple.com/us/app/%D0%BF%D0%B0%D0%BF%D0%B0-%D0%BA%D0%B0%D1%80%D0%BB%D0%BE/id6443966083") { +"1. Папа карло" } }
                        li { a(href = "https://apps.apple.com/us/app/%D0%B3%D1%83%D1%81%D1%82%D0%BE-%D0%BF%D0%B0%D0%B1/id6450189862") { +"2. Густо паб" } }
                        li { a(href = "https://apps.apple.com/us/app/%D1%80%D0%BA-%D1%83%D1%81%D0%B0%D0%B4%D1%8C%D0%B1%D0%B0/id6737488265") { +"3. Усадьба" } }
                    }
                }
            }
        }
    }
}
