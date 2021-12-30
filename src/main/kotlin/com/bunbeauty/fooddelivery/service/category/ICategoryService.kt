package com.bunbeauty.fooddelivery.service.category

import com.bunbeauty.fooddelivery.data.model.category.GetCategory
import com.bunbeauty.fooddelivery.data.model.category.PatchCategory
import com.bunbeauty.fooddelivery.data.model.category.PostCategory

interface ICategoryService {

    suspend fun createCategory(postCategory: PostCategory, creatorUuid: String): GetCategory?
    suspend fun updateCategory(categoryUuid: String, patchCategory: PatchCategory): GetCategory?
    suspend fun getCategoryListByCompanyUuid(companyUuid: String): List<GetCategory>
}