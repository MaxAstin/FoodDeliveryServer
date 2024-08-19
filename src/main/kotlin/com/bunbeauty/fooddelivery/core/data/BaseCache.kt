package com.bunbeauty.fooddelivery.core.data

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

abstract class BaseCache<K, V> {

    private val cacheMap: ConcurrentMap<K, V> = ConcurrentHashMap()

    fun setCache(key: K, value: V) {
        cacheMap[key] = value
    }

    fun clearCache(key: K) {
        cacheMap[key] = null
    }

    fun getCache(key: K): V? {
        return cacheMap[key]
    }

}