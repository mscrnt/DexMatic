package com.dexmatic.shared

import kotlin.test.Test
import kotlin.test.assertEquals

class CardParserTest {
    private val parser = CardParser()

    @Test
    fun parseSimpleCard() {
        val text = """John Doe
Acme Inc
(123) 456-7890
john.doe@example.com"""
        val contact = parser.parse(text)
        assertEquals("John Doe", contact.name)
        assertEquals("(123) 456-7890", contact.phone)
        assertEquals("john.doe@example.com", contact.email)
        assertEquals("Acme Inc", contact.company)
    }

    @Test
    fun parseDifferentOrder() {
        val text = """Jane Smith - VP Sales
jane@foo.com
FooCorp LLC
+1 555 123 4567"""
        val contact = parser.parse(text)
        assertEquals("Jane Smith - VP Sales", contact.name)
        assertEquals("+1 555 123 4567", contact.phone)
        assertEquals("jane@foo.com", contact.email)
        assertEquals("FooCorp LLC", contact.company)
    }
}
