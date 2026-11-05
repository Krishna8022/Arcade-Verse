package com.example.data.model

data class PlayerProfile(
    val nickname: String = "CyberPilot_01",
    val avatarEmoji: String = "👾",
    val level: Int = 4,
    val xp: Int = 340,
    val xpForNextLevel: Int = 500,
    val coins: Int = 1250,
    val totalGamesPlayed: Int = 64,
    val totalPlayTimeMinutes: Int = 142,
    val favoriteGameId: String = "neon_snake"
)

data class DailyChallenge(
    val id: String,
    val title: String,
    val description: String,
    val gameId: String,
    val currentProgress: Int,
    val targetGoal: Int,
    val rewardCoins: Int,
    val rewardXp: Int,
    val isCompleted: Boolean = false,
    val isClaimed: Boolean = false
)

data class AchievementItem(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val gameId: String?,
    val isUnlocked: Boolean,
    val unlockedDate: String? = null,
    val xpReward: Int = 100
)

data class LeaderboardEntry(
    val rank: Int,
    val playerName: String,
    val gameId: String,
    val gameTitle: String,
    val score: Int,
    val dateAchieved: String,
    val isCurrentPlayer: Boolean = false
)
