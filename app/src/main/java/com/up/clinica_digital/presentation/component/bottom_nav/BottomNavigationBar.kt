package com.up.clinica_digital.presentation.component.bottom_nav

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    selectedRoute: String,
    onItemSelected: (BottomNavItem) -> Unit
) {
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedRoute == item.route,
                onClick = { onItemSelected(item) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
