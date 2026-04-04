package com.tavro.outstanding.navigation

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.navigate

class Navigator {
    val stack = StackNavigation<Config>()

    fun navigateTo(config: Config) = stack.navigate { listOf(config) }
    fun popToMain(tab: Config.Tab = Config.Tab.Map) = stack.navigate { listOf(Config.Main(tab)) }
    fun pop() = stack.navigate { it.dropLast(1).ifEmpty { listOf(Config.Main()) } }
}
