package com.example.data.model

data class GameModel(
    val id: String,
    val title: String,
    val tagline: String,
    val category: GameCategory,
    val difficulty: GameDifficulty,
    val accentColorHex: Long,
    val isFeatured: Boolean = false,
    val isFavorite: Boolean = false,
    val playCount: Int = 0,
    val highScore: Int = 0,
    val description: String
)

enum class GameCategory(val displayName: String) {
    ALL("All"),
    RETRO("Retro"),
    PLATFORMER("Platformer"),
    RACING("Racing"),
    PUZZLE("Puzzle"),
    ARCADE("Arcade"),
    BRAIN("Brain")
}

enum class GameDifficulty(val displayName: String) {
    CASUAL("Casual"),
    NORMAL("Normal"),
    CHALLENGING("Challenging"),
    EXTREME("Extreme")
}

object PredefinedGames {
    val games = listOf(
        GameModel(
            id = "neon_snake",
            title = "Neon Snake",
            tagline = "Classic arcade snake with a glowing neon pulse",
            category = GameCategory.RETRO,
            difficulty = GameDifficulty.NORMAL,
            accentColorHex = 0xFF00FF88, // Neon Green
            isFeatured = true,
            isFavorite = true,
            playCount = 14,
            highScore = 480,
            description = "Navigate through glowing cyber grid corridors, gather pulsing energy orbs, avoid speed-boosting obstacles, and survive the ever-growing neon serpent tail."
        ),
        GameModel(
            id = "pixel_jumper",
            title = "Pixel Jumper",
            tagline = "Precision 2D platforming and moving ledge ascents",
            category = GameCategory.PLATFORMER,
            difficulty = GameDifficulty.CHALLENGING,
            accentColorHex = 0xFF00F0FF, // Neon Cyan
            isFeatured = true,
            isFavorite = true,
            playCount = 9,
            highScore = 1250,
            description = "Leap across floating platforms, dodge spikes, collect golden coins, and time double jumps carefully to scale cosmic heights in this retro 2D platformer."
        ),
        GameModel(
            id = "turbo_racer",
            title = "Turbo Racer",
            tagline = "High-octane top-down arcade highway racing",
            category = GameCategory.RACING,
            difficulty = GameDifficulty.CHALLENGING,
            accentColorHex = 0xFFFF007F, // Cyber Pink
            isFeatured = true,
            isFavorite = false,
            playCount = 18,
            highScore = 2100,
            description = "Weave through neon traffic lanes at supersonic speed! Collect fuel canisters, execute nitro drifts, brake around congested semi-trucks, and set the ultimate distance record."
        ),
        GameModel(
            id = "block_puzzle",
            title = "Block Puzzle",
            tagline = "Tactical shape placement and line-clearing combos",
            category = GameCategory.PUZZLE,
            difficulty = GameDifficulty.CASUAL,
            accentColorHex = 0xFFB026FF, // Electric Purple
            isFeatured = false,
            isFavorite = true,
            playCount = 11,
            highScore = 890,
            description = "Strategically drag and place polyomino block clusters onto an 8x8 neon grid. Complete horizontal and vertical lines simultaneously to ignite combo score multipliers."
        ),
        GameModel(
            id = "brick_breaker",
            title = "Brick Breaker",
            tagline = "Hyper-kinetic paddle deflection and brick demolition",
            category = GameCategory.ARCADE,
            difficulty = GameDifficulty.NORMAL,
            accentColorHex = 0xFFFFE600, // Neon Yellow
            isFeatured = false,
            isFavorite = false,
            playCount = 7,
            highScore = 1420,
            description = "Control a magnetic laser paddle, calculate ricochet angles, unleash multi-ball splits, and smash through reinforced neon brick fortresses across progressive stages."
        ),
        GameModel(
            id = "memory_match",
            title = "Memory Match",
            tagline = "Holographic card-flipping and mental recall challenge",
            category = GameCategory.BRAIN,
            difficulty = GameDifficulty.CASUAL,
            accentColorHex = 0xFF3D7EFF, // Electric Blue
            isFeatured = false,
            isFavorite = false,
            playCount = 5,
            highScore = 650,
            description = "Exercise your cognitive memory by flipping synth-wave cards to match pairs against the ticking stopwatch. Maintain combos with flawless consecutive matches."
        )
    )
}
