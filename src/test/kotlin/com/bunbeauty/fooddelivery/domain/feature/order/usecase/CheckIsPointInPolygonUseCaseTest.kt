package com.bunbeauty.fooddelivery.domain.feature.order.usecase

import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CheckIsPointInPolygonUseCaseTest {

    private lateinit var checkIsPointInPolygonUseCase: CheckIsPointInPolygonUseCase

    @BeforeTest
    fun setup() {
        checkIsPointInPolygonUseCase = CheckIsPointInPolygonUseCase()
    }

    /**
     *   •
     * • × •
     *   •
     */
    @Test
    fun `returns true if point inside convex polygon`() {
        val latitude = 1.0
        val longitude = 1.0
        val polygon = listOf(
            0.0 to 1.0,
            1.0 to 2.0,
            2.0 to 1.0,
            1.0 to 0.0
        )

        val result = checkIsPointInPolygonUseCase(
            latitude = latitude,
            longitude = longitude,
            polygon = polygon
        )

        assertTrue(result)
    }

    /**
     *   •   •
     * •   •
     *   ×
     */
    @Test
    fun `returns false if point outside convex polygon`() {
        val latitude = 1.0
        val longitude = 0.0
        val polygon = listOf(
            0.0 to 1.0,
            1.0 to 2.0,
            3.0 to 2.0,
            2.0 to 1.0
        )

        val result = checkIsPointInPolygonUseCase(
            latitude = latitude,
            longitude = longitude,
            polygon = polygon
        )

        assertFalse(result)
    }

    /**
     * •     •
     *     ×
     *   •
     * •   •
     */
    @Test
    fun `returns true if point inside concave polygon`() {
        val latitude = 2.0
        val longitude = 2.0
        val polygon = listOf(
            0.0 to 0.0,
            0.0 to 3.0,
            3.0 to 3.0,
            2.0 to 0.0,
            1.0 to 1.0
        )

        val result = checkIsPointInPolygonUseCase(
            latitude = latitude,
            longitude = longitude,
            polygon = polygon
        )

        assertTrue(result)
    }

    /**
     * •     •
     *   •
     *   ×
     * •   •
     */
    @Test
    fun `returns false if point outside concave polygon`() {
        val latitude = 1.0
        val longitude = 1.0
        val polygon = listOf(
            0.0 to 0.0,
            0.0 to 3.0,
            3.0 to 3.0,
            2.0 to 0.0,
            1.0 to 2.0
        )

        val result = checkIsPointInPolygonUseCase(
            latitude = latitude,
            longitude = longitude,
            polygon = polygon
        )

        assertFalse(result)
    }

    /**
     * •   •    •
     *     •
     *   •   •
     *   × •
     * •        •
     */
    @Test
    fun `returns true if point inside polygon with a hole`() {
        val latitude = 1.0
        val longitude = 1.0
        val polygon = listOf(
            0.0 to 0.0,
            0.0 to 4.0,
            2.0 to 4.0,
            2.0 to 3.0,
            1.0 to 2.0,
            2.0 to 1.0,
            3.0 to 3.0,
            2.0 to 3.0,
            2.0 to 4.0,
            4.0 to 4.0,
            0.0 to 4.0
        )

        val result = checkIsPointInPolygonUseCase(
            latitude = latitude,
            longitude = longitude,
            polygon = polygon
        )

        assertFalse(result)
    }

    /**
     * •   •    •
     *     •
     *   • × •
     *     •
     * •        •
     */
    @Test
    fun `returns false if point outside polygon with a hole`() {
        val latitude = 2.0
        val longitude = 2.0
        val polygon = listOf(
            0.0 to 0.0,
            0.0 to 4.0,
            2.0 to 4.0,
            2.0 to 3.0,
            1.0 to 2.0,
            2.0 to 1.0,
            3.0 to 3.0,
            2.0 to 3.0,
            2.0 to 4.0,
            4.0 to 4.0,
            0.0 to 4.0
        )

        val result = checkIsPointInPolygonUseCase(
            latitude = latitude,
            longitude = longitude,
            polygon = polygon
        )

        assertFalse(result)
    }
}
