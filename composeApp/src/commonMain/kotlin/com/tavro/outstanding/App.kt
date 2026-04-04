package com.tavro.outstanding

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.tavro.outstanding.component.RootComponent
import com.tavro.outstanding.component.main.MainScreen

@Composable
fun App(rootComponent: RootComponent) {
    MaterialTheme {
        val stack by rootComponent.stack.subscribeAsState()

        Children(stack) { child ->
            when (val instance = child.instance) {
                is RootComponent.Child.Main -> MainScreen(instance.component)
                RootComponent.Child.Settings -> Unit // TODO
            }
        }
    }
}
