package com.example.eduhub.data.api

import com.example.eduhub.ui.snackbar.AppSnackbarController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import com.example.eduhub.data.model.Result

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(
        val message: String = "Unknown error",
        val throwable: Throwable? = null
    ) : ApiResult<Nothing>()
}

suspend fun <T> safeApiCall(
    snackbarOnError: Boolean = true,
    block: suspend () -> T
): ApiResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResult.Success(block())
        } catch (e: IOException) {
            if (snackbarOnError) {
                AppSnackbarController.controller.showSnackbar(
                    "No internet connection", actionLabel = "Retry"
                )
            }
            ApiResult.Error("Network error", e)
        } catch (e: HttpException) {
            if (snackbarOnError) {
                AppSnackbarController.controller.showSnackbar(
                    "Server error: ${e.code()}", actionLabel = "Dismiss"
                )
            }
            ApiResult.Error("Server error: ${e.code()}", e)
        } catch (e: Exception) {
            if (snackbarOnError) {
                AppSnackbarController.controller.showSnackbar(
                    "Unknown error", actionLabel = "Dismiss"
                )
            }
            ApiResult.Error("Unknown error", e)
        }
    }
}

suspend fun <T> apiCallToResult(
    snackbarOnError: Boolean = true,
    block: suspend () -> T
): Result<T> {
    return when (val result = safeApiCall(snackbarOnError, block)) {
        is ApiResult.Success -> Result.Success(result.data)
        is ApiResult.Error -> Result.Error(result.throwable ?: Exception(result.message))
    }
}

