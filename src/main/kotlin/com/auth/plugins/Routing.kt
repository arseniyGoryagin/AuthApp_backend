package com.auth.plugins

import com.auth.auth
import com.auth.data.model.user.UserSource
import com.auth.login
import com.auth.register
import com.auth.security.hashing.HashingService
import com.auth.security.token.TokenConfig
import com.auth.security.token.TokenService
import com.auth.user
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    hashingService: HashingService,
    userSource: UserSource,
    tokenService: TokenService,
    tokenConfig: TokenConfig) {


    routing {
        login(tokenService, hashingService, userSource, tokenConfig)
        register(hashingService, userSource)
        auth()
        user(userSource)
        get("/") {
            call.respondText { "HEllo" }
        }
    }

}
