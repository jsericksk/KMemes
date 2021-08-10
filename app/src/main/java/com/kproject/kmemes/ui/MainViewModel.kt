package com.kproject.kmemes.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kproject.kmemes.model.Image
import com.kproject.kmemes.repository.ImageRepository
import com.kproject.kmemes.repository.ImageResultCallback
import com.kproject.kmemes.utils.Constants
import kotlinx.coroutines.launch

class MainViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {
    val popularMemeListLiveData = MutableLiveData<List<Image>>()
    val animeMemeListLiveData = MutableLiveData<List<Image>>()

    val resultLiveData = MutableLiveData<Int>()

    fun getImages(memeList: String) {
        viewModelScope.launch {
            imageRepository.getImages(memeList) { imageResult ->
                when (imageResult) {
                    is ImageResultCallback.Success -> {
                        if (memeList == Constants.MemeList.POPULAR) {
                            popularMemeListLiveData.postValue(imageResult.imageList)
                        } else if (memeList == Constants.MemeList.ANIME) {
                            animeMemeListLiveData.postValue(imageResult.imageList)
                        }
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