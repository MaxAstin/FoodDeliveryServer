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

    // CODE
    const val CODE_NUMBER_COUNT = 100
    const val CODE_NUMBER_STEP = 9
    const val CODE_DIVIDER = "-"
    const val CODE_LETTERS = "АБВГДЕИКЛМНПРСТУФХЦЧШЭЮЯ"

    // TIME
    const val ORDER_HISTORY_DAY_COUNT = 2

    // REQUEST
    const val REQUEST_LIMIT_TIMEOUT = 60 * 1000L
    const val CONSECUTIVE_REQUESTS_LIMIT = 2

    // HITS
    const val HITS_CATEGORY_NAME = "Хиты"
    const val HITS_COUNT = 5
    const val HITS_ORDER_DAY_COUNT = 30

    // INIT DB
    const val MAIN_COMPANY_NAME = "BunBeauty"
    const val MAIN_CITY_NAME = "Дубна"

    // NOTIFICATION KEYS
    const val ORDER_KOD_KEY = "orderCode"
}