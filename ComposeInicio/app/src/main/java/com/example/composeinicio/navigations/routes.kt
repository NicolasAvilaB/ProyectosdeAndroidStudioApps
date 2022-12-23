package com.example.composeinicio.navigations

sealed class routes(var routes: String){
    object Pantalla1: routes("pantalla1")
    object Pantalla2: routes("pantalla2")
    object Pantalla3: routes("pantalla3")
}
