package com.bunbeauty.fooddelivery.data

import com.bunbeauty.fooddelivery.data.Constants.DATABASE_JDBC_URL
import com.bunbeauty.fooddelivery.data.Constants.DATASOURCE_PASSWORD
import com.bunbeauty.fooddelivery.data.Constants.DATASOURCE_USERNAME
import com.bunbeauty.fooddelivery.data.Constants.JDBC_DRIVER
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
        val config = HikariConfig().apply {
            driverClassName = System.getenv(JDBC_DRIVER)
            jdbcUrl = System.getenv(DATABASE_JDBC_URL)
            username = System.getenv(DATASOURCE_USERNAME)
            password = System.getenv(DATASOURCE_PASSWORD)
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }

}