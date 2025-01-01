package com.bunbeauty.fooddelivery.domain.feature.menu.model.addition

data class MenuProductAdditionGroup(
    val uuid: String,
    val name: String,
    val singleChoice: Boolean,
    val priority: Int,
    val isVisible: Boolean,
    val additions: List<MenuProductAddition>,
)