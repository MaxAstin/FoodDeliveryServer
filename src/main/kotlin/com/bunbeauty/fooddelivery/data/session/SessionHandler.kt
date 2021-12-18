package com.bunbeauty.fooddelivery.data.session

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SessionHandler<T> {

    private val sessionMap: LinkedHashMap<String, Session<T>> = LinkedHashMap()

    suspend fun emitNewValue(key: String, value: T) {
        sessionMap[key]?.mutableSharedFlow?.emit(value)
    }

    fun connect(key: String): SharedFlow<T> {
        val existedSession = sessionMap[key]
        return if (existedSession == null) {
            val session = Session<T>()
            sessionMap[key] = session

            session.mutableSharedFlow.asSharedFlow()
        } else {
            existedSession.mutableSharedFlow.asSharedFlow()
        }
    }

    fun disconnect(key: String) {
        sessionMap[key]?.let { session ->
            if (session.count.get() == 1) {
                println("session $key removed")
                sessionMap.remove(key)
            } else {
                session.count.decrementAndGet()
                println("session $key count decreased to ${session.count.get()}")
            }
        }
    }
}