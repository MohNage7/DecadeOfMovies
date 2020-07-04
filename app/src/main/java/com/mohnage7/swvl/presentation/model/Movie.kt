package com.mohnage7.swvl.presentation.model

import android.os.Parcel
import android.os.Parcelable

class Movie(
    val title: String? = null,
    val year: String? = null,
    val rating: String? = null,
    private val genresList: List<String>? = null,
    private val castList: List<String>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createStringArrayList()
    ) {
    }

    fun getGenresListAsString(): String? {
        return genresList?.joinToString(separator = ", ")
    }

    fun getCastListAsString(): String? {
        var cast = ""
        castList?.forEach {
            cast = cast.plus("• ".plus(it).plus("\n"))
        }
        return cast
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(year)
        parcel.writeString(rating)
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

    /**
     * Because the local file doesn't have any id to be able to build a relationship between the movie
     * and its photos we will generate an Id to insert and retrieve the data with.
     */
    fun getMovieUniqueId(): String {
        return "${title?.replace("\\s+".toRegex(), "")?.trim()}_${year}"
    }
}