package com.yoloroy.newsapp.ui.mapper

import com.yoloroy.newsapp.ui.mapper.common.CalendarToStringMapper
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class CalendarToStringMapperTest {

    @Test
    fun `check full months naming`() {
        val mapper = CalendarToStringMapper(Locale.ENGLISH)
        val monthsNames = listOf(
            "January", "February",
            "March", "April", "May",
            "June", "July", "August",
            "September", "October", "November",
            "December"
        )
        for ((mI, mName) in monthsNames.withIndex()) {
            val date = Calendar.getInstance().apply {
                set(2022, mI, 18, 22, 22)
            }
            assertEquals("18 $mName 2022, 22:22", mapper.map(date))
        }
    }

    @Test
    fun `check day presenting`() {
        val mapper = CalendarToStringMapper(Locale.ENGLISH)
        run {
            val date = Calendar.getInstance().apply {
                set(2022, 0, 18, 22, 22)
            }
            assertEquals("18 January 2022, 22:22", mapper.map(date))
        }
        run {
            val date = Calendar.getInstance().apply {
                set(2022, 0, 8, 22, 22)
            }
            assertEquals("8 January 2022, 22:22", mapper.map(date))
        }
    }

    @Test
    fun `check time presenting`() {
        val mapper = CalendarToStringMapper(Locale.ENGLISH)
        run {
            val date = Calendar.getInstance().apply {
                set(2022, 0, 18, 22, 22)
            }
            assertEquals("check normal time", "18 January 2022, 22:22", mapper.map(date))
        }
        run {
            val date = Calendar.getInstance().apply {
                set(2022, 0, 18, 2, 22)
            }
            assertEquals("check one symbol hour", "18 January 2022, 02:22", mapper.map(date))
        }
        run {
            val date = Calendar.getInstance().apply {
                set(2022, 0, 18, 22, 2)
            }
            assertEquals("check one symbol minute", "18 January 2022, 22:02", mapper.map(date))
        }
        run {
            val date = Calendar.getInstance().apply {
                set(2022, 0, 18, 2, 2)
            }
            assertEquals("check one symbol hour and minute", "18 January 2022, 02:02", mapper.map(date))
        }
    }
}
