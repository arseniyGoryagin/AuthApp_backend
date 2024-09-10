package com.auth.security.hashing

interface HashingService {
    fun generateSaltedHash(value : String, length : Int = 32) : SaltedHash
    fun verify(value : String, saltedHash: SaltedHash) : Boolean
}