package com.bunbeauty.fooddelivery.service.date_time

import org.joda.time.DateTime
import org.joda.time.DateTimeConstants.MONDAY
import org.joda.time.DateTimeConstants.SUNDAY

private const val DEFAULT_MONTH = "декабрь"
private val MONTH_MAP = mapOf(
    1 to "январь",
    2 to "февраль",
    3 to "март",
    4 to "апрель",
    5 to "май",
    6 to "июнь",
    7 to "июль",
    8 to "август",
    9 to "сентябрь",
    10 to "октябрь",
    11 to "ноябрь",
    12 to "декабрь",
)

private const val DEFAULT_MONTH_IN_GENITIVE = "декабря"
private val MONTH_IN_GENITIVE_MAP = mapOf(
    1 to "января",
    2 to "февраля",
    3 to "марта",
    4 to "апреля",
    5 to "мая",
    6 to "июня",
    7 to "июля",
    8 to "августа",
    9 to "сентября",
    10 to "октября",
    11 to "ноября",
    12 to "декабря",
)

class DateTimeService : IDateTimeService {

    override fun getDateDDMMMMYYYY(millis: Long): String {
        val dateTime = DateTime(millis)

        return dateTime.dayOfMonth.toString() + " " +
                MONTH_IN_GENITIVE_MAP.getOrDefault(dateTime.monthOfYear, DEFAULT_MONTH_IN_GENITIVE) + " " +
                dateTime.year
    }

    override fun getWeekPeriod(millis: Long): String {
        val dateTime = DateTime(millis)
        val weekMonday = getDateDDMMMMYYYY(dateTime.withDayOfWeek(MONDAY).millis)
        val weekSunday = getDateDDMMMMYYYY(dateTime.withDayOfWeek(SUNDAY).millis)

        return "$weekMonday - $weekSunday"
    }

    override fun getDateMMMMYYYY(millis: Long): String {
        val dateTime = DateTime(millis)
        return MONTH_MAP.getOrDefault(dateTime.monthOfYear, DEFAULT_MONTH) + " " + dateTime.year
    }
}