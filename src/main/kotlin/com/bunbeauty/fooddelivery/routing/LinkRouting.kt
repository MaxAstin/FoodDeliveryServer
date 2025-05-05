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
import kotlinx.html.unsafe
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
                title { +"BunBeauty - Лучшие рестораны" }
                style {
                    unsafe {
                        """
                        body {
                            font-family: 'Arial', sans-serif;
                            margin: 0;
                            padding: 0;
                            height: 100vh;
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            background: url('https://firebasestorage.googleapis.com/v0/b/fooddelivery-ce2ef.appspot.com/o/Header.webp?alt=media&token=d5c93c88-56d8-467e-ba88-00c8403787c1') center/cover no-repeat;
                            color: #333;
                        }
                        .overlay {
                            background-color: rgba(255, 255, 255, 0.9);
                            padding: 40px;
                            border-radius: 15px;
                            text-align: center;
                            max-width: 600px;
                            box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
                        }
                        h1 {
                            color: #0AB9E8;
                            font-size: 3em;
                            margin-bottom: 10px;
                        }
                        .slogan {
                            color: #555;
                            font-size: 1.3em;
                            margin-bottom: 30px;
                            font-weight: 300;
                        }
                        .restaurant-list {
                            list-style-type: none;
                            padding: 0;
                            margin-bottom: 30px;
                        }
                        .restaurant-list a {
                            display: block;
                            margin: 15px 0;
                            color: #333;
                            text-decoration: none;
                            font-size: 1.2em;
                            transition: all 0.3s;
                            padding: 8px;
                            border-radius: 5px;
                        }
                        .restaurant-list a:hover {
                            color: #ff6b6b;
                            background-color: rgba(255, 107, 107, 0.1);
                        }
                        .contact-button {
                            display: inline-block;
                            background-color: #0AB9E8;
                            color: white;
                            text-decoration: none;
                            padding: 12px 30px;
                            border-radius: 30px;
                            font-size: 1.1em;
                            transition: all 0.3s;
                            border: none;
                            cursor: pointer;
                            margin-top: 20px;
                        }
                        .contact-button:hover {
                            background-color: #089bc8;
                            transform: translateY(-2px);
                            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                        }
                        """.trimIndent()
                    }
                }
            }
            body {
                div("overlay") {
                    h1 { +"BunBeauty" }
                    p("slogan") { +"Еда в приложении. Комфорт в жизни." }
                    p("slogan") { +"Нас выбирают" }
                    ul("restaurant-list") {
                        li { a(href = "https://apps.apple.com/us/app/%D0%B3%D1%83%D1%81%D1%82%D0%BE-%D0%BF%D0%B0%D0%B1/id6450189862") { +"1. Густо паб" } }
                        li { a(href = "https://apps.apple.com/us/app/%D0%BF%D0%B0%D0%BF%D0%B0-%D0%BA%D0%B0%D1%80%D0%BB%D0%BE/id6443966083") { +"2. Папа карло" } }
                        li { a(href = "https://apps.apple.com/us/app/%D1%80%D0%BA-%D1%83%D1%81%D0%B0%D0%B4%D1%8C%D0%B1%D0%B0/id6737488265") { +"3. Усадьба" } }
                        li { a(href = "https://apps.apple.com/us/app/%D1%8D%D0%BC%D0%BE%D0%B4%D0%B7%D0%B8/id6737520690") { +"4. Эмодзи" } }
                        li { a(href = "https://apps.apple.com/us/app/%D1%8E%D0%BB%D0%B8%D0%B0%D1%80/id6447322629") { +"4. Юлиар" } }
                        li { a(href = "https://apps.apple.com/us/developer/mark-shavlovskiy/id1651086345") { +"И другие..." } }
                    }
                    a(href = "https://vk.com/bunbeauty?from=groups", classes = "contact-button") { +"Связаться с нами" }
                }
            }
        }
    }
}
// body {
//    div("container") {
//        h1 { +"BunBeauty" }
//        p("slogan") { +"Еда в приложении. Комфорт в жизни." }
//        ul("restaurant-list") {
//            li { a(href = "https://apps.apple.com/us/app/%D0%BF%D0%B0%D0%BF%D0%B0-%D0%BA%D0%B0%D1%80%D0%BB%D0%BE/id6443966083") { +"1. Папа карло" } }
//            li { a(href = "https://apps.apple.com/us/app/%D0%B3%D1%83%D1%81%D1%82%D0%BE-%D0%BF%D0%B0%D0%B1/id6450189862") { +"2. Густо паб" } }
//            li { a(href = "https://apps.apple.com/us/app/%D1%80%D0%BA-%D1%83%D1%81%D0%B0%D0%B4%D1%8C%D0%B1%D0%B0/id6737488265") { +"3. Усадьба" } }
//        }
//    }
// }
