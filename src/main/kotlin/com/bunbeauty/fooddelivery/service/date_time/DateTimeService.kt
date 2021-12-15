package com.bunbeauty.fooddelivery.service.date_time

import com.bunbeauty.fooddelivery.data.Constants.DD_MMMM_YYYY_PATTERN
import com.bunbeauty.fooddelivery.data.Constants.MMMM_YYYY_PATTERN
import org.joda.time.DateTime
import org.joda.time.DateTimeConstants.MONDAY
import org.joda.time.DateTimeConstants.SUNDAY

class DateTimeService : IDateTimeService {

    override fun getDateDDMMMMYYYY(millis: Long): String {
        val dateTime = DateTime(millis)

        return dateTime.toString(DD_MMMM_YYYY_PATTERN)
    }

    override fun getWeekPeriod(millis: Long): String {
        val dateTime = DateTime(millis)
        val weekMonday = getDateDDMMMMYYYY(dateTime.withDayOfWeek(MONDAY).millis)
        val weekSunday = getDateDDMMMMYYYY(dateTime.withDayOfWeek(SUNDAY).millis)

        return "$weekMonday - $weekSunday"
    }

    override fun getDateMMMMYYYY(millis: Long): String {
        val dateTime = DateTime(millis)

        return dateTime.toString(MMMM_YYYY_PATTERN)
    }
}