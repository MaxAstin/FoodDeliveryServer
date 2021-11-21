package com.bunbeauty.food_delivery.data

import com.bunbeauty.food_delivery.data.table.*
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
            SchemaUtils.create(OrderTable)
            SchemaUtils.create(CityTable)
            SchemaUtils.create(CafeTable)
            SchemaUtils.create(CategoryTable)
            SchemaUtils.create(MenuProductTable)
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
            driverClassName = System.getenv("JDBC_DRIVER")
            jdbcUrl = System.getenv("DATABASE_URL")
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }

}