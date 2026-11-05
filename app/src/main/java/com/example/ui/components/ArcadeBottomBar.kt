package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigation.Screen
import com.example.ui.theme.NeonCyan

@Composable
fun ArcadeBottomBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
                        MaterialTheme.colorScheme.surface
                    )
                )
            )
            .navigationBarsPadding()
            .padding(horizontal = 4.dp, vertical = 6.dp)
            .testTag("arcade_bottom_bar")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Screen.bottomNavItems.forEach { screen ->
                val isSelected = currentScreen.route == screen.route
                val iconColor by animateColorAsState(
                    targetValue = if (isSelected) NeonCyan else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    label = "bottom_icon_color"
                )
                val pillBackground = if (isSelected) NeonCyan.copy(alpha = 0.16f) else Color.Transparent

                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(pillBackground)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onNavigate(screen)
                        }
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                        .testTag(screen.testTag),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (isSelected) screen.filledIcon else screen.outlinedIcon,
                        contentDescription = screen.title,
                        tint = iconColor,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = screen.title,
                        color = iconColor,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        ),
                        maxLines = 1
                    )
                }
            }
        }
    }
}
