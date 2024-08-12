package com.nasa.astronomy.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AstronomyPicture_table")
data class AstronomyPictureEntity(
    @PrimaryKey val date: String,
    val title: String,
    val explanation: String,
    val url: String
)
