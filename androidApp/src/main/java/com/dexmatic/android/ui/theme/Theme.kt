// File: androidApp/src/main/java/com/dexmatic/android/ui/theme/Theme.kt

package com.dexmatic.android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = White,
    surface = White,
    onPrimary = Black,
    onSecondary = Black,
    onBackground = Black,
    onSurface = Black,
)

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Black,
    surface = Black,
    onPrimary = Black,
    onSecondary = Black,
    onBackground = White,
    onSurface = White,
)

@Composable
fun DexMaticTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
