package com.nasa.astronomy.domain

import com.nasa.astronomy.data.AstronomyPicture
import com.nasa.astronomy.db.dao.AstronomyPictureDao
import com.nasa.astronomy.db.entities.AstronomyPictureEntity
import com.nasa.astronomy.network.APIService
import java.time.LocalDate

class AstronomyPictureRepository(
    private val astronomyPictureDao: AstronomyPictureDao,
    private val apiService: APIService
) {

    suspend fun getAstronomyPicture(apiKey: String): AstronomyPicture {
        val today = LocalDate.now().toString()
        val cachedPicture = astronomyPictureDao.getAstronomyPicture(today)

        return if (cachedPicture != null) {

            AstronomyPicture(
                cachedPicture.date,
                cachedPicture.explanation,
                cachedPicture.title,
                cachedPicture.url
            )
        } else {
            val astronomyPicture = apiService.getAstronomyPicture(apiKey)
            astronomyPictureDao.insertAstronomyPicture(
                AstronomyPictureEntity(
                    astronomyPicture.date,
                    astronomyPicture.explanation,
                    astronomyPicture.title,
                    astronomyPicture.url
                )
            )
            astronomyPicture
        }

    }
}

