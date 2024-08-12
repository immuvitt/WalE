package com.nasa.astronomy.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nasa.astronomy.db.entities.AstronomyPictureEntity

@Dao
interface AstronomyPictureDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAstronomyPicture(astronomyPictureEntity: AstronomyPictureEntity)

    @Query("SELECT * FROM astronomypicture_table WHERE date = :date LIMIT 1")
    suspend fun getAstronomyPicture(date: String): AstronomyPictureEntity?
}