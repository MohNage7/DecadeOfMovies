package com.mohnage7.swvl.framework.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohnage7.domain.LocalMovie
import com.mohnage7.local.MoviesLocalDataSource
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.lang.reflect.Type


class MoviesLocalDataSourceImpl(private val context: Context) : MoviesLocalDataSource {

    override fun getMovies(): List<LocalMovie> {
        val jsonFileString = inputStreamToString()
        val jsonArrayString = getArrayStringFrom(jsonFileString)
        val listUserType: Type = object : TypeToken<List<LocalMovie?>?>() {}.type
        return Gson().fromJson(jsonArrayString, listUserType)
    }

    private fun getArrayStringFrom(jsonFileString: String?): String? {
        var jsonString: String? = null
        if (!jsonFileString.isNullOrEmpty()) {
            val jsonObject = JSONObject(jsonFileString)
            val jsonArray = jsonObject.getJSONArray("movies") as JSONArray
            jsonString = jsonArray.toString()
        }
        return jsonString
    }

    private fun inputStreamToString(): String? {
        val jsonString: String
        jsonString = try {
            val inputStream = context.assets.open("movies.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }
}