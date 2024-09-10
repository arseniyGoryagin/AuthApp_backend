package com.auth.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity(secret : String) {


    authentication {
        jwt {

           verifier(JWT.require(Algorithm.HMAC256(secret)).build())

            validate{ cred ->
                JWTPrincipal(cred.payload)
            }
        }
    }

}
