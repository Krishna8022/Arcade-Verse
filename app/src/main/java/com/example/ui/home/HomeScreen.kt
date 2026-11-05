package com.example.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GameCategory
import com.example.data.model.GameModel
import com.example.navigation.Screen
import com.example.ui.MainUiState
import com.example.ui.components.GameCard
import com.example.ui.components.GlassCard
import com.example.ui.components.StatBadge
import com.example.ui.theme.CoinGold
import com.example.ui.theme.CyberPink
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGreen

@Composable
fun HomeScreen(
    uiState: MainUiState,
    onPlayGame: (GameModel) -> Unit,
    onToggleFavorite: (GameModel) -> Unit,
    onCategorySelect: (GameCategory) -> Unit,
    onClaimChallenge: (String) -> Unit,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen"),
        contentPadding = PaddingValues(bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Hero Showcase Banner
        item {
            HeroBanner(
                featuredGame = uiState.games.firstOrNull { it.isFeatured } ?: uiState.games.first(),
                onPlayClick = onPlayGame
            )
        }

        // 2. Player Progress & Level Card
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                PlayerStatusCard(
                    uiState = uiState,
                    onViewProfile = { onNavigate(Screen.Profile) }
                )
            }
        }

        // 3. Daily Quest Highlight
        item {
            val activeQuest = uiState.dailyChallenges.firstOrNull { !it.isClaimed }
            if (activeQuest != null) {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    DailyQuestHighlightCard(
                        challenge = activeQuest,
                        onClaimClick = { onClaimChallenge(activeQuest.id) },
                        onViewQuests = { onNavigate(Screen.Challenges) }
                    )
                }
            }
        }

        // 4. Quick Category Filter Chips
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    text = "CATEGORIES",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(GameCategory.values()) { category ->
                        val isSelected = uiState.selectedCategory == category
                        FilterChip(
                            selected = isSelected,
                            onClick = { onCategorySelect(category) },
                            label = {
                                Text(
                                    text = category.displayName,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = NeonCyan.copy(alpha = 0.2f),
                                selectedLabelColor = NeonCyan
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.testTag("category_chip_${category.name.lowercase()}")
                        )
                    }
                }
            }
        }

        // 5. Featured Arcade Games (Horizontal Scroll)
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "FEATURED GAMES",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "See All (${uiState.games.size})",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan
                        ),
                        modifier = Modifier
                            .clickable { onNavigate(Screen.Games) }
                            .padding(4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    val featured = uiState.games.filter { it.isFeatured }
                    items(featured) { game ->
                        FeaturedMiniCard(
                            game = game,
                            onPlayClick = { onPlayGame(game) }
                        )
                    }
                }
            }
        }

        // 6. Filtered Games List or Favorite Games
        item {
            val displayedGames = if (uiState.selectedCategory == GameCategory.ALL) {
                uiState.games
            } else {
                uiState.games.filter { it.category == uiState.selectedCategory }
            }

            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (uiState.selectedCategory == GameCategory.ALL) "ALL ARCADE GAMES" else "${uiState.selectedCategory.displayName.uppercase()} GAMES",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                    color = MaterialTheme.colorScheme.onBackground
                )

                displayedGames.forEach { game ->
                    GameCard(
                        game = game,
                        onPlayClick = onPlayGame,
                        onToggleFavorite = onToggleFavorite
                    )
                }
            }
        }

        // 7. Hall of Fame Mini Leaderboard
        item {
            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                HallOfFamePreviewCard(
                    leaderboard = uiState.leaderboardEntries.take(4)
                )
            }
        }
    }
}

