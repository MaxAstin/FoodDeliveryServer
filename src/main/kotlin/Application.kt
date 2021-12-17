import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.DatabaseFactory
import com.bunbeauty.fooddelivery.di.*
import com.bunbeauty.fooddelivery.plugins.configureSerialization
import com.bunbeauty.fooddelivery.plugins.configureSockets
import com.bunbeauty.fooddelivery.routing.configureRouting
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import java.io.InputStream

fun main() {
    DatabaseFactory.init()
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        val inputStream: InputStream = System.getenv("FB_ADMIN_KEY").byteInputStream()
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(inputStream))
            .build()
        FirebaseApp.initializeApp(options)
        configureSockets()
        configureSerialization()
        install(Koin) {
            modules(
                dataModule,
                authModule,
                userModule,
                clientUserModule,
                companyModule,
                orderModule,
                cityModule,
                cafeModule,
                streetModule,
                categoryModule,
                menuProductModule,
                deliveryModule,
                addressModule,
                statisticModule,
                dateTimeModule,
            )
        }
        install(Authentication) {
            jwt {
                val jwtService: IJwtService by inject()
                jwtService.configureAuth(this)
            }
        }
        configureRouting()
    }.start(wait = true)
}