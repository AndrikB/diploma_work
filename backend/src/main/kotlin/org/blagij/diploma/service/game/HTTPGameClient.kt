package org.blagij.diploma.service.game

import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.coroutines.await
import org.jsoup.Jsoup


class HTTPGameClient(val client: WebClient) {

    private val bggUrl = "https://boardgamegeek.com"

    suspend fun searchGame(name: String): List<Game> {
        return client.getAbs("$bggUrl/search/boardgame?nosession=1&q=$name&showcount=20")
            .send().await()
            .bodyAsString()
            .toGameList()

    }
}

private fun String.toGameList(): List<Game> {
    return Jsoup.parseBodyFragment(this).select(".collection_table").select("tr#row_").map {
        val a = it.select("a")
        val id = a.attr("href").split("/")[2]
        Game(
            objectid = id,
            subtype = "boardgame",
            primaryname = "1",
            nameid = id,
            yearpublished = it.selectFirst(".smallerfont")?.text()?.removePrefix("(")?.removeSuffix(")")?.toInt() ?: 0,
            ordtitle = a.text(),
            rep_imageid = 1,
            objecttype = "thing",
            name = a.text(),
            sortindex = "1",
            type = "things",
            id = id,
            href = a.attr("href")
        )
    }
}
