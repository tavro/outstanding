package com.tavro.outstanding.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Config {
    @Serializable
    data class Main(val tab: Tab = Tab.Map) : Config

    @Serializable
    sealed interface Settings : Config {
        @Serializable
        data object Main : Settings
    }

    enum class Tab(val label: String) {
        Map("Map"),
        Feed("Feed"),
        Create("Create"),
        Profile("Profile"),
    }
}
