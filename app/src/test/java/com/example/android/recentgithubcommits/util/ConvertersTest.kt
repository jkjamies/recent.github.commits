package com.example.android.recentgithubcommits.util

import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class ConvertersTest {

    private lateinit var converters: Converters

    @Before
    fun setup() {
        converters = Converters()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `date returns timestamp value`() {
        val result = converters.dateToTimestamp(Date(1631933529))
        // assertEquals will detect int vs long (and won't be equal), use compareTo
        assertTrue(result?.compareTo(1631933529) == 0)
    }

    @Test
    fun `date returns null if given null value`() {
        val result = converters.dateToTimestamp(null)
        assertEquals(null, result)
    }

    @Test
    fun `timestamp returns date object`() {
        val result = converters.fromTimestamp(1631933529)
        // assertEquals will detect int vs long (and won't be equal), use compareTo
        assertTrue(result?.compareTo(Date(1631933529)) == 0)
    }

    @Test
    fun `timestamp returns null object if given null value`() {
        val result = converters.fromTimestamp(null)
        assertEquals(null, result)
    }
}