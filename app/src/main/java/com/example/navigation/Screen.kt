package com.example.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SportsEsports
import androidx.compose.material.icons.outlined.Whatshot
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val filledIcon: ImageVector,
    val outlinedIcon: ImageVector,
    val testTag: String
) {
    object Home : Screen(
        route = "home",
        title = "Home",
        filledIcon = Icons.Filled.Home,
        outlinedIcon = Icons.Outlined.Home,
        testTag = "nav_home"
    )

    object Games : Screen(
        route = "games",
        title = "Games",
        filledIcon = Icons.Filled.SportsEsports,
        outlinedIcon = Icons.Outlined.SportsEsports,
        testTag = "nav_games"
    )

    object Challenges : Screen(
        route = "challenges",
        title = "Quests",
        filledIcon = Icons.Filled.Whatshot,
        outlinedIcon = Icons.Outlined.Whatshot,
        testTag = "nav_challenges"
    )

    object Achievements : Screen(
        route = "achievements",
        title = "Trophies",
        filledIcon = Icons.Filled.EmojiEvents,
        outlinedIcon = Icons.Outlined.EmojiEvents,
        testTag = "nav_achievements"
    )

    object Profile : Screen(
        route = "profile",
        title = "Profile",
        filledIcon = Icons.Filled.Person,
        outlinedIcon = Icons.Outlined.Person,
        testTag = "nav_profile"
    )

    object Settings : Screen(
        route = "settings",
        title = "Settings",
        filledIcon = Icons.Filled.Settings,
        outlinedIcon = Icons.Outlined.Settings,
        testTag = "nav_settings"
    )

    companion object {
        val bottomNavItems = listOf(
            Home,
            Games,
            Challenges,
            Achievements,
            Profile,
            Settings
        )
    }
}
