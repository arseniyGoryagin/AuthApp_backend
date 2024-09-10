package com.auth

import com.auth.data.model.user.User
import com.auth.data.model.user.UserSerializable
import com.auth.data.model.user.UserSource
import com.auth.data.requests.LoginRequest
import com.auth.data.requests.RegisterRequest
import com.auth.data.responses.LoginResponse
import com.auth.data.responses.UserResponse
import com.auth.security.hashing.HashingService
import com.auth.security.hashing.SaltedHash
import com.auth.security.token.TokenConfig
import com.auth.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.register(hashingService : HashingService, userSource: UserSource){

 post("register"){

   val req = call.receiveOrNull<RegisterRequest>() ?: kotlin.run {
       call.respond(HttpStatusCode.BadRequest)
       return@post
   }


     // Validate
     if(req.password.isBlank() || req.email.isBlank()  || req.name.isBlank() || req.phone.isBlank()){
         call.respond(HttpStatusCode.BadRequest)
         return@post
     }

     if(userSource.getUserByEmail(req.email) != null){
         call.respond(HttpStatusCode.BadRequest)
         return@post
     }


     val saltedHash  = hashingService.generateSaltedHash(req.password)

     val user = User(
         name = req.name,
         email = req.email,
         phone = req.phone,
         password = saltedHash.hash,
         salt = saltedHash.salt
     )


     if(!userSource.insertNewUser(user)){
         call.respond(HttpStatusCode.Conflict)
         return@post
     }

     call.respond(HttpStatusCode.OK)


 }


}


fun Route.login(jwtTokenService : TokenService, hashingService : HashingService,  userSource: UserSource, config : TokenConfig){

    post("login"){

        val req = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }


        // Validate
        if(req.password.isBlank() || req.email.isBlank()){
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }


        val user = userSource.getUserByEmail(email = req.email) ?: kotlin.run {
            call.respond(HttpStatusCode.Conflict, "User does not exist")
            return@post
        }

        if(hashingService.verify(req.password, saltedHash = SaltedHash(
            hash = user.password,
            salt = user.salt
        ))){
            call.respond(HttpStatusCode.Conflict, "Wrong password")
            return@post
        }


        val token = jwtTokenService.generateToken(req.email, config.secret, config.exp)

        call.respond(LoginResponse(
            token = token
        ))


    }


}


fun Route.auth(){
    authenticate{
        get("authenticate"){
            call.respond(HttpStatusCode.OK)
        }
    }
}


fun Route.user(userSource: UserSource){
    authenticate{

        get("user"){
            val principal = call.principal<JWTPrincipal>()

            val email = principal?.getClaim("email", String::class)

            if(email == null){
                call.respond(HttpStatusCode.Conflict)
                return@get
            }

            val user = userSource.getUserByEmail(email)

            if(user == null){
                call.respond(HttpStatusCode.Conflict)
                return@get
            }


            call.respond(UserResponse(
                userData = UserSerializable.toSerializable(user)
            ))

        }
    }
}