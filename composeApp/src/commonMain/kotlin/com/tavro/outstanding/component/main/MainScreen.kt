package com.tavro.outstanding.component.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tavro.outstanding.component.create.CreateScreen
import com.tavro.outstanding.component.feed.FeedScreen
import com.tavro.outstanding.component.map.MapScreen
import com.tavro.outstanding.component.profile.ProfileScreen
import com.tavro.outstanding.navigation.Config

@Composable
fun MainScreen(component: MainComponent) {
    val activeTab by component.activeTab.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            NavigationBar {
                Config.Tab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = activeTab == tab,
                        onClick = { component.selectTab(tab) },
                        icon = { Icon(tab.icon, contentDescription = tab.label) },
                        label = { Text(tab.label) },
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (activeTab) {
                Config.Tab.Map -> MapScreen(component.mapComponent)
                Config.Tab.Feed -> FeedScreen(component.feedComponent)
                Config.Tab.Create -> CreateScreen(component.createComponent)
                Config.Tab.Profile -> ProfileScreen(component.profileComponent)
            }
        }
    }
}

private val Config.Tab.icon: ImageVector
    get() = when (this) {
        Config.Tab.Map -> Icons.Default.Place
        Config.Tab.Feed -> Icons.Default.Home
        Config.Tab.Create -> Icons.Default.Add
        Config.Tab.Profile -> Icons.Default.AccountCircle
    }
