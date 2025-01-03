package com.bunbeauty.fooddelivery.data.session

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asSharedFlow

class SessionHandler<T> {

    private val sessionMap: LinkedHashMap<String, Session<T>> = LinkedHashMap()

    suspend fun emitNewValue(key: String?, value: T?) {
        if (key != null && value != null) {
            sessionMap[key]?.mutableSharedFlow?.emit(value)
        }
    }

    fun connect(key: String): Flow<T> {
        println("connect to session $key")
        val existedSession = sessionMap[key]
        return if (existedSession == null) {
            val session = Session<T>()
            sessionMap[key] = session

            println("session $key created")
            session.mutableSharedFlow.asSharedFlow()
        } else {
            val newCount = existedSession.count.incrementAndGet()
            println("session $key count increment $newCount")
            existedSession.mutableSharedFlow.asSharedFlow()
        }
    }

    fun disconnect(key: String) {
        println("disconnect from session $key")
        sessionMap[key]?.let { session ->
            if (session.count.get() == 1) {
                sessionMap.remove(key)
                println("session $key removed")
            } else {
                val newCount = session.count.decrementAndGet()
                println("session $key count decrement $newCount")
            }
        }
    }
}