@Composable
fun HeroBanner(
    featuredGame: GameModel,
    onPlayClick: (GameModel) -> Unit
) {
    val accent = Color(featuredGame.accentColorHex)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        accent.copy(alpha = 0.35f),
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        accent.copy(alpha = 0.8f),
                        ElectricPurple.copy(alpha = 0.4f)
                    )
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
            .testTag("hero_banner")
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatBadge(
                    text = "FEATURED OF THE DAY",
                    badgeColor = accent,
                    icon = Icons.Filled.Star
                )
                Text(
                    text = "OFFLINE NATIVE",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonGreen
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = featuredGame.title,
                style = MaterialTheme.typography.displayMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-0.5).sp
                ),
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = featuredGame.tagline,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.85f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "RECORD SCORE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = CoinGold.copy(alpha = 0.8f)
                        )
                    )
                    Text(
                        text = "${featuredGame.highScore} PTS",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = CoinGold
                        )
                    )
                }

                Button(
                    onClick = { onPlayClick(featuredGame) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accent,
                        contentColor = Color(0xFF0C0B14)
                    ),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.testTag("hero_play_btn")
                ) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "Play Featured Game",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LAUNCH GAME",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.5.sp
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun PlayerStatusCard(
    uiState: MainUiState,
    onViewProfile: () -> Unit
) {
    val profile = uiState.playerProfile
    val progress = (profile.xp.toFloat() / profile.xpForNextLevel.toFloat()).coerceIn(0f, 1f)

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onViewProfile() }
            .testTag("player_status_card")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(ElectricPurple.copy(alpha = 0.25f))
                            .border(1.dp, ElectricPurple.copy(alpha = 0.6f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = profile.avatarEmoji, fontSize = 22.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = profile.nickname,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Pilot Level ${profile.level}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan
                            )
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = "View Profile",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // XP Progress Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "PROGRESS TO LV ${profile.level + 1}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                )
                Text(
                    text = "${profile.xp} / ${profile.xpForNextLevel} XP",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonCyan
                    )
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = NeonCyan,
                trackColor = MaterialTheme.colorScheme.surface
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Quick Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickStatItem(icon = Icons.Filled.MonetizationOn, label = "Coins", value = "${profile.coins}", color = CoinGold)
                QuickStatItem(icon = Icons.Filled.Gamepad, label = "Games", value = "${profile.totalGamesPlayed}", color = ElectricPurple)
                QuickStatItem(icon = Icons.Filled.Timer, label = "Playtime", value = "${profile.totalPlayTimeMinutes}m", color = ElectricBlue)
            }
        }
    }
}

@Composable
fun QuickStatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(
                text = value,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Black,
                    fontSize = 11.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 9.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            )
        }
    }
}

@Composable
fun DailyQuestHighlightCard(
    challenge: com.example.data.model.DailyChallenge,
    onClaimClick: () -> Unit,
    onViewQuests: () -> Unit
) {
    val progress = (challenge.currentProgress.toFloat() / challenge.targetGoal.toFloat()).coerceIn(0f, 1f)

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("daily_quest_highlight")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Whatshot,
                        contentDescription = "Daily Quest",
                        tint = CyberPink,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "DAILY QUEST",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 0.8.sp,
                            color = CyberPink
                        )
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "+${challenge.rewardCoins}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            color = CoinGold
                        )
                    )
                    Icon(
                        imageVector = Icons.Filled.MonetizationOn,
                        contentDescription = null,
                        tint = CoinGold,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "+${challenge.rewardXp} XP",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            color = NeonCyan
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = challenge.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = challenge.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = CyberPink,
                    trackColor = MaterialTheme.colorScheme.surface
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${challenge.currentProgress}/${challenge.targetGoal}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = CyberPink
                    )
                )
            }

            if (challenge.isCompleted && !challenge.isClaimed) {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = onClaimClick,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonGreen, contentColor = Color(0xFF0C0B14)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("claim_quest_btn")
                ) {
                    Text("CLAIM REWARD", fontWeight = FontWeight.Black)
                }
            }
        }
    }
}

@Composable
fun FeaturedMiniCard(
    game: GameModel,
    onPlayClick: () -> Unit
) {
    val accent = Color(game.accentColorHex)
    Box(
        modifier = Modifier
            .width(180.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
            .border(1.dp, accent.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .clickable { onPlayClick() }
            .padding(12.dp)
            .testTag("featured_mini_${game.id}")
    ) {
        Column {
            StatBadge(text = game.category.displayName, badgeColor = accent)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = game.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${game.highScore} PTS",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Black,
                    color = CoinGold
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = onPlayClick,
                colors = ButtonDefaults.buttonColors(containerColor = accent, contentColor = Color(0xFF0C0B14)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Play", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
            }
        }
    }
}

@Composable
fun HallOfFamePreviewCard(
    leaderboard: List<com.example.data.model.LeaderboardEntry>
) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.EmojiEvents,
                        contentDescription = "Leaderboard",
                        tint = CoinGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LOCAL HALL OF FAME",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                StatBadge(text = "LOCAL ONLY", badgeColor = MaterialTheme.colorScheme.outline)
            }

            Spacer(modifier = Modifier.height(10.dp))

            leaderboard.forEach { entry ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "#${entry.rank}",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Black,
                                color = if (entry.rank == 1) CoinGold else NeonCyan
                            ),
                            modifier = Modifier.width(26.dp)
                        )
                        Text(
                            text = entry.gameTitle,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Text(
                        text = "${entry.score} PTS",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Black,
                            color = CoinGold
                        )
                    )
                }
            }
        }
    }
}
