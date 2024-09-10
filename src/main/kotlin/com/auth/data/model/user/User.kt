package com.auth.data.model.user

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId



data class User(

    @BsonId val id : ObjectId = ObjectId(),
    val name : String,
    val email : String,
    val password : String,
    val phone : String,
    val salt : String
)

@Serializable
data class UserSerializable(

    @BsonId val id : String,
    val name : String,
    val email : String,
    val password : String,
    val phone : String,
    val salt : String



){
    companion object{

        fun toSerializable(user  : User) : UserSerializable{
            return UserSerializable(
                name = user.name,
                email = user.email,
                password = user.password,
                phone = user.phone,
                salt = user.salt,
                id = user.id.toString()
            )
        }
    }
}