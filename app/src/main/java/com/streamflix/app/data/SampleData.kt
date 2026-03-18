package com.streamflix.app.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color

object SampleData {

    val featured = StreamContent(
        id = "featured", title = "Shadow Dynasty", subtitle = "Season 2 Now Streaming",
        description = "In a world where ancient bloodlines hold unimaginable power, the Kael family must navigate political intrigue, forbidden alliances, and a rising darkness that threatens to consume everything they've built.",
        icon = Icons.Default.Star, gradientColors = listOf(Color(0xFF8B0000), Color(0xFF1A0000)),
        rating = 9.2, year = 2026, duration = "8 Episodes",
        genres = listOf("Drama", "Fantasy", "Thriller"), maturityRating = "TV-MA",
        isTrending = true, isNew = true, is4K = true, contentType = "Series",
        cast = listOf(
            CastMember("Elena Vasquez", "Queen Sera Kael"), CastMember("Marcus Webb", "Lord Darian"),
            CastMember("Anya Okafor", "Captain Lyris"), CastMember("Jin Tanaka", "Master Ren"),
            CastMember("Sarah Mitchell", "Princess Nova")
        ),
        episodes = listOf(
            Episode(1, "The Coronation", "58m", "A new ruler rises as old enemies plot in the shadows."),
            Episode(2, "Blood Oath", "52m", "An ancient pact is discovered that could change everything."),
            Episode(3, "The Siege", "61m", "The capital comes under attack from an unexpected foe."),
            Episode(4, "Whispers", "55m", "Betrayal lurks within the inner circle."),
            Episode(5, "Shattered Crown", "49m", "The queen faces her darkest hour."),
            Episode(6, "Rising Tide", "57m", "Allies emerge from the most unlikely places."),
            Episode(7, "The Reckoning", "63m", "Battle lines are drawn for the final confrontation."),
            Episode(8, "Dynasty", "72m", "The fate of the realm is decided once and for all.")
        )
    )

