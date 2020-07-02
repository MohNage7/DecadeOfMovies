package com.mohnage7.swvl.presentation.movies.model

import android.os.Parcel
import android.os.Parcelable

class Movie(
    val title: String? = null,
    val year: String? = null,
    val genresList: List<String>? = null,
    val castList: List<String>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    ) {
    }

    fun getGenresAsString(): String? {
        return genresList?.joinToString(separator = ", ")
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(year)
        parcel.writeStringList(genresList)
        parcel.writeStringList(castList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}