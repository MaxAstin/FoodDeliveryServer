import com.bunbeauty.fooddelivery.auth.IJwtService
import com.bunbeauty.fooddelivery.data.Constants.FB_ADMIN_KEY
import com.bunbeauty.fooddelivery.data.Constants.PORT
import com.bunbeauty.fooddelivery.data.DatabaseFactory
import com.bunbeauty.fooddelivery.di.configureKoin
import com.bunbeauty.fooddelivery.plugins.configureSerialization
import com.bunbeauty.fooddelivery.plugins.configureSockets
import com.bunbeauty.fooddelivery.routing.configureRouting
import com.bunbeauty.fooddelivery.task.scheduleTasks
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.ext.inject
import java.io.InputStream

fun main() {
    DatabaseFactory.init()
    embeddedServer(Netty, port = System.getenv(PORT).toInt()) {
        configureApp()
    }.start(wait = true)
}

private fun Application.configureApp() {
    val inputStream: InputStream = System.getenv(FB_ADMIN_KEY).byteInputStream()
    val options = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(inputStream))
        .build()
    FirebaseApp.initializeApp(options)
    configureSockets()
    configureSerialization()
    configureKoin()
    val jwtService: IJwtService by inject()
    install(Authentication) {
        jwt {
            jwtService.configureAuth(this)
        }
    }
    configureRouting()
    scheduleTasks()
}
