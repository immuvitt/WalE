package com.nasa.astronomy.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasa.astronomy.data.AstronomyPicture
import com.nasa.astronomy.db.database.AppDatabase
import com.nasa.astronomy.domain.AstronomyPictureRepository
import com.nasa.astronomy.network.APIClient
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AstronomyPictureViewModel(application: Application) : ViewModel() {

    private val _pictureData = MutableLiveData<AstronomyPicture>()
    val pictureData: LiveData<AstronomyPicture> get() = _pictureData

    private val repository: AstronomyPictureRepository

    init {
        val dao = AppDatabase.getDatabase(application).astronomyPictureDao()
        repository = AstronomyPictureRepository(dao, APIClient.apiService)
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        print(error)
    }

    fun fetchAstronomyPicture(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val result = repository.getAstronomyPicture(apiKey)
                _pictureData.postValue(result)
            } catch (e: Exception) {
                // handle exception
            }

        }
    }

}