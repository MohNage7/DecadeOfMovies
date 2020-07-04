package com.mohnage7.network

import retrofit2.HttpException
import java.io.IOException
import javax.net.ssl.HttpsURLConnection

/**
 * Naive error handling class for the sake of saving time.
 */
fun Throwable.handleError(): String {
    val genericError = "An error occur !! Please try again later."
    return when (val throwable = this) {
        is HttpException -> {
            when (throwable.code()) {
                HttpsURLConnection.HTTP_BAD_REQUEST -> "Bad Request"
                HttpsURLConnection.HTTP_UNAUTHORIZED -> "Unauthorized"
                HttpsURLConnection.HTTP_FORBIDDEN -> "Forbidden"
                HttpsURLConnection.HTTP_NOT_FOUND -> "Not Found"
                HttpsURLConnection.HTTP_INTERNAL_ERROR -> "Server Error"
                else -> throwable.localizedMessage
            }
        }
        is IOException -> "Request timeout, Please check your internet connection."
        else -> genericError
    }
}