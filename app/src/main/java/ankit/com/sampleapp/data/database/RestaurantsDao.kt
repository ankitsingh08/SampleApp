package ankit.com.sampleapp.data.database

import androidx.room.*
import ankit.com.sampleapp.data.entity.Restaurant

/**
 * Created by AnkitSingh on 11/28/20.
 */
@Dao
interface RestaurantsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(restaurants: List<Restaurant>)

    @Query("SELECT * FROM restaurants ORDER BY favorite == 0")
    suspend fun getRestaurants(): List<Restaurant>

    @Query("DELETE FROM restaurants")
    suspend fun deleteAll()

    @Query("UPDATE restaurants SET favorite =:favorite WHERE name = :name ")
    suspend fun updateRestaurantsAsFavorite(name: String, favorite: Boolean)

    @Transaction
    suspend fun updateRestaurantsData(restaurants: List<Restaurant>) {
        deleteAll()
        insertAll(restaurants)
    }


}