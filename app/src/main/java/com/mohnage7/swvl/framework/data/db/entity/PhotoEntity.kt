package com.mohnage7.swvl.framework.data.db.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class PhotoEntity(
    @NonNull
    @PrimaryKey var id: String,

    @ColumnInfo(name = "photos_list")
    var photosList: List<String>? = null

)
