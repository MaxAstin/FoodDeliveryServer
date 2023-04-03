package com.bunbeauty.fooddelivery

import at.favre.lib.crypto.bcrypt.BCrypt
import com.toxicbakery.bcrypt.Bcrypt
import com.toxicbakery.bcrypt.Bcrypt.verify
import kotlin.test.Test
import kotlin.test.assertTrue

class ApplicationTest {

    @Test
    fun testHashing() {
        val value = "secretPassword"
        val hash = String(Bcrypt.hash(value, BCrypt.MIN_COST))
        println(hash)
        val isVerified = verify(value, "hash".toByteArray())
        assertTrue(isVerified)
    }

}