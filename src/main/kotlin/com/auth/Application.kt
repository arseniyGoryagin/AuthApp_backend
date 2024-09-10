package com.auth

import com.auth.data.model.user.MongoUserSource
import com.auth.plugins.*
import com.auth.security.hashing.SHA256HashingService
import com.auth.security.token.JwtTokenGenerator
import com.auth.security.token.TokenConfig
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.litote.kmongo.KMongo
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import io.github.cdimascio.dotenv.dotenv


fun main() {
    embeddedServer(Netty,host = "0.0.0.0",  port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {


    val dotenv = dotenv()

    val secret = dotenv["JWT_SECRET"]
    val databaseUrl  = dotenv["DATABASE_URL"]

    install(ContentNegotiation){
        json(Json { ignoreUnknownKeys = true })
    }


    val db = KMongo.createClient(
        connectionString = databaseUrl
    ).getDatabase("authKotlin")


    val userDataSource = MongoUserSource(db)
    val tokenService = JwtTokenGenerator()
    val hashingService = SHA256HashingService()

    val tokenConfig = TokenConfig(
        secret = secret,
        exp = Date.from(Instant.now().plus(1, ChronoUnit.DAYS))
    )


    configureHTTP()
    configureSecurity(secret)
    configureRouting(hashingService = hashingService,
        userSource = userDataSource,
        tokenService = tokenService,
        tokenConfig = tokenConfig)

}
