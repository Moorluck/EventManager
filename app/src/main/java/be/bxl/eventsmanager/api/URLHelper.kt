package be.bxl.eventsmanager.api

class URLHelper {

    companion object {

        val apiKey = "c3fa448b20d4333b499f552522c429d3"
        val apiKeyGoogle = "AIzaSyC6n4YMDjAuQb402lAhP0aBRe35rh4ZFTE"

        val URLCurrentWeather = "https://api.openweathermap.org/data/2.5/weather?q=__city__&appid=$apiKey&units=metric"
        val URLNextWeather = "https://api.openweathermap.org/data/2.5/forecast?q=__city__&appid=$apiKey&units=metric"
        val URLLocation = "https://maps.googleapis.com/maps/api/geocode/json?latlng=__lat__,__long__&key=$apiKeyGoogle"
        val URLIcon = "https://openweathermap.org/img/w/__iconID__.png"

        val URLMeme = "https://meme-api.herokuapp.com/gimme"

    }
}