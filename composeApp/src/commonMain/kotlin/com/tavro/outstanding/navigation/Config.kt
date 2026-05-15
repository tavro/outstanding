package com.tavro.outstanding.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface BaseConfig {
    val debugName: String
}

@Serializable
sealed class Config(override val debugName: String) : BaseConfig {
    @Serializable
    data class Main(val tab: Tab = Tab.Home) : Config("Main") {
        data class Result(
            val tab: Tab = Tab.Home,
        )

        enum class Tab { Home, Profile }
    }

    @Serializable
    data class Error(val message: String, val stacktrace: String?) : Config("Error")

    object Settings {
        // TODO
    }

    @Serializable
    sealed class Onboarding(
        private val internalDebugName: String
    ) : Config("Onboarding$internalDebugName") {
        @Serializable
        data object Login : Onboarding("Login")
    }

}
