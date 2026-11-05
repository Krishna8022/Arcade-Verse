package com.example.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.MainUiState
import com.example.ui.components.GlassCard
import com.example.ui.theme.AppThemeMode
import com.example.ui.theme.CyberPink
import com.example.ui.theme.NeonCyan

@Composable
fun SettingsScreen(
    uiState: MainUiState,
    onMusicVolumeChange: (Float) -> Unit,
    onSoundFxVolumeChange: (Float) -> Unit,
    onVibrationToggle: (Boolean) -> Unit,
    onThemeSelect: (AppThemeMode) -> Unit,
    onReducedAnimationsToggle: (Boolean) -> Unit,
    onResetProgress: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showResetConfirmDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("settings_screen"),
        contentPadding = PaddingValues(16.dp, 12.dp, 16.dp, 100.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "SYSTEM SETTINGS",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Black),
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // Audio & Haptics Card
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "AUDIO & HAPTICS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan,
                            letterSpacing = 1.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Music Volume
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.MusicNote, contentDescription = null, tint = NeonCyan)
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text("Music Volume", style = MaterialTheme.typography.bodyMedium)
                        }
                        Text("${(uiState.musicVolume * 100).toInt()}%", fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = uiState.musicVolume,
                        onValueChange = onMusicVolumeChange,
                        modifier = Modifier.testTag("music_volume_slider"),
                        colors = SliderDefaults.colors(thumbColor = NeonCyan, activeTrackColor = NeonCyan)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Sound Effects Volume
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.VolumeUp, contentDescription = null, tint = NeonCyan)
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text("Sound FX", style = MaterialTheme.typography.bodyMedium)
                        }
                        Text("${(uiState.soundFxVolume * 100).toInt()}%", fontWeight = FontWeight.Bold)
                    }
                    Slider(
                        value = uiState.soundFxVolume,
                        onValueChange = onSoundFxVolumeChange,
                        modifier = Modifier.testTag("sfx_volume_slider"),
                        colors = SliderDefaults.colors(thumbColor = NeonCyan, activeTrackColor = NeonCyan)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Vibration Toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.Vibration, contentDescription = null, tint = NeonCyan)
                            Spacer(modifier = Modifier.padding(4.dp))
                            Text("Haptic Feedback", style = MaterialTheme.typography.bodyMedium)
                        }
                        Switch(
                            checked = uiState.vibrationEnabled,
                            onCheckedChange = onVibrationToggle,
                            colors = SwitchDefaults.colors(checkedThumbColor = NeonCyan),
                            modifier = Modifier.testTag("vibration_switch")
                        )
                    }
                }
            }
        }

        // Appearance & Display
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "THEME & APPEARANCE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan,
                            letterSpacing = 1.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val themes = listOf(
                            Triple(AppThemeMode.DARK, "Charcoal", "theme_chip_dark"),
                            Triple(AppThemeMode.AMOLED, "AMOLED", "theme_chip_amoled"),
                            Triple(AppThemeMode.LIGHT, "Light", "theme_chip_light")
                        )

                        themes.forEach { (mode, label, tag) ->
                            val isSelected = uiState.themeMode == mode
                            FilterChip(
                                selected = isSelected,
                                onClick = { onThemeSelect(mode) },
                                label = { Text(label, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = NeonCyan.copy(alpha = 0.2f),
                                    selectedLabelColor = NeonCyan
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f).testTag(tag)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Reduced Animations", style = MaterialTheme.typography.bodyMedium)
                        Switch(
                            checked = uiState.reducedAnimations,
                            onCheckedChange = onReducedAnimationsToggle,
                            colors = SwitchDefaults.colors(checkedThumbColor = NeonCyan),
                            modifier = Modifier.testTag("reduced_anim_switch")
                        )
                    }
                }
            }
        }

        // Data & Reset Card
        item {
            GlassCard(modifier = Modifier.fillMaxWidth(), strokeColor = CyberPink.copy(alpha = 0.3f)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "DATA MANAGEMENT",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = CyberPink,
                            letterSpacing = 1.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "All player scores, achievements, coins and progress are stored entirely offline on this device.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { showResetConfirmDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = CyberPink.copy(alpha = 0.2f),
                            contentColor = CyberPink
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("reset_progress_button")
                    ) {
                        Icon(Icons.Filled.Warning, contentDescription = null)
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text("Reset All Game Progress", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // About & Privacy
        item {
            GlassCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Info, contentDescription = null, tint = NeonCyan)
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(
                            text = "ABOUT ARCADEVERSE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan,
                                letterSpacing = 1.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "ArcadeVerse v1.0 • Built with Kotlin & Jetpack Compose\n" +
                                "Tagline: \"Your Games. Your Scores. Your Universe.\"\n\n" +
                                "🔒 Privacy & Offline Guarantee:\n" +
                                "• 100% Offline Gameplay\n" +
                                "• No Mandatory Account or Login\n" +
                                "• Zero Ads or In-App Purchases\n" +
                                "• Zero Telemetry or Personal Data Collection",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }

    if (showResetConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showResetConfirmDialog = false },
            icon = { Icon(Icons.Filled.Warning, contentDescription = null, tint = CyberPink) },
            title = { Text("Confirm Data Reset", fontWeight = FontWeight.Bold) },
            text = {
                Text("Are you sure you want to erase all high scores, daily quests, XP and coin progression? This action cannot be undone.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        onResetProgress()
                        showResetConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = CyberPink),
                    modifier = Modifier.testTag("confirm_reset_btn")
                ) {
                    Text("Yes, Reset Everything", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetConfirmDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
