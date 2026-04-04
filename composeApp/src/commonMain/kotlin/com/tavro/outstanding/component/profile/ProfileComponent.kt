package com.tavro.outstanding.component.profile

import com.arkivanov.decompose.ComponentContext
import com.tavro.outstanding.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileComponent(
    componentContext: ComponentContext,
    private val navigator: Navigator,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()
}

data class ProfileState(
    val isLoading: Boolean = false,
)
