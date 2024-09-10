package com.auth.security.token

import java.util.*

interface TokenService {

    fun generateToken(email: String, secret : String, expiresAt : Date) : String

}