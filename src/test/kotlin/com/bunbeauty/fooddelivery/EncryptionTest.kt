package com.bunbeauty.fooddelivery

import at.favre.lib.crypto.bcrypt.BCrypt
import com.toxicbakery.bcrypt.Bcrypt
import com.toxicbakery.bcrypt.Bcrypt.verify
import io.ktor.utils.io.core.*
import org.junit.Assert.assertTrue
import org.junit.Test
import java.sql.DriverManager.println

class EncryptionTest {

    @Test
    fun testBcryptHashVerification() {
        val value = "secret"

        val hash = String(Bcrypt.hash(value, BCrypt.MIN_COST))
        println(hash)
        val isVerified = verify(value, hash.toByteArray())

        assertTrue(isVerified)
    }

}