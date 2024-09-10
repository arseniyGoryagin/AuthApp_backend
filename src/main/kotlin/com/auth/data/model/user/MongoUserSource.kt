package com.auth.data.model.user

import com.mongodb.client.MongoDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

class MongoUserSource(private val db : MongoDatabase) : UserSource {


    private val users  = db.getCollection<User>("Users")

    override suspend fun getUserByEmail(email : String): User? {
        return users.findOne(User::email eq email )
    }

    override suspend fun insertNewUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }
}