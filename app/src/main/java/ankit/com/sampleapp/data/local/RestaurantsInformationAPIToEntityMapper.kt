package ankit.com.sampleapp.data.local

import ankit.com.sampleapp.data.entity.RestaurantsResponse
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Created by AnkitSingh on 11/25/20.
 */
class RestaurantsInformationAPIToEntityMapper @Inject constructor(
    private val gson: Gson
) {
    @Throws(JsonSyntaxException::class)
    fun transformTeamEntityCollection(teamListJsonResponse: String): RestaurantsResponse {
        return try {
            val typeTeamEntityList: Type =
                object : TypeToken<RestaurantsResponse>() {}.type
             gson.fromJson(teamListJsonResponse, typeTeamEntityList)
        } catch (exception: JsonSyntaxException) {
            exception.printStackTrace()
            throw exception
        }
    }
}