package com.kproject.kmemes.utils

object Constants {
    object ResultCallback {
        const val LOADING = 0
        const val SUCCESS = 1
        const val ERROR = 2
    }

    /**
     * Array name in Realtime Database JSON to be used as a
     * reference to fetch images.
     */
    object MemeList {
        const val POPULAR = "popular"
        const val ANIME = "anime"
    }

    /**
     * Meme page to be used in Pager.
     */
    object MemePage {
        const val POPULAR = 0
        const val ANIME = 1
    }
}