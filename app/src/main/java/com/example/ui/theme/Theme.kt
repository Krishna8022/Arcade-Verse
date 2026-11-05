package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class AppThemeMode {
    DARK,
    AMOLED,
    LIGHT,
    SYSTEM
}

val CharcoalDarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color(0xFF00363A),
    primaryContainer = Color(0xFF004F55),
    onPrimaryContainer = Color(0xFF6FF6FF),
    secondary = ElectricPurple,
    onSecondary = Color(0xFF38006B),
    secondaryContainer = Color(0xFF550099),
    onSecondaryContainer = Color(0xFFEEDBFF),
    tertiary = CyberPink,
    onTertiary = Color(0xFF5C0028),
    tertiaryContainer = Color(0xFF82003D),
    onTertiaryContainer = Color(0xFFFFD9E2),
    background = DarkCharcoalBg,
    onBackground = Color(0xFFEDEAF5),
    surface = DarkSurface,
    onSurface = Color(0xFFEDEAF5),
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = Color(0xFFCAC4D0),
    outline = Color(0xFF4A4660)
)

val AmoledDarkColorScheme = darkColorScheme(
    primary = NeonCyan,
    onPrimary = Color(0xFF00363A),
    primaryContainer = Color(0xFF003A40),
    onPrimaryContainer = Color(0xFF6FF6FF),
    secondary = ElectricPurple,
    onSecondary = Color(0xFF38006B),
    secondaryContainer = Color(0xFF400078),
    onSecondaryContainer = Color(0xFFEEDBFF),
    tertiary = CyberPink,
    onTertiary = Color(0xFF5C0028),
    tertiaryContainer = Color(0xFF660030),
    onTertiaryContainer = Color(0xFFFFD9E2),
    background = AmoledBg,
    onBackground = Color(0xFFF0F0F5),
    surface = AmoledSurface,
    onSurface = Color(0xFFF0F0F5),
    surfaceVariant = AmoledSurfaceVariant,
    onSurfaceVariant = Color(0xFFC0BAC6),
    outline = Color(0xFF363445)
)

val ArcadeLightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFB2EBF2),
    onPrimaryContainer = Color(0xFF002022),
    secondary = LightSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFF3E5F5),
    onSecondaryContainer = Color(0xFF2A0047),
    tertiary = LightTertiary,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFD9E2),
    onTertiaryContainer = Color(0xFF3F0018),
    background = LightBg,
    onBackground = Color(0xFF161520),
    surface = LightSurface,
    onSurface = Color(0xFF161520),
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E)
)

@Composable
fun ArcadeVerseTheme(
    themeMode: AppThemeMode = AppThemeMode.DARK,
    content: @Composable () -> Unit
) {
    val systemInDark = isSystemInDarkTheme()
    val colorScheme = when (themeMode) {
        AppThemeMode.AMOLED -> AmoledDarkColorScheme
        AppThemeMode.LIGHT -> ArcadeLightColorScheme
        AppThemeMode.DARK -> CharcoalDarkColorScheme
        AppThemeMode.SYSTEM -> if (systemInDark) CharcoalDarkColorScheme else ArcadeLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
