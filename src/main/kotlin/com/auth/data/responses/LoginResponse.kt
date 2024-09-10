package com.auth.data.responses

import kotlinx.serialization.Serializable


@Serializable
data class LoginResponse(
    val token : String
)