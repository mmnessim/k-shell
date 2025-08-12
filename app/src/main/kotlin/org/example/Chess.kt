package org.example
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.call.body
import kotlinx.coroutines.runBlocking

class Chess {
    fun getStats() = runBlocking {
        val client = HttpClient(CIO)
        val response: String = client.get("https://api.chess.com/pub/player/tenderllama/stats").body<String>()
        println(response)
        client.close()
    }
}
