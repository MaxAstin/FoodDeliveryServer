package com.bunbeauty.fooddelivery.data.features.menu.cache

import com.bunbeauty.fooddelivery.core.data.BaseCache
import com.bunbeauty.fooddelivery.domain.feature.menu.model.menuproduct.MenuProduct
import java.util.*

class MenuProductCatch : BaseCache<UUID, List<MenuProduct>>()
