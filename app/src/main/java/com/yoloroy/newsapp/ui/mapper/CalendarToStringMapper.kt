package com.yoloroy.newsapp.ui.mapper

import java.text.SimpleDateFormat
import java.util.*

class CalendarToStringMapper(locale: Locale) : Mapper<Calendar, String> {
    private val format = SimpleDateFormat("dd MMMMMMMMM yyyy, HH:mm", locale)

    override fun map(data: Calendar): String = format.format(data.time)
}