package be.bxl.eventsmanager.api

import be.bxl.eventsmanager.models.Meme
import org.json.JSONObject

class MemeParse {
    companion object {
        fun parseJson(json : String) : Meme {
            val jsonObject = JSONObject(json)

            val author = jsonObject.getString("author")
            val imgUrl = jsonObject.getString("url")

            return Meme(author, imgUrl)
        }
    }
}