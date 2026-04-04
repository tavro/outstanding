package com.tavro.outstanding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.defaultComponentContext
import com.tavro.outstanding.component.RootComponent
import com.tavro.outstanding.di.appModule
import com.tavro.outstanding.navigation.Navigator
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(applicationContext)
            modules(appModule)
        }

        val navigator = Navigator()
        val rootComponent = RootComponent(
            componentContext = defaultComponentContext(),
            navigator = navigator,
        )

        setContent {
            App(rootComponent)
        }
    }
}
