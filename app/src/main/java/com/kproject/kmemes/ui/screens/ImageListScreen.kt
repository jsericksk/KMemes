package com.kproject.kmemes.ui.screens

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.kproject.kmemes.R
import com.kproject.kmemes.repository.ImageRepository
import com.kproject.kmemes.ui.ImageViewerActivity
import com.kproject.kmemes.ui.MainViewModel
import com.kproject.kmemes.utils.Constants

@ExperimentalFoundationApi
@Composable
fun ImageListScreen(
    mainViewModel: MainViewModel = viewModel(
        factory = MainViewModel.MainViewModelFactory(ImageRepository())
    )
) {
    val imageUrlList by mainViewModel.imagesLiveData.observeAsState()
    val result by mainViewModel.resultLiveData.observeAsState(initial = Constants.ResultCallback.LOADING)

    mainViewModel.getImages()

    when (result) {
        Constants.ResultCallback.LOADING -> {
            Loading()
        }
        Constants.ResultCallback.SUCCESS -> {
            imageUrlList?.let { images ->
                LazyVerticalGrid(
                    cells = GridCells.Fixed(3)
                ) {
                    items(images) { imageUrl ->
                        ImageListItem(imageUrl)
                    }
                }
            }
        }
        Constants.ResultCallback.ERROR -> {
            ErrorMessage()
        }
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ImageListItem(imageUrl: String) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = 10.dp,
    ) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl,
                builder = {
                    placeholder(R.drawable.loading_meme)
                }
            ),
            contentDescription = null,
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(200.dp)
                .height(150.dp)
                .clickable {
                    context.startActivity(
                        Intent(context, ImageViewerActivity::class.java)
                            .putExtra("imageUrl", imageUrl)
                    )
                }
        )
    }
}

@Composable
fun ErrorMessage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_image_error),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(text = stringResource(id = R.string.error_gettin_image_list))
    }
}