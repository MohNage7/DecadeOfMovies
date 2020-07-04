package com.mohnage7.network.model

import com.google.gson.annotations.SerializedName
import com.mohnage7.domain.Photo

class PhotosBody(@SerializedName("photo") val photosList: List<Photo>)