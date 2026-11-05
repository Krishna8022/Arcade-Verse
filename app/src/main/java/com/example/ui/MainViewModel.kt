package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.AchievementItem
import com.example.data.model.DailyChallenge
import com.example.data.model.GameCategory
import com.example.data.model.GameModel
import com.example.data.model.LeaderboardEntry
import com.example.data.model.PlayerProfile
import com.example.data.model.PredefinedGames
import com.example.navigation.Screen
import com.example.ui.theme.AppThemeMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class MainUiState(
    val currentScreen: Screen = Screen.Home,
    val selectedGameId: String? = null,
    val playerProfile: PlayerProfile = PlayerProfile(),
    val games: List<GameModel> = PredefinedGames.games,
    val selectedCategory: GameCategory = GameCategory.ALL,
    val searchQuery: String = "",
    val dailyChallenges: List<DailyChallenge> = listOf(
        DailyChallenge(
            id = "dc_1",
            title = "Snake Master",
            description = "Score 300+ in Neon Snake without touching hazards",
            gameId = "neon_snake",
            currentProgress = 220,
            targetGoal = 300,
            rewardCoins = 150,
            rewardXp = 80,
            isCompleted = false
        ),
        DailyChallenge(
            id = "dc_2",
            title = "Coin Collector",
            description = "Collect 20 coins in Pixel Jumper",
            gameId = "pixel_jumper",
            currentProgress = 20,
            targetGoal = 20,
            rewardCoins = 200,
            rewardXp = 100,
            isCompleted = true,
            isClaimed = false
        ),
        DailyChallenge(
            id = "dc_3",
            title = "Highway Velocity",
            description = "Reach 1,500m distance in Turbo Racer",
            gameId = "turbo_racer",
            currentProgress = 950,
            targetGoal = 1500,
            rewardCoins = 250,
            rewardXp = 120,
            isCompleted = false
        )
    ),
    val achievements: List<AchievementItem> = listOf(
        AchievementItem(
            id = "ach_first_blood",
            title = "First Ignition",
            description = "Launch your first arcade session in the Universe",
            iconEmoji = "🚀",
            gameId = null,
            isUnlocked = true,
            unlockedDate = "Nov 02",
            xpReward = 50
        ),
        AchievementItem(
            id = "ach_snake_chubby",
            title = "Ouroboros",
            description = "Grow snake length beyond 25 segments",
            iconEmoji = "🐍",
            gameId = "neon_snake",
            isUnlocked = true,
            unlockedDate = "Nov 03",
            xpReward = 100
        ),
        AchievementItem(
            id = "ach_jumper_stratosphere",
            title = "Cloud Strider",
            description = "Climb past platform layer 15 in Pixel Jumper",
            iconEmoji = "🦘",
            gameId = "pixel_jumper",
            isUnlocked = true,
            unlockedDate = "Nov 04",
            xpReward = 120
        ),
        AchievementItem(
            id = "ach_nitro_demon",
            title = "Nitro Demon",
            description = "Survive 2 minutes in Turbo Racer without collisions",
            iconEmoji = "🏎️",
            gameId = "turbo_racer",
            isUnlocked = false,
            xpReward = 150
        ),
        AchievementItem(
            id = "ach_combo_god",
            title = "Grid Tactician",
            description = "Clear 3 rows simultaneously in Block Puzzle",
            iconEmoji = "🧩",
            gameId = "block_puzzle",
            isUnlocked = false,
            xpReward = 200
        ),
        AchievementItem(
            id = "ach_brick_shatter",
            title = "Deflector Core",
            description = "Clear stage 3 without losing a single paddle life",
            iconEmoji = "🧱",
            gameId = "brick_breaker",
            isUnlocked = false,
            xpReward = 180
        ),
        AchievementItem(
            id = "ach_photographic",
            title = "Photographic Mind",
            description = "Complete Memory Match in under 45 seconds",
            iconEmoji = "🧠",
            gameId = "memory_match",
            isUnlocked = false,
            xpReward = 160
        )
    ),
    val leaderboardEntries: List<LeaderboardEntry> = listOf(
        LeaderboardEntry(1, "CyberPilot_01", "turbo_racer", "Turbo Racer", 2100, "Nov 04", true),
        LeaderboardEntry(2, "NeoVoxel", "turbo_racer", "Turbo Racer", 1850, "Nov 03", false),
        LeaderboardEntry(3, "PixelGhost", "turbo_racer", "Turbo Racer", 1620, "Nov 01", false),
        LeaderboardEntry(1, "CyberPilot_01", "pixel_jumper", "Pixel Jumper", 1250, "Nov 04", true),
        LeaderboardEntry(2, "VaporFox", "pixel_jumper", "Pixel Jumper", 1120, "Nov 02", false),
        LeaderboardEntry(1, "CyberPilot_01", "neon_snake", "Neon Snake", 480, "Nov 03", true),
        LeaderboardEntry(2, "GlitchMaster", "neon_snake", "Neon Snake", 440, "Nov 01", false)
    ),
    val musicVolume: Float = 0.7f,
    val soundFxVolume: Float = 0.85f,
    val vibrationEnabled: Boolean = true,
    val themeMode: AppThemeMode = AppThemeMode.DARK,
    val reducedAnimations: Boolean = false
)

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    fun navigateTo(screen: Screen) {
        _uiState.update { it.copy(currentScreen = screen, selectedGameId = null) }
    }

    fun openGame(game: GameModel) {
        _uiState.update { it.copy(selectedGameId = game.id) }
    }

    fun closeGame() {
        _uiState.update { it.copy(selectedGameId = null) }
    }

    fun toggleFavorite(game: GameModel) {
        _uiState.update { state ->
            val updated = state.games.map {
                if (it.id == game.id) it.copy(isFavorite = !it.isFavorite) else it
            }
            state.copy(games = updated)
        }
    }

    fun selectCategory(category: GameCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun updateSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun claimDailyChallenge(challengeId: String) {
        viewModelScope.launch {
            _uiState.update { state ->
                val challenge = state.dailyChallenges.find { it.id == challengeId } ?: return@update state
                if (!challenge.isCompleted || challenge.isClaimed) return@update state

                val updatedChallenges = state.dailyChallenges.map {
                    if (it.id == challengeId) it.copy(isClaimed = true) else it
                }

                val currentProfile = state.playerProfile
                val newCoins = currentProfile.coins + challenge.rewardCoins
                val newXp = currentProfile.xp + challenge.rewardXp
                val (newLevel, nextLevelXp) = calculateLevel(newXp, currentProfile.level, currentProfile.xpForNextLevel)

                state.copy(
                    dailyChallenges = updatedChallenges,
                    playerProfile = currentProfile.copy(
                        coins = newCoins,
                        xp = newXp,
                        level = newLevel,
                        xpForNextLevel = nextLevelXp
                    )
                )
            }
        }
    }

    fun updatePlayerNickname(nickname: String) {
        _uiState.update { state ->
            state.copy(playerProfile = state.playerProfile.copy(nickname = nickname.trim()))
        }
    }

    fun updateMusicVolume(volume: Float) {
        _uiState.update { it.copy(musicVolume = volume) }
    }

    fun updateSoundFxVolume(volume: Float) {
        _uiState.update { it.copy(soundFxVolume = volume) }
    }

    fun toggleVibration(enabled: Boolean) {
        _uiState.update { it.copy(vibrationEnabled = enabled) }
    }

    fun setThemeMode(mode: AppThemeMode) {
        _uiState.update { it.copy(themeMode = mode) }
    }

    fun toggleReducedAnimations(reduced: Boolean) {
        _uiState.update { it.copy(reducedAnimations = reduced) }
    }

    fun resetProgress() {
        _uiState.update { state ->
            state.copy(
                playerProfile = PlayerProfile(nickname = state.playerProfile.nickname, level = 1, xp = 0, xpForNextLevel = 100, coins = 100, totalGamesPlayed = 0, totalPlayTimeMinutes = 0),
                games = state.games.map { it.copy(highScore = 0, playCount = 0) },
                dailyChallenges = state.dailyChallenges.map { it.copy(currentProgress = 0, isCompleted = false, isClaimed = false) }
            )
        }
    }

    private fun calculateLevel(xp: Int, currentLevel: Int, currentCap: Int): Pair<Int, Int> {
        var level = currentLevel
        var cap = currentCap
        while (xp >= cap) {
            level += 1
            cap += (level * 150)
        }
        return Pair(level, cap)
    }
}
