package com.example.ui.challenges

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import com.example.data.model.DailyChallenge
import com.example.ui.MainUiState
import com.example.ui.components.GlassCard
import com.example.ui.theme.CoinGold
import com.example.ui.theme.CyberPink
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGreen

@Composable
fun ChallengesScreen(
    uiState: MainUiState,
    onClaimChallenge: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("challenges_screen"),
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
                        text = "DAILY CHALLENGES",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Complete objectives to earn XP & Coins",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Icon(
                    imageVector = Icons.Filled.Whatshot,
                    contentDescription = null,
                    tint = CyberPink,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        items(uiState.dailyChallenges) { challenge ->
            ChallengeItemCard(
                challenge = challenge,
                onClaim = { onClaimChallenge(challenge.id) }
            )
        }
    }
}

@Composable
fun ChallengeItemCard(
    challenge: DailyChallenge,
    onClaim: () -> Unit
) {
    val progress = (challenge.currentProgress.toFloat() / challenge.targetGoal.toFloat()).coerceIn(0f, 1f)

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("quest_card_${challenge.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = challenge.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.MonetizationOn,
                        contentDescription = null,
                        tint = CoinGold,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "+${challenge.rewardCoins}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            color = CoinGold
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "+${challenge.rewardXp} XP",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Black,
                            color = NeonCyan
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = challenge.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = if (challenge.isCompleted) NeonGreen else CyberPink,
                    trackColor = MaterialTheme.colorScheme.surface
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "${challenge.currentProgress}/${challenge.targetGoal}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (challenge.isCompleted) NeonGreen else CyberPink
                    )
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            when {
                challenge.isClaimed -> {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Claimed",
                            tint = NeonGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Reward Claimed",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = NeonGreen
                            )
                        )
                    }
                }
                challenge.isCompleted -> {
                    Button(
                        onClick = onClaim,
                        colors = ButtonDefaults.buttonColors(containerColor = NeonGreen, contentColor = Color(0xFF0C0B14)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("claim_btn_${challenge.id}")
                    ) {
                        Text("CLAIM ${challenge.rewardCoins} COINS & ${challenge.rewardXp} XP", fontWeight = FontWeight.Black)
                    }
                }
                else -> {
                    Text(
                        text = "In Progress",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                    )
                }
            }
        }
    }
}
