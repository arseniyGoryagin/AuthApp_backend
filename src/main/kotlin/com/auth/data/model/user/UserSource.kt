package com.auth.data.model.user

interface UserSource {

    suspend fun getUserByEmail(email : String) : User?
    suspend fun insertNewUser(user : User) : Boolean

}