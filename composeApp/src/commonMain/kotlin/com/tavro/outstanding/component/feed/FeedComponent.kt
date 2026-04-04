package com.tavro.outstanding.component.feed

import com.arkivanov.decompose.ComponentContext
import com.tavro.outstanding.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FeedComponent(
    componentContext: ComponentContext,
    private val navigator: Navigator,
) : ComponentContext by componentContext {

    private val _state = MutableStateFlow(FeedState())
    val state = _state.asStateFlow()
}

data class FeedState(
    val isLoading: Boolean = false,
)
