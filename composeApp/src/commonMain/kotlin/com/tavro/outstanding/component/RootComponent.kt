package com.tavro.outstanding.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.tavro.outstanding.component.main.MainComponent
import com.tavro.outstanding.navigation.Config
import com.tavro.outstanding.navigation.Navigator
import kotlinx.serialization.serializer

class RootComponent(
    componentContext: ComponentContext,
    private val navigator: Navigator,
) : ComponentContext by componentContext {

    val stack: Value<ChildStack<Config, Child>> = childStack(
        source = navigator.stack,
        serializer = serializer(),
        initialConfiguration = Config.Main(),
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(config: Config, context: ComponentContext): Child = when (config) {
        is Config.Main -> Child.Main(MainComponent(context, navigator))
        is Config.Settings.Main -> Child.Settings
    }

    sealed interface Child {
        class Main(val component: MainComponent) : Child
        data object Settings : Child
    }
}
