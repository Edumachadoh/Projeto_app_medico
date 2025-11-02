package com.up.clinica_digital.presentation.component.top_nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

/**
 * PEDRO:
 * A reusable Top App Bar component that displays a back arrow.
 *
 * This component is designed to be used as a top navigation bar on screens
 * that are not primary destinations (e.g., detail screens).
 * When a [navController] is provided, it displays a back arrow icon button
 * that triggers [NavController.popBackStack] when clicked.
 *
 * @param navController The optional navigation controller. If provided,
 * the back button will be displayed and functional.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(
    navController: NavController? = null,
) {
    TopAppBar(
        title = {
        },
        navigationIcon = {
            if (navController != null){
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
