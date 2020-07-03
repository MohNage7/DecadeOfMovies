package com.mohnage7.swvl.framework.data.db.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @NonNull
    @PrimaryKey var id: String,
    var farm: String? = null,
    var server: String? = null,
    var secret: String? = null
)