    val allContent = listOf(
        StreamContent(
            id = "neon-ronin", title = "Neon Ronin", subtitle = "A Cyberpunk Saga",
            description = "In neo-Tokyo 2089, a ronin hacker battles megacorporations to uncover the truth about her erased past.",
            icon = Icons.Default.FlashOn, gradientColors = listOf(Color(0xFFFF006E), Color(0xFF1A001A)),
            rating = 8.7, year = 2025, duration = "2h 18m",
            genres = listOf("Action", "Sci-Fi", "Thriller"), maturityRating = "R",
            isTrending = true, isNew = true, is4K = true, contentType = "Movie",
            cast = listOf(CastMember("Yuki Sato", "Kaida"), CastMember("Derek Chen", "Ghost"))
        ),
        StreamContent(
            id = "last-frontier", title = "The Last Frontier", subtitle = "Earth's Final Stand",
            description = "When an alien signal is decoded, humanity must unite or face extinction.",
            icon = Icons.Default.Public, gradientColors = listOf(Color(0xFF006400), Color(0xFF001A00)),
            rating = 8.4, year = 2026, duration = "6 Episodes",
            genres = listOf("Sci-Fi", "Drama", "Adventure"), maturityRating = "TV-14",
            isTrending = true, isNew = false, is4K = true, contentType = "Series",
            cast = listOf(CastMember("James Porter", "Commander Hayes"), CastMember("Priya Sharma", "Dr. Meera Patel")),
            episodes = listOf(
                Episode(1, "Contact", "55m", "A mysterious signal changes everything."),
                Episode(2, "Assembly", "48m", "The team is brought together."),
                Episode(3, "Departure", "52m", "They leave Earth behind.")
            )
        ),
        StreamContent(
            id = "whisper-house", title = "Whisper House", subtitle = "Some Secrets Never Die",
            description = "A family moves into a Victorian mansion with a horrifying past.",
            icon = Icons.Default.Home, gradientColors = listOf(Color(0xFF191970), Color(0xFF0A0A2E)),
            rating = 7.9, year = 2025, duration = "1h 52m",
            genres = listOf("Horror", "Mystery", "Thriller"), maturityRating = "R",
            isNew = true, contentType = "Movie",
            cast = listOf(CastMember("Rebecca Holloway", "Claire Mercer"))
        ),
        StreamContent(
            id = "ember-ash", title = "Ember & Ash", subtitle = "Love in the Ruins",
            description = "In a post-apocalyptic world, two survivors from rival factions fall in love.",
            icon = Icons.Default.LocalFireDepartment, gradientColors = listOf(Color(0xFFFF4500), Color(0xFF1A0800)),
            rating = 8.1, year = 2026, duration = "2h 05m",
            genres = listOf("Romance", "Drama", "Adventure"), maturityRating = "PG-13",
            isTrending = true, is4K = true, contentType = "Movie",
            cast = listOf(CastMember("Luna Clarke", "Ember"), CastMember("Kai Nakamura", "Ash"))
        ),
        StreamContent(
            id = "code-zero", title = "Code Zero", subtitle = "The Hack That Changed the World",
            description = "A team of whistleblower hackers exposes the largest government surveillance program.",
            icon = Icons.Default.Shield, gradientColors = listOf(Color(0xFF00CED1), Color(0xFF001A1A)),
            rating = 8.8, year = 2025, duration = "10 Episodes",
            genres = listOf("Thriller", "Crime", "Drama"), maturityRating = "TV-MA",
            isTrending = true, isNew = true, is4K = true, contentType = "Series",
            cast = listOf(CastMember("Alex Drummond", "Zero"), CastMember("Nina Fedorov", "Cipher")),
            episodes = listOf(
                Episode(1, "Breach", "47m", "The first hack reveals a terrifying truth."),
                Episode(2, "Ghost Protocol", "44m", "They go underground."),
                Episode(3, "Firewall", "51m", "The government fights back.")
            )
        ),
        StreamContent(
            id = "laughing-stock", title = "Laughing Stock", subtitle = "Comedy Special",
            description = "Stand-up legend Ricky Fontaine delivers his most hilarious special yet.",
            icon = Icons.Default.TheaterComedy, gradientColors = listOf(Color(0xFFFFD700), Color(0xFF1A1500)),
            rating = 7.5, year = 2026, duration = "1h 15m",
            genres = listOf("Comedy"), maturityRating = "TV-14",
            isNew = true, contentType = "Movie",
            cast = listOf(CastMember("Ricky Fontaine", "Himself"))
        ),
        StreamContent(
            id = "planet-unknown", title = "Planet Unknown", subtitle = "A Journey Beyond",
            description = "Award-winning documentary exploring the most extreme environments on Earth.",
            icon = Icons.Default.Landscape, gradientColors = listOf(Color(0xFF228B22), Color(0xFF0A1A0A)),
            rating = 9.0, year = 2025, duration = "6 Episodes",
            genres = listOf("Documentary", "Adventure"), maturityRating = "PG",
            isTrending = true, is4K = true, contentType = "Documentary",
            cast = listOf(CastMember("David Harlow", "Narrator")),
            episodes = listOf(
                Episode(1, "The Abyss", "52m", "Descending into the deepest oceans."),
                Episode(2, "Frozen Kingdoms", "48m", "Life at the extremes of cold.")
            )
        ),
        StreamContent(
            id = "ronin-academy", title = "Ronin Academy", subtitle = "Honor. Courage. Destiny.",
            description = "At a secret academy in the mountains of Japan, young warriors train in ancient arts.",
            icon = Icons.Default.SportsKabaddi, gradientColors = listOf(Color(0xFFDC143C), Color(0xFF1A0008)),
            rating = 8.6, year = 2026, duration = "12 Episodes",
            genres = listOf("Animation", "Action", "Fantasy"), maturityRating = "TV-14",
            isNew = true, is4K = true, contentType = "Anime",
            cast = listOf(CastMember("Hiro Takeshi", "Kenji (voice)")),
            episodes = listOf(
                Episode(1, "Initiation", "24m", "A new student arrives."),
                Episode(2, "First Blood", "24m", "The trials begin.")
            )
        ),
        StreamContent(
            id = "midnight-heist", title = "Midnight Heist", subtitle = "One Night. One Chance.",
            description = "Six strangers are recruited for an impossible heist from the world's most secure vault.",
            icon = Icons.Default.NightsStay, gradientColors = listOf(Color(0xFF4B0082), Color(0xFF0A0020)),
            rating = 8.3, year = 2025, duration = "2h 22m",
            genres = listOf("Action", "Thriller", "Crime"), maturityRating = "R",
            isTrending = true, is4K = true, contentType = "Movie",
            cast = listOf(CastMember("Sofia Rodriguez", "The Architect"), CastMember("Liam Chase", "The Ghost"))
        )
    )

    val continueWatching = listOf(
        WatchProgress("neon-ronin", 0.65f),
        WatchProgress("last-frontier", 0.30f, episode = 3),
        WatchProgress("code-zero", 0.80f, episode = 7),
        WatchProgress("midnight-heist", 0.45f)
    )

    fun getContentRows(): List<ContentRow> = listOf(
        ContentRow("Trending Now", Icons.Default.LocalFireDepartment, allContent.filter { it.isTrending }),
        ContentRow("New Releases", Icons.Default.NewReleases, allContent.filter { it.isNew }),
        ContentRow("Top Rated", Icons.Default.Star, allContent.sortedByDescending { it.rating }),
        ContentRow("Movies", Icons.Default.Movie, allContent.filter { it.contentType == "Movie" }),
        ContentRow("Series", Icons.Default.Tv, allContent.filter { it.contentType == "Series" }),
        ContentRow("Action & Thriller", Icons.Default.FlashOn, allContent.filter { it.genres.any { g -> g in listOf("Action", "Thriller") } })
    )

    fun findContent(id: String): StreamContent? = if (featured.id == id) featured else allContent.find { it.id == id }
}
