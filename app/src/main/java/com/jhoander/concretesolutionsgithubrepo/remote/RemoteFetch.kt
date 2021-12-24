package com.jhoander.concretesolutionsgithubrepo.remote

import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class RemoteFetch {

    companion object{
        private val GITHUB_API = "https://api.github.com/%s"
        fun getJSON(user: String?): JSONObject? {
            return try {
                val url = URL(String.format(GITHUB_API, user))
                val connection = url.openConnection() as HttpURLConnection
                val reader = BufferedReader(InputStreamReader(connection.inputStream))
                val json = StringBuffer(1024)
                var tmp: String? = ""
                while (reader.readLine().also { tmp = it } != null) json.append(tmp).append("\n")
                reader.close()
                val data = JSONObject(json.toString())
                Log.i("UserURL", "$url - $json")
                data
            } catch (e: Exception) {
                null
            }
        }
    }
}