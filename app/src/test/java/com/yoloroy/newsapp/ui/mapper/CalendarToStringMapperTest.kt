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
}
