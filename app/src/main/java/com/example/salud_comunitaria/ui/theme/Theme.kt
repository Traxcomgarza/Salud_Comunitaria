package com.example.salud_comunitaria.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.data_core.repository.ThemeMode

private val DarkColorScheme = darkColorScheme(
    primary = AzulPrimary,
    onPrimary = OnPrimaryDark,
    primaryContainer = Color(0xFF2B4F80),
    onPrimaryContainer = OnDark,

    secondary = AzulSecondary,
    onSecondary = OnDark,
    secondaryContainer = Color(0xFF2C446B),
    onSecondaryContainer = OnDark,

    tertiary = AzulTertiary,
    onTertiary = OnDark,

    background = DarkBackground,
    onBackground = OnDark,
    surface = DarkSurface,
    onSurface = OnDark
)

private val LightColorScheme = lightColorScheme(
    primary = AzulPrimary,
    onPrimary = OnPrimaryLight,
    primaryContainer = Color(0xFFD6E9FF),
    onPrimaryContainer = OnLight,

    secondary = AzulSecondary,
    onSecondary = OnPrimaryLight,
    secondaryContainer = Color(0xFFD7DFEF),
    onSecondaryContainer = OnLight,

    tertiary = AzulTertiary,
    onTertiary = OnPrimaryLight,

    background = LightBackground,
    onBackground = OnLight,
    surface = LightSurface,
    onSurface = OnLight
)

@Composable
fun Salud_ComunitariaTheme(
    themeMode: ThemeMode,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val darkTheme = themeMode == ThemeMode.DARK
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}