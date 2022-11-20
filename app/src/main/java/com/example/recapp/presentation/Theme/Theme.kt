package com.example.recapp.presentation.Theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = Blue600,
    primaryVariant = Blue400,
    onPrimary = Black2,
    secondary = Color.White,
    secondaryVariant = Color.LightGray,
    onSecondary = Black1,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Black2,

)

private val DarkThemeColors = darkColors(
    primary = Blue700,
    primaryVariant = Color.White,
    onPrimary = Color.White,
    secondary = Black1,
    onSecondary = Color.White,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = Black1,
    onSurface = Color.White,
    secondaryVariant= Black1


)

@Composable
fun AppTheme(
    darkTheme:Boolean,
    content: @Composable () -> Unit,

){
    MaterialTheme(
        colors = if(darkTheme) DarkThemeColors else LightThemeColors
    ){
        content() //Now every composable will be updated either with Light or Dark theme
    }
}