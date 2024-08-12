package com.nasa.astronomy.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nasa.astronomy.db.dao.AstronomyPictureDao
import com.nasa.astronomy.db.entities.AstronomyPictureEntity


@Database(entities = [AstronomyPictureEntity::class], exportSchema = true, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun astronomyPictureDao(): AstronomyPictureDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "astronomy_db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}