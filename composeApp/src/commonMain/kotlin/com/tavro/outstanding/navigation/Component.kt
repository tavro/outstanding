package com.tavro.outstanding.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface BaseComponent {
    fun accept(value: Any) {}
}

interface Component : BaseComponent {
    @Composable
    fun Render(modifier: Modifier)
}
