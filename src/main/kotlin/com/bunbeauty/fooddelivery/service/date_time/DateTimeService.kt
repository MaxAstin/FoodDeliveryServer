package com.bunbeauty.fooddelivery.service.date_time

import org.joda.time.DateTime
import org.joda.time.DateTimeConstants.MONDAY
import org.joda.time.DateTimeConstants.SUNDAY

class DateTimeService : IDateTimeService {

    override fun getDateDDMMMMYYYY(millis: Long): String {
        val dateTime = DateTime(millis)

        return dateTime.dayOfMonth.toString() + " " + getMonthNameInclined(dateTime.monthOfYear) + " " + dateTime.year
    }

    override fun getWeekPeriod(millis: Long): String {
        val dateTime = DateTime(millis)
        val weekMonday = getDateDDMMMMYYYY(dateTime.withDayOfWeek(MONDAY).millis)
        val weekSunday = getDateDDMMMMYYYY(dateTime.withDayOfWeek(SUNDAY).millis)

        return "$weekMonday - $weekSunday"
    }

    override fun getDateMMMMYYYY(millis: Long): String {
        val dateTime = DateTime(millis)
        return getMonthName(dateTime.monthOfYear) + " " + dateTime.year
    }

    fun getMonthName(monthNumber: Int): String {
        return when (monthNumber) {
            1 -> "январь"
            2 -> "февраль"
            3 -> "март"
            4 -> "апрель"
            5 -> "май"
            6 -> "июнь"
            7 -> "июль"
            8 -> "август"
            9 -> "сентябрь"
            10 -> "октябрь"
            11 -> "ноябрь"
            else -> "декабрь"
        }
    }

    fun getMonthNameInclined(monthNumber: Int): String {
        return when (monthNumber) {
            1 -> "января"
            2 -> "февраля"
            3 -> "марта"
            4 -> "апреля"
            5 -> "мая"
            6 -> "июня"
            7 -> "июля"
            8 -> "августа"
            9 -> "сентября"
            10 -> "октября"
            11 -> "ноября"
            else -> "декабря"
        }
    }
}