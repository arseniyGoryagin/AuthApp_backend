package com.auth.security.token

import java.util.Date

data class TokenConfig(

    val secret : String,
    val exp : Date


)
