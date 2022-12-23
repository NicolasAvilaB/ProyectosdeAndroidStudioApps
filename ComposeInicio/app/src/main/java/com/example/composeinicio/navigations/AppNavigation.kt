package com.example.composeinicio.navigations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composeinicio.Pantalla2
import com.example.composeinicio.Pantalla3
import com.example.composeinicio.uiscreen.Pantalla1

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = routes.Pantalla1.routes)
    {
        composable(route = routes.Pantalla1.routes){
            Pantalla1(navController, modifier = Modifier)
        }
        composable(route = routes.Pantalla2.routes){
            Pantalla2(navController, modifier = Modifier)
        }
        composable(route = routes.Pantalla3.routes){
            Pantalla3(navController, modifier = Modifier)
        }
    }
}