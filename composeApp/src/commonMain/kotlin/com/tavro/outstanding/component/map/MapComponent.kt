package com.tavro.outstanding.component.map

import com.arkivanov.decompose.ComponentContext
import com.tavro.outstanding.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapComponent(
    componentContext: ComponentContext,
    private val navigator: Navigator,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()
}

data class MapState(
    val isLoading: Boolean = false,
)
