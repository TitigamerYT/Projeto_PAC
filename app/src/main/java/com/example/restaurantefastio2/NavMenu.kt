package com.example.restaurantefastio2

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.restaurantefastio2.ui.theme.RestauranteFastio2Theme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavMenu() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "TelaMesa") {
        composable("TelaMesa") { TelaMesa(navController) }
        composable(
            "TelaMenu/{mesaNumber}",
            arguments = listOf(navArgument("mesaNumber") { defaultValue = "1" })
        ) { backStackEntry ->
            val mesaNumber = backStackEntry.arguments?.getString("mesaNumber")?.toIntOrNull() ?: 1
            TelaMenu(navController, mesaNumber)
        }
        composable(
            "TelaEntradas/{mesaNumber}",
            arguments = listOf(navArgument("mesaNumber") { defaultValue = "1" })
        ) { backStackEntry ->
            val mesaNumber = backStackEntry.arguments?.getString("mesaNumber")?.toIntOrNull() ?: 1
            TelaEntradas(navController, mesaNumber)
        }
        composable(
            "TelaBebidas/{mesaNumber}",
            arguments = listOf(navArgument("mesaNumber") { defaultValue = "1" })
        ) { backStackEntry ->
            val mesaNumber = backStackEntry.arguments?.getString("mesaNumber")?.toIntOrNull() ?: 1
            TelaBebidas(navController, mesaNumber)
        }
        composable(
            "TelaPratoCarne/{mesaNumber}",
            arguments = listOf(navArgument("mesaNumber") { defaultValue = "1" })
        ) { backStackEntry ->
            val mesaNumber = backStackEntry.arguments?.getString("mesaNumber")?.toIntOrNull() ?: 1
            TelaPratoCarne(navController, mesaNumber)
        }
        composable(
            "TelaPratoPeixe/{mesaNumber}",
            arguments = listOf(navArgument("mesaNumber") { defaultValue = "1" })
        ) { backStackEntry ->
            val mesaNumber = backStackEntry.arguments?.getString("mesaNumber")?.toIntOrNull() ?: 1
            TelaPratoPeixe(navController, mesaNumber)
        }
        composable(
            "TelaOutros/{mesaNumber}",
            arguments = listOf(navArgument("mesaNumber") { defaultValue = "1" })
        ) { backStackEntry ->
            val mesaNumber = backStackEntry.arguments?.getString("mesaNumber")?.toIntOrNull() ?: 1
            TelaOutros(navController, mesaNumber)
        }
        composable(
            "TelaSobremesas/{mesaNumber}",
            arguments = listOf(navArgument("mesaNumber") { defaultValue = "1" })
        ) { backStackEntry ->
            val mesaNumber = backStackEntry.arguments?.getString("mesaNumber")?.toIntOrNull() ?: 1
            TelaSobremesas(navController, mesaNumber)
        }
        composable(
            "TelaConta/{mesaNumber}",
            arguments = listOf(navArgument("mesaNumber") { defaultValue = "1" })
        ) { backStackEntry ->
            val mesaNumber = backStackEntry.arguments?.getString("mesaNumber")?.toIntOrNull() ?: 1
            TelaConta(navController, mesaNumber)
        }
        composable("TelaTodasAsContas") { TelaTodasAsContas(navController) }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewNavMenu() {
    RestauranteFastio2Theme {
        NavMenu()
    }
}





