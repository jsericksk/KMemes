package com.kproject.kmemes.ui.screens

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.kproject.kmemes.R
import com.kproject.kmemes.model.Image
import com.kproject.kmemes.navigation.Screen
import com.kproject.kmemes.repository.ImageRepository
import com.kproject.kmemes.ui.MainViewModel
import com.kproject.kmemes.utils.Constants
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                elevation = 0.dp
            )
        }
    ) {
        PagerTab(navController = navController)
    }
}

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun PagerTab(navController: NavController) {
    val pages = listOf(
        stringResource(id = R.string.popular_meme_list),
        stringResource(id = R.string.anime_meme_list)
    )
    val pagerState = rememberPagerState(pageCount = pages.size)
    val scrollCoroutine = rememberCoroutineScope()

    Column {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
        ) {
            pages.forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scrollCoroutine.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            if (page == Constants.MemePage.POPULAR) {
                ImageList(
                    navController = navController,
                    memeList = Constants.MemeList.POPULAR
                )
            } else if (page == Constants.MemePage.ANIME) {
                ImageList(
                    navController = navController,
                    memeList = Constants.MemeList.ANIME
                )
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ImageList(
    mainViewModel: MainViewModel = viewModel(
        factory = MainViewModel.MainViewModelFactory(ImageRepository())
    ),
    navController: NavController,
    memeList: String
) {
    val popularMemeList by mainViewModel.popularMemeListLiveData.observeAsState()
    val animeMemeList by mainViewModel.animeMemeListLiveData.observeAsState()
    val result by mainViewModel.resultLiveData.observeAsState(initial = Constants.ResultCallback.LOADING)

    mainViewModel.getImages(memeList)

    when (result) {
        Constants.ResultCallback.LOADING -> {
            Loading()
        }
        Constants.ResultCallback.SUCCESS -> {
            if (memeList == Constants.MemeList.POPULAR) {
                popularMemeList?.let { images ->
                    GridList(navController = navController, imageList = images)
                }
            } else if (memeList == Constants.MemeList.ANIME) {
                animeMemeList?.let { images ->
                    GridList(navController = navController, imageList = images)
                }
            }
        }
        Constants.ResultCallback.ERROR -> {
            ErrorMessage()
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun GridList(navController: NavController, imageList: List<Image>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(3),
        Modifier.fillMaxSize()
    ) {
        items(imageList) { image ->
            ImageListItem(navController, image.imageUrl!!)
        }
    }
}

@Composable
fun ImageListItem(navController: NavController, imageUrl: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = 8.dp,
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
                    /**
                     * It is necessary to encode the URL so that there are no errors when passing
                     * the argument through Navigation.
                     */
                    navController.navigate(Screen.ImageViewerScreen.withArgs(Uri.encode(imageUrl)))
                }
        )
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