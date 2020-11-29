package ankit.com.sampleapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ankit.com.sampleapp.data.database.converters.StatusTypeConverter
import ankit.com.sampleapp.data.entity.Restaurant

/**
 * Created by AnkitSingh on 11/27/20.
 */

const val DATABASE_VERSION = 1

@Database(
    entities = [Restaurant::class],
    version = DATABASE_VERSION
)
@TypeConverters(StatusTypeConverter::class)
abstract class RestaurantsDataBase : RoomDatabase() {
    abstract fun restaurantsDao(): RestaurantsDao

}