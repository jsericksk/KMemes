package com.kproject.kmemes.model

/**
 * It is necessary to have predefined null values for the class to be correctly
 * deserialized by Firebase Realtime Database, as it requires an empty public constructor.
 */
data class Image(val imageUrl: String? = null, val imageName: String? = null)