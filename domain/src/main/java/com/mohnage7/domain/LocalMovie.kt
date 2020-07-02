package com.mohnage7.domain


class LocalMovie(
    val title: String? = null,
    val year: String? = null,
    val rating: String? = null,
    val genres: List<String>? = null,
    val cast: List<String>? = null
)