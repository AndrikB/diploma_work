package org.blagij.diploma.service.game

import io.vertx.core.json.JsonObject
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.coroutines.await
import org.blagij.diploma.common.logger
import org.json.JSONArray
import org.json.JSONObject
import org.json.XML
import org.jsoup.Jsoup


class HTTPGameClient(val client: WebClient) {

    private val bggUrl = "https://boardgamegeek.com"
    private val geekdoUrl = "https://api.geekdo.com/api/geekitem/linkeditems?linkdata_index=boardgame&nosession=1&objecttype=property&pageid=1&showcount=25&sort=rank&subtype=boardgamecategory"
    private val log = logger(this::class)

    suspend fun searchGame(name: String): List<Game> {
        return client.getAbs("$bggUrl/search/boardgame?nosession=1&q=$name&showcount=20")
            .send().await()
            .bodyAsString()
            .toGameList()
    }

    suspend fun getById(id: String): JSONObject {
        val response = client.getAbs("$bggUrl/xmlapi2/thing?id=$id")
            .send().await()
            .bodyAsString()

        val json: JSONObject = XML.toJSONObject(response).getJSONObject("items").getJSONObject("item")

        return json
            .addLinks("boardgamecategory")
            .addLinks("boardgamemechanic")
            .put("id", id)
    }


    suspend fun getByIds(id: String): JSONArray {
        val response = client.getAbs("$bggUrl/xmlapi2/thing?id=$id")
            .send().await()
            .bodyAsString()

        val arr: JSONArray = XML.toJSONObject(response).getJSONObject("items").getJSONArray("item")

        for (i in 0 until arr.length()) {
            arr.getJSONObject(i)
                .addLinks("boardgamecategory")
                .addLinks("boardgamemechanic")
        }

        return arr
    }

    suspend fun getGamesByTypes(gameTypeId: Int): JsonObject {
        return client.getAbs("$geekdoUrl&objectid=${gameTypeId}")
            .send().await()
            .bodyAsJsonObject()
    }
}

private fun JSONObject.addLinks(nameOfLink: String): JSONObject {
    val categories: List<String> = this.getJSONArray("link").toList()
        .filterIsInstance<Map<*, *>>()
        .filter { nameOfLink == it["type"] }
        .map { it["value"].toString() }

    this.put(nameOfLink, categories)
    return this
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
