package com.kproject.kmemes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kproject.kmemes.repository.ImageRepository
import com.kproject.kmemes.repository.ImageResultCallback
import com.kproject.kmemes.utils.Constants
import kotlinx.coroutines.launch

class MainViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {
    val imagesLiveData = MutableLiveData<List<String>>()
    val resultLiveData = MutableLiveData<Int>()

    fun getImages() {
        viewModelScope.launch {
            imageRepository.getImages { imageResult ->
                when (imageResult) {
                    is ImageResultCallback.Success -> {
                        imagesLiveData.postValue(imageResult.imageList)
                        resultLiveData.postValue(Constants.ResultCallback.SUCCESS)
                    }
                    is ImageResultCallback.Error -> {
                        resultLiveData.postValue(Constants.ResultCallback.ERROR)
                    }
                }
            }
        }
    }

    class MainViewModelFactory constructor(private val repository: ImageRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                MainViewModel(this.repository) as T
            } else {
                throw IllegalArgumentException("ViewModel not found")
            }
        }
    }
}