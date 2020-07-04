package com.mohnage7.network.model

import com.google.gson.annotations.SerializedName

class PhotosResponse(@SerializedName("photos") val photosBody: PhotosBody)