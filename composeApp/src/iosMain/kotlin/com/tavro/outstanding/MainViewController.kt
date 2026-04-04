package com.tavro.outstanding

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.tavro.outstanding.component.RootComponent
import com.tavro.outstanding.navigation.Navigator

fun MainViewController() = ComposeUIViewController {
    val lifecycle = LifecycleRegistry()
    val navigator = Navigator()
    val rootComponent = RootComponent(
        componentContext = DefaultComponentContext(lifecycle),
        navigator = navigator,
    )
    lifecycle.resume()
    App(rootComponent)
}
