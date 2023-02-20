package com.bunbeauty.fooddelivery.data

import com.bunbeauty.fooddelivery.data.Constants.AT_SIGN_DIVIDER
import com.bunbeauty.fooddelivery.data.Constants.COLON_DIVIDER
import com.bunbeauty.fooddelivery.data.Constants.DATABASE_URL
import com.bunbeauty.fooddelivery.data.Constants.JDBC_DRIVER
import com.bunbeauty.fooddelivery.data.Constants.JDBC_POSTGRESQL_PREFIX
import com.bunbeauty.fooddelivery.data.Constants.POSTGRES_PREFIX
import com.bunbeauty.fooddelivery.data.table.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        val dataSource = getDataSource()
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(CompanyTable)
            SchemaUtils.createMissingTablesAndColumns(CompanyTable)

            SchemaUtils.create(UserTable)
            SchemaUtils.createMissingTablesAndColumns(UserTable)

            SchemaUtils.create(ClientUserTable)
            SchemaUtils.createMissingTablesAndColumns(ClientUserTable)

            SchemaUtils.create(CityTable)
            SchemaUtils.createMissingTablesAndColumns(CityTable)

            SchemaUtils.create(CafeTable)
            SchemaUtils.createMissingTablesAndColumns(CafeTable)

            SchemaUtils.create(StreetTable)
            SchemaUtils.createMissingTablesAndColumns(StreetTable)

            SchemaUtils.create(CategoryTable)
            SchemaUtils.createMissingTablesAndColumns(CategoryTable)

            SchemaUtils.create(MenuProductTable)
            SchemaUtils.createMissingTablesAndColumns(MenuProductTable)

            SchemaUtils.create(MenuProductCategoryTable)
            SchemaUtils.createMissingTablesAndColumns(MenuProductCategoryTable)

            SchemaUtils.create(AddressTable)
            SchemaUtils.createMissingTablesAndColumns(AddressTable)

            SchemaUtils.create(OrderTable)
            SchemaUtils.createMissingTablesAndColumns(OrderTable)

            SchemaUtils.create(OrderProductTable)
            SchemaUtils.createMissingTablesAndColumns(OrderProductTable)

            SchemaUtils.create(RequestTable)
            SchemaUtils.createMissingTablesAndColumns(RequestTable)

            SchemaUtils.create(ClientUserLoginSessionTable)
            SchemaUtils.createMissingTablesAndColumns(ClientUserLoginSessionTable)

            SchemaUtils.create(TestClientUserPhoneTable)
            SchemaUtils.createMissingTablesAndColumns(TestClientUserPhoneTable)

            SchemaUtils.create(CompanyStatisticTable)
            SchemaUtils.createMissingTablesAndColumns(CompanyStatisticTable)

            SchemaUtils.create(CompanyStatisticProductTable)
            SchemaUtils.createMissingTablesAndColumns(CompanyStatisticProductTable)

            SchemaUtils.create(CafeStatisticTable)
            SchemaUtils.createMissingTablesAndColumns(CafeStatisticTable)

            SchemaUtils.create(CafeStatisticProductTable)
            SchemaUtils.createMissingTablesAndColumns(CafeStatisticProductTable)
        }
    }

    suspend fun <T> query(block: () -> T): T {
        return withContext(IO) {
            transaction {
                block()
            }
        }
    }

    private fun getDataSource(): HikariDataSource {
        val databaseUrl = System.getenv(DATABASE_URL).replace(POSTGRES_PREFIX, "")
        val databaseUsernameAndPassword = databaseUrl.split(AT_SIGN_DIVIDER, COLON_DIVIDER)
        val config = HikariConfig().apply {
            driverClassName = System.getenv(JDBC_DRIVER)
            jdbcUrl = JDBC_POSTGRESQL_PREFIX + databaseUrl.split(AT_SIGN_DIVIDER)[1]
            username = databaseUsernameAndPassword[0]
            password = databaseUsernameAndPassword[1]
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }
}