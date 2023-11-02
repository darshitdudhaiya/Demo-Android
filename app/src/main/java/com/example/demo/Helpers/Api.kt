package com.example.demo.Helpers

import java.net.HttpURLConnection
import java.net.URL

class Api {
    companion object {
        fun request(path: String): String {
            val url = URL(Config.prefixUrl + path)

            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"

            val reader = connection.inputStream.bufferedReader()
            return reader.readText()
        }
    }
}