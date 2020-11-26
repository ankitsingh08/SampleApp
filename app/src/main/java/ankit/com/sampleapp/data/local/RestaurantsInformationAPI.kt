package ankit.com.sampleapp.data.local

import android.content.Context
import java.io.IOException
import javax.inject.Inject

/**
 * Created by AnkitSingh on 11/25/20.
 */
const val RESTAURANTS_DATA_FILE = "restaurants_data.json"
class RestaurantsInformationAPI @Inject constructor(private val context: Context) {

    fun fetchRestaurantsInformation(): String {
        val json: String
        try {
            val inputStream = context.assets.open(RESTAURANTS_DATA_FILE)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.use { it.read(buffer) }
            json = String(buffer)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return ""
        }
        return json
    }
}