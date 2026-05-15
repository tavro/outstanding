package com.tavro.outstanding.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import com.arkivanov.decompose.router.stack.pushNew
import com.tavro.outstanding.component.login.LoginComponent
import com.tavro.outstanding.component.login.LoginError
import com.tavro.outstanding.component.login.LoginScreenState
import com.tavro.outstanding.designsystem.components.JadeFilledButton
import com.tavro.outstanding.designsystem.components.JadeScaffold
import com.tavro.outstanding.navigation.Config
import com.tavro.outstanding.navigation.Navigator
import com.tavro.outstanding.navigation.rememberNavigator

// TODO: Move
@Composable
fun scaffoldContentWindowInsets(): WindowInsets = WindowInsets.navigationBars

// TODO: Move
sealed interface UserMessage {
    val text: String
    data class Info(override val text: String, val action: SnackbarData.Action? = null) : UserMessage
    data class Error(override val text: String, val cause: Throwable? = null) : UserMessage
}

// TODO: Move
@Immutable
data class SnackbarData(
    val message: String?,
    val action: Action? = null,
    val onShown: () -> Unit,
) {
    data class Action(val label: String, val onClick: () -> Unit)
}

@Composable
fun bindDataToSnackbarHost(data: SnackbarData?): SnackbarHostState {
    val snackbarHostState = remember { SnackbarHostState() }
    val message = data?.message
    if (message != null) {
        val action = data.action
        LaunchedEffect(message) {
            val res = snackbarHostState.showSnackbar(
                message = message,
                actionLabel = action?.label,
                duration = if (action != null) SnackbarDuration.Long else SnackbarDuration.Short
            )
            when (res) {
                SnackbarResult.Dismissed -> Unit
                SnackbarResult.ActionPerformed -> {
                    action?.onClick?.invoke()
                }
            }
            data.onShown()
        }
    }
    return snackbarHostState
}

// TODO: Move
fun Navigator.navigateToErrorDetails(error: UserMessage.Error) {
    val stacktrace = error.cause?.stackTraceToString()
    pushNew(
        Config.Error(
            message = error.cause?.message ?: error.text,
            stacktrace = stacktrace
        )
    )
}

// TODO: Move
typealias OnErrorDetailsClick = (error: UserMessage.Error) -> Unit
expect fun Throwable.isNetworkError(): Boolean
@Composable
fun bindMessageToSnackbarHostState(
    message: UserMessage?,
    onMessageShown: () -> Unit,
    onErrorDetailsClick: OnErrorDetailsClick?
) : SnackbarHostState {
    val messageText = message?.text
    if (messageText != null && message is UserMessage.Error) {
        LaunchedEffect(messageText) {
            // TODO: Use Kermin logger
            println("Message=" + messageText + " Throwable=" + message.cause)
        }
    }
    val action = if (message is UserMessage.Error && message.cause?.isNetworkError() == true)
        null
    else if (message is UserMessage.Error && onErrorDetailsClick != null) SnackbarData.Action(
        label = "Details", // TODO: String resources
        onClick = { onErrorDetailsClick(message) }
    ) else if (message is UserMessage.Info && message.action != null) SnackbarData.Action(
        label = message.action.label,
        onClick = message.action.onClick
    ) else null
    return bindDataToSnackbarHost(
        data = SnackbarData(
            message = messageText,
            onShown = onMessageShown,
            action = action
        )
    )
}
@Composable
fun bindErrorToSnackbarHostState(
    error: UserMessage.Error?,
    onErrorShown: () -> Unit,
    onErrorDetailsClick: OnErrorDetailsClick?
): SnackbarHostState = bindMessageToSnackbarHostState(
    message = error,
    onMessageShown = onErrorShown,
    onErrorDetailsClick = onErrorDetailsClick
)

@Composable
fun LoginScreen(
    component: LoginComponent,
    modifier: Modifier = Modifier
) {
    val state = component.state.collectAsState().value
    val error = if (state is LoginScreenState.Failure) UserMessage.Error(
        text = "Failed to sign in",
        cause = state.cause
    ) else null
    val navigator = rememberNavigator()
    val snackbarHostState = bindErrorToSnackbarHostState(
        error = error,
        onErrorShown = component::onErrorShown,
        onErrorDetailsClick = navigator::navigateToErrorDetails
    )

    JadeScaffold(
        modifier = modifier,
        contentWindowInsets = scaffoldContentWindowInsets(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        content = { contentPadding ->
            LoginScreenContent(
                loginState = state,
                onLogin = component::onLogin,
                onLoginFailed = component::failedToLogin,
                onLoginClicked = component::onLoginStarted,
                contentPadding = contentPadding,
                modifier = Modifier.fillMaxSize()
            )
        }
    )
}

@Composable
internal fun LoginScreenContent(
    loginState: LoginScreenState,
    onLogin: () -> Unit,
    onLoginFailed: (error: LoginError) -> Unit,
    onLoginClicked: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues.Zero,
) {
    Box(modifier = modifier) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
        ) {
            // TODO: Input fields
            JadeFilledButton( // TODO: Turn into state button LoginButton
                text = "Login", // TODO: Add to string resources
                onClick = onLoginClicked,
                enabled = loginState is LoginScreenState.Initial,
                minFontSize = TextUnit.Unspecified
            )
        }
    }
}
