package com.daeseong.businfo
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

object HttpUtil {

    fun getBusDataResult(urlString: String): String {
        val stringBuilder = StringBuilder()

        try {
            val url = URL(urlString)
            val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection

            httpURLConnection.allowUserInteraction = false
            httpURLConnection.instanceFollowRedirects = true
            httpURLConnection.requestMethod = "GET"

            val responseCode = httpURLConnection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                httpURLConnection.inputStream.use { inputStream ->
                    BufferedReader(InputStreamReader(inputStream)).use { bufferedReader ->
                        var line: String?
                        while (bufferedReader.readLine().also { line = it } != null) {
                            stringBuilder.append(line)
                        }
                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stringBuilder.toString()
    }
}
