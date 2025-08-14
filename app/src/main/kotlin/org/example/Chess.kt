package org.example
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.call.body
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class Chess {
    fun getStats() = runBlocking {
        val client = HttpClient(CIO)
        val response: String = client.get("https://api.chess.com/pub/player/tenderllama/stats").body<String>()
        println(response)
        val stats = Json.decodeFromString<ChessStatsResponse>(response)
        println(stats)
        client.close()
    }
}

@Serializable
data class ChessStatsResponse(
    val chess_daily: ChessMode,
    val chess_rapid: ChessMode,
    val chess_bullet: ChessMode,
    val chess_blitz: ChessMode,
    val fide: Int,
    val tactics: Tactics,
    val puzzle_rush: PuzzleRush = PuzzleRush()
)

@Serializable
data class ChessMode(
    val last: ChessRating,
    val best: ChessBest,
    val record: ChessRecord
)

@Serializable
data class ChessRating(
    val rating: Int,
    val date: Long,
    val rd: Int
)

@Serializable
data class ChessBest(
    val rating: Int,
    val date: Long,
    val game: String
)

@Serializable
data class ChessRecord(
    val win: Int,
    val loss: Int,
    val draw: Int,
    val time_per_move: Int? = null,
    val timeout_percent: Int? = null
)

@Serializable
data class Tactics(
    val highest: TacticRating,
    val lowest: TacticRating
)

@Serializable
data class TacticRating(
    val rating: Int,
    val date: Long
)

@Serializable
data class PuzzleRush(val dummy: Int = 0)
