package com.tavro.outstanding.component.create

import com.arkivanov.decompose.ComponentContext
import com.tavro.outstanding.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateComponent(
    componentContext: ComponentContext,
    private val navigator: Navigator,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(CreateState())
    val state = _state.asStateFlow()
}

data class CreateState(
    val isLoading: Boolean = false,
)
