package com.example.ui.achievements

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AchievementItem
import com.example.ui.MainUiState
import com.example.ui.components.GlassCard
import com.example.ui.components.StatBadge
import com.example.ui.theme.CoinGold
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.NeonCyan

@Composable
fun AchievementsScreen(
    uiState: MainUiState,
    modifier: Modifier = Modifier
) {
    val unlockedCount = uiState.achievements.count { it.isUnlocked }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("achievements_screen"),
        contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 100.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ACHIEVEMENT VAULT",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "$unlockedCount of ${uiState.achievements.size} Badges Unlocked",
                        style = MaterialTheme.typography.bodyMedium,
                        color = NeonCyan
                    )
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(CoinGold.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.EmojiEvents,
                        contentDescription = "Trophy",
                        tint = CoinGold,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }

        items(uiState.achievements) { achievement ->
            AchievementCard(achievement = achievement)
        }
    }
}

@Composable
fun AchievementCard(achievement: AchievementItem) {
    val isUnlocked = achievement.isUnlocked
    val strokeColor = if (isUnlocked) CoinGold.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("achievement_${achievement.id}"),
        strokeColor = strokeColor
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(if (isUnlocked) ElectricPurple.copy(alpha = 0.3f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                    .border(
                        1.dp,
                        if (isUnlocked) CoinGold.copy(alpha = 0.6f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isUnlocked) {
                    Text(text = achievement.iconEmoji, fontSize = 24.sp)
                } else {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "Locked",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = achievement.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isUnlocked) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    )
                    StatBadge(
                        text = "+${achievement.xpReward} XP",
                        badgeColor = if (isUnlocked) NeonCyan else MaterialTheme.colorScheme.outline
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = achievement.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = if (isUnlocked) 0.9f else 0.5f)
                )

                if (isUnlocked && achievement.unlockedDate != null) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Unlocked: ${achievement.unlockedDate}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            color = CoinGold.copy(alpha = 0.8f)
                        )
                    )
                }
            }
        }
    }
}
