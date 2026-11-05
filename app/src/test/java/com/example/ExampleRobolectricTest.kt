package com.example

import com.example.data.model.GameCategory
import com.example.data.model.PredefinedGames
import com.example.ui.MainViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTestSuite {

  @Test
  fun `verify predefined games count and integrity`() {
    val games = PredefinedGames.games
    assertEquals(6, games.size)
    assertNotNull(games.find { it.id == "neon_snake" })
    assertNotNull(games.find { it.id == "pixel_jumper" })
    assertNotNull(games.find { it.id == "turbo_racer" })
    assertNotNull(games.find { it.id == "block_puzzle" })
    assertNotNull(games.find { it.id == "brick_breaker" })
    assertNotNull(games.find { it.id == "memory_match" })
  }

  @Test
  fun `verify default player profile initialization`() {
    val viewModel = MainViewModel()
    val state = viewModel.uiState.value
    assertEquals("CyberPilot_01", state.playerProfile.nickname)
    assertEquals(4, state.playerProfile.level)
    assertEquals(1250, state.playerProfile.coins)
  }

  @Test
  fun `verify category filtering and favorite toggle`() {
    val viewModel = MainViewModel()
    viewModel.selectCategory(GameCategory.RETRO)
    assertEquals(GameCategory.RETRO, viewModel.uiState.value.selectedCategory)

    val snake = viewModel.uiState.value.games.first { it.id == "neon_snake" }
    val initialFav = snake.isFavorite
    viewModel.toggleFavorite(snake)
    val toggledFav = viewModel.uiState.value.games.first { it.id == "neon_snake" }.isFavorite
    assertEquals(!initialFav, toggledFav)
  }
}
