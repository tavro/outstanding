package com.tavro.outstanding.component.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.tavro.outstanding.component.create.CreateComponent
import com.tavro.outstanding.component.feed.FeedComponent
import com.tavro.outstanding.component.map.MapComponent
import com.tavro.outstanding.component.profile.ProfileComponent
import com.tavro.outstanding.navigation.Config
import com.tavro.outstanding.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainComponent(
    componentContext: ComponentContext,
    private val navigator: Navigator,
) : ComponentContext by componentContext {

    private val _activeTab = MutableStateFlow(Config.Tab.Map)
    val activeTab = _activeTab.asStateFlow()

    val mapComponent = MapComponent(childContext("map"), navigator)
    val feedComponent = FeedComponent(childContext("feed"), navigator)
    val createComponent = CreateComponent(childContext("create"), navigator)
    val profileComponent = ProfileComponent(childContext("profile"), navigator)

    fun selectTab(tab: Config.Tab) {
        _activeTab.value = tab
    }
}
