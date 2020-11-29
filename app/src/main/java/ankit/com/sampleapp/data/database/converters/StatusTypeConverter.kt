package ankit.com.sampleapp.data.database.converters

import androidx.room.TypeConverter
import ankit.com.sampleapp.data.entity.Status

/**
 * Created by AnkitSingh on 11/28/20.
 */
class StatusTypeConverter {
    @TypeConverter
    fun fromStatus(value: String): Status {
        return Status.valueOf(value)
        }

    @TypeConverter
    fun toStatus(value: Status): String{
        return value.name
    }
    }
