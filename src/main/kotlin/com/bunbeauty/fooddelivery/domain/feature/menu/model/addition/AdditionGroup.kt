package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

data class AdditionGroup(
    val uuid: String,
    val name: String,
    val singleChoice: Boolean,
    val isVisible: Boolean,
    val additions: List<Addition>,
)