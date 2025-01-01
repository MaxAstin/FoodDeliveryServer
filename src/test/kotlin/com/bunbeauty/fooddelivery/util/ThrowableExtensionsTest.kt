package com.bunbeauty.fooddelivery.util

import org.junit.Test
import kotlin.test.assertEquals

class ThrowableExtensionsTest {

    @Test
    fun `WHEN throwable have no causes THEN return only its message`() {
        val throwable = Throwable(
            message = "Message",
            cause = null
        )
        val expected = "Extension: Message\n"

        val result = throwable.fullMessage

        assertEquals(expected, result)
    }

    @Test
    fun `WHEN throwable have 2 causes THEN return message with cause messages`() {
        val cause1 = Throwable(
            message = "Cause message 1",
            cause = null
        )
        val cause2 = Throwable(
            message = "Cause message 2",
            cause = cause1
        )
        val throwable = Throwable(
            message = "Message 1",
            cause = cause2
        )
        val expected = "Extension: Message 1\nCaused by: Cause message 2\nCaused by: Cause message 1\n"

        val result = throwable.fullMessage

        assertEquals(expected, result)
    }
}
