package com.nasa.astronomy.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nasa.astronomy.data.AstronomyPicture
import com.nasa.astronomy.domain.AstronomyPictureRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AstronomyPictureViewModel(private val repository: AstronomyPictureRepository) : ViewModel() {

    private val _pictureData = MutableLiveData<AstronomyPicture>()
    val pictureData: LiveData<AstronomyPicture> get() = _pictureData

    private val exceptionHandler = CoroutineExceptionHandler { _, error ->
        _pictureData.postValue(null)
    }

    fun fetchAstronomyPicture(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val result = repository.getAstronomyPicture(apiKey)
                _pictureData.postValue(result)
            } catch (e: Exception) {
                _pictureData.postValue(null)
            }

        }
    }

}