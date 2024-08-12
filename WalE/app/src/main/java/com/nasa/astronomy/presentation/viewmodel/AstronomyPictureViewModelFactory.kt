package com.nasa.astronomy.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nasa.astronomy.db.dao.AstronomyPictureDao
import com.nasa.astronomy.domain.AstronomyPictureRepository
import com.nasa.astronomy.network.APIService

class AstronomyPictureViewModelFactory(
    private val apiService: APIService,
    private val astronomyPictureDao: AstronomyPictureDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AstronomyPictureViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AstronomyPictureViewModel(
                AstronomyPictureRepository(
                    astronomyPictureDao,
                    apiService
                )
            ) as T
        }
        return super.create(modelClass)
    }

}