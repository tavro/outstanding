package com.tavro.outstanding.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.CoroutineScope
import org.koin.compose.getKoin

@Composable
fun rememberNavigator(): Navigator {
    val koin = getKoin()
    return remember { koin.get<Navigator>() }
}

class Navigator internal constructor(
    private val stackNavigation: StackNavigation<Config>,
    // TODO: slotNavigator: SlotNavigation<SlotConfig>,
    mainScope: CoroutineScope,
    // TODO: private val configuration: Configuration, our custom Configuration class
    // TODO: deepLinkParser
) : StackNavigation<Config> by stackNavigation {
    private var childStack: Value<ChildStack<Config, BaseComponent>>? = null
    fun subscribeToChildStack(childStack: Value<ChildStack<Config, BaseComponent>>) {
        this.childStack = childStack
    }

    fun deliver(value: Any) {
        childStack?.active?.instance?.accept(value)
    }

    fun popWithResult(result: Any) {
        pop { if (it) deliver(result) }
    }

    fun popWhileWithResult(predicate: (Config) -> Boolean, result: Any) {
        popWhile(predicate = predicate, onComplete = { deliver(result) })
    }
}

@Suppress("SpreadOperator")
fun Navigator.navigateToStack(stack: List<Config>) {
    replaceAll(*stack.toTypedArray())
}
