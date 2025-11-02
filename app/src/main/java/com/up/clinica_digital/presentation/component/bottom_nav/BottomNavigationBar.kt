package com.up.clinica_digital.presentation.component.bottom_nav

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
/**
 * A reusable composable that displays the main bottom navigation bar for the app.
 *
 * It takes a list of [BottomNavItem] and highlights the one
 * corresponding to the [selectedRoute].
 *
 * @param items The list of [BottomNavItem] to display.
 * @param selectedRoute The route string of the currently selected screen.
 * @param onItemSelected A callback function invoked when a navigation item is clicked.
 */
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
