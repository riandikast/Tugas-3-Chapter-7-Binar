package com.restoran.listmakanan.room

import androidx.room.*

@Dao
interface FavoriteFilmDao {
    @Insert
    fun addMakanan (favoriteMakanan: FavoriteMakanan) : Long
    @Delete
    fun deleteFav(favoriteMakanan: FavoriteMakanan ):Int
    @Query("SELECT * FROM Fav WHERE Fav.email = :email AND Fav.id = :id ")
    fun checkFav(email: String, id: Int): List<FavoriteMakanan>
    @Query("SELECT * FROM Fav WHERE Fav.email = :email ")
    fun getFav(email: String): List<FavoriteMakanan>
    @Query("DELETE FROM Fav WHERE Fav.email = :email AND Fav.id = :id")
    fun deleteMakananID(email :String, id:Int): Int
    @Query("SELECT *  FROM Fav")
    fun getAllFav(): List<FavoriteMakanan>

}