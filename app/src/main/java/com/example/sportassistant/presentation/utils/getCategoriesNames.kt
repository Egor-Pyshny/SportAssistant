package com.example.sportassistant.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.sportassistant.R
import com.example.sportassistant.domain.model.CategoryModel
import java.util.UUID

@Composable
fun getCategoryText(categoryId: UUID?, categories: List<CategoryModel>): String {
    if (categoryId == null) {
        return stringResource(R.string.category_name_placeholder)
    }
    val category = categories.find { it.id == categoryId }
    return category?.name ?: "ERROR"
}
