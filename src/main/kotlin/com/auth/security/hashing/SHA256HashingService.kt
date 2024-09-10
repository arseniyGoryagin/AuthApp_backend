package com.auth.security.hashing

import org.apache.commons.codec.binary.Hex
import org.apache.commons.codec.digest.DigestUtils
import java.security.SecureRandom

class SHA256HashingService : HashingService {


    override fun generateSaltedHash(value: String, length : Int ): SaltedHash {
        val salt  = SecureRandom.getInstance("SHA1PRNG").generateSeed(length)
        val saltHex = Hex.encodeHexString(salt)
        val hash = DigestUtils.sha256Hex("$salt$value")
        return SaltedHash(hash = hash, salt = saltHex)
    }

    override fun verify(value: String, saltedHash: SaltedHash): Boolean {
        return DigestUtils.sha256Hex(saltedHash.salt + value) == saltedHash.hash
    }


}