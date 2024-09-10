package com.auth.data.requests

import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequest(
    val name : String,
    val email : String,
    val password : String,
    val phone : String
)