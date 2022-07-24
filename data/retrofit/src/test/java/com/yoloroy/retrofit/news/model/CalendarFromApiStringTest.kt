package com.yoloroy.retrofit.news.model

import org.junit.Assert
import org.junit.Test
import java.util.*

class CalendarFromApiStringTest {

    @Test
    fun calendarFromApiString() {
        val expected = Calendar.getInstance().apply {
            set(2022, 3, 21, 10, 13, 0)
            set(Calendar.MILLISECOND, 0)
        }.time
        val actual = calendarFromApiString("2022-04-21T10:13:00Z").time
        Assert.assertEquals(
            expected.time,
            actual.time
        )
    }
}
