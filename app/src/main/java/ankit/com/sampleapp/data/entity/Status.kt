package ankit.com.sampleapp.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by AnkitSingh on 11/27/20.
 */
enum class Status(val value: String) {
    @SerializedName("open")
    OPEN("open"),
    @SerializedName("order ahead")
    ORDERAHEAD("order ahead"),
    @SerializedName("closed")
    CLOSE("closed")
}