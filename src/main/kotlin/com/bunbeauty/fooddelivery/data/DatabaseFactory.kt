package com.bunbeauty.fooddelivery.data

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
            SchemaUtils.create(UserTable)
            SchemaUtils.create(ClientUserTable)
            SchemaUtils.create(CityTable)
            SchemaUtils.create(CafeTable)
            SchemaUtils.create(StreetTable)
            SchemaUtils.create(CategoryTable)
            SchemaUtils.create(MenuProductTable)
            SchemaUtils.create(MenuProductCategoryTable)
            SchemaUtils.create(AddressTable)
            SchemaUtils.create(OrderTable)
            SchemaUtils.create(OrderProductTable)
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
            //driverClassName = System.getenv("JDBC_DRIVER")
            jdbcUrl = System.getenv("DATABASE_JDBC_URL")
            username = System.getenv("DATASOURCE_USERNAME")
            password = System.getenv("DATASOURCE_PASSWORD")
            maximumPoolSize = 3
            //isAutoCommit = false
            //transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            //validate()
        }
        return HikariDataSource(config)
    }

}