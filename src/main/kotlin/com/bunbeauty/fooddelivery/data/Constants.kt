package com.bunbeauty.fooddelivery.data

object Constants {

    // ENVIRONMENT VARIABLE KEY
    const val JDBC_DRIVER = "JDBC_DRIVER"
    const val DATABASE_URL = "DATABASE_URL"
    const val PORT = "PORT"
    const val FB_ADMIN_KEY = "FB_ADMIN_KEY"
    const val JWT_SECRET = "JWT_SECRET"

    // DATABASE
    const val JDBC_POSTGRESQL_PREFIX = "jdbc:postgresql://"
    const val POSTGRES_PREFIX = "postgres://"
    const val AT_SIGN_DIVIDER = "@"
    const val COLON_DIVIDER = ":"

    // PARAMS
    const val COMPANY_UUID_PARAMETER = "companyUuid"
    const val CITY_UUID_PARAMETER = "cityUuid"
    const val CAFE_UUID_PARAMETER = "cafeUuid"
    const val UUID_PARAMETER = "uuid"
    const val PERIOD_PARAMETER = "period"
    const val COUNT_PARAMETER = "count"
    const val QUERY_PARAMETER = "query"

    // CODE
    const val CODE_NUMBER_COUNT = 100
    const val CODE_NUMBER_STEP = 9
    const val CODE_DIVIDER = "-"
    const val CODE_LETTERS = "АБВГДЕИКЛМНПРСТУФХЦЧШЭЮЯ"

    // TIME
    const val ORDER_HISTORY_DAY_COUNT = 1

    // REQUEST
    const val REQUIRED_TIME_BETWEEN_REQUESTS = 60 * 1000L
    const val DAY_REQUEST_LIMIT = 8

    // HITS
    const val HITS_CATEGORY_NAME = "Хиты"
    const val HITS_COUNT = 6
    const val HITS_ORDER_DAY_COUNT = 30

    // NOTIFICATION KEYS
    const val ORDER_KOD_KEY = "orderCode"
}