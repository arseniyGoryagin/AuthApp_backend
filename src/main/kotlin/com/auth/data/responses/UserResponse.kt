package com.auth.data.responses

import com.auth.data.model.user.User
import com.auth.data.model.user.UserSerializable
import kotlinx.serialization.Serializable


@Serializable
data class UserResponse (
    val userData : UserSerializable
)