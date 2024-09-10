package com.auth.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

class JwtTokenGenerator : TokenService{

    override fun generateToken(email : String, secret : String, expiresAt : Date): String {
        return JWT.create().withClaim(  "email",email)
            .withExpiresAt(expiresAt)
            .sign(Algorithm.HMAC256(secret))
    }
}