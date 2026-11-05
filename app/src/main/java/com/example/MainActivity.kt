package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.GameModel
import com.example.navigation.Screen
import com.example.ui.MainUiState
import com.example.ui.MainViewModel
import com.example.ui.achievements.AchievementsScreen
import com.example.ui.challenges.ChallengesScreen
import com.example.ui.components.ArcadeBottomBar
import com.example.ui.components.ArcadeTopBar
import com.example.ui.components.StatBadge
import com.example.ui.games.GamesScreen
import com.example.ui.home.HomeScreen
import com.example.ui.profile.ProfileScreen
import com.example.ui.settings.SettingsScreen
import com.example.ui.theme.ArcadeVerseTheme
import com.example.ui.theme.CoinGold
import com.example.ui.theme.NeonCyan

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val mainViewModel: MainViewModel = viewModel()
            val uiState by mainViewModel.uiState.collectAsStateWithLifecycle()

            ArcadeVerseTheme(themeMode = uiState.themeMode) {
                MainAppScaffold(
                    uiState = uiState,
                    viewModel = mainViewModel
                )
            }
        }
    }
}

@Composable
fun MainAppScaffold(
    uiState: MainUiState,
    viewModel: MainViewModel
) {
    // BackHandler: If on any secondary screen, navigate back to Home
    if (uiState.currentScreen != Screen.Home) {
        BackHandler {
            viewModel.navigateTo(Screen.Home)
        }
    }

    // BackHandler: If game modal or active game is open, close it
    if (uiState.selectedGameId != null) {
        BackHandler {
            viewModel.closeGame()
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("arcade_scaffold"),
        topBar = {
            ArcadeTopBar(
                playerProfile = uiState.playerProfile,
                onProfileClick = { viewModel.navigateTo(Screen.Profile) }
            )
        },
        bottomBar = {
            ArcadeBottomBar(
                currentScreen = uiState.currentScreen,
                onNavigate = { viewModel.navigateTo(it) }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (uiState.currentScreen) {
                Screen.Home -> HomeScreen(
                    uiState = uiState,
                    onPlayGame = { viewModel.openGame(it) },
                    onToggleFavorite = { viewModel.toggleFavorite(it) },
                    onCategorySelect = { viewModel.selectCategory(it) },
                    onClaimChallenge = { viewModel.claimDailyChallenge(it) },
                    onNavigate = { viewModel.navigateTo(it) }
                )

                Screen.Games -> GamesScreen(
                    uiState = uiState,
                    onPlayGame = { viewModel.openGame(it) },
                    onToggleFavorite = { viewModel.toggleFavorite(it) },
                    onCategorySelect = { viewModel.selectCategory(it) },
                    onSearchChange = { viewModel.updateSearchQuery(it) }
                )

                Screen.Challenges -> ChallengesScreen(
                    uiState = uiState,
                    onClaimChallenge = { viewModel.claimDailyChallenge(it) }
                )

                Screen.Achievements -> AchievementsScreen(
                    uiState = uiState
                )

                Screen.Profile -> ProfileScreen(
                    uiState = uiState,
                    onUpdateNickname = { viewModel.updatePlayerNickname(it) }
                )

                Screen.Settings -> SettingsScreen(
                    uiState = uiState,
                    onMusicVolumeChange = { viewModel.updateMusicVolume(it) },
                    onSoundFxVolumeChange = { viewModel.updateSoundFxVolume(it) },
                    onVibrationToggle = { viewModel.toggleVibration(it) },
                    onThemeSelect = { viewModel.setThemeMode(it) },
                    onReducedAnimationsToggle = { viewModel.toggleReducedAnimations(it) },
                    onResetProgress = { viewModel.resetProgress() }
                )
            }

            // Game Launch Readiness Modal (Stage 1 bridge to Stage 2 games)
            val selectedGame = uiState.games.find { it.id == uiState.selectedGameId }
            if (selectedGame != null) {
                GameLaunchModal(
                    game = selectedGame,
                    onDismiss = { viewModel.closeGame() }
                )
            }
        }
    }
}

@Composable
fun GameLaunchModal(
    game: GameModel,
    onDismiss: () -> Unit
) {
    val accentColor = Color(game.accentColorHex)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = game.title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.onBackground
            )
        },
        text = {
            Column {
                StatBadge(text = game.category.displayName, badgeColor = accentColor)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = game.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "High Score: ${game.highScore} PTS",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = CoinGold
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Times Played: ${game.playCount}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = if (game.id == "neon_snake")
                        "⚡ Ready to play! Next stage installs the custom 2D Canvas engine and joystick controls."
                    else
                        "🚀 Engine framework configured. This mini-game will be installed in the upcoming development stage.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = NeonCyan
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = accentColor, contentColor = Color(0xFF0C0B14)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.testTag("dismiss_game_launch_btn")
            ) {
                Text("Got It", fontWeight = FontWeight.Bold)
            }
        }
    )
}
