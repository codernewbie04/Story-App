package com.akmalmf.storyapp.data.abstraction

import com.akmalmf.storyapp.domain.utils.fromJson
import com.google.gson.Gson
import retrofit2.HttpException

/**
 * Created by Akmal Muhamad Firdaus on 30/04/2023 14:49.
 * akmalmf007@gmail.com
 */
class HttpExceptionError {
    inline fun <reified T> parse(throwable: HttpException): Resource<T> {
        return when (throwable.code()) {
            in 400..451 -> {
                val resource = try {
                    val errorBody = throwable.response()?.errorBody()?.charStream()?.readText()
                        ?: "Unknown HTTP error body"
                    val errorResponse = Gson().fromJson<T>(errorBody)
                    Resource.error(
                        data = errorResponse,
                        message = null,
                        code = throwable.code(),
                        cause = HttpResult.CLIENT_ERROR
                    )
                } catch (e: Exception) {

                    Resource.error(
                        data = null,
                        message = e.message.toString(),
                        code = throwable.code(),
                        cause = HttpResult.CLIENT_ERROR
                    )
                }
                resource
            }
            in 500..599 -> Resource.error(
                data = null,
                message = "Server sedang bermasalah",
                code = throwable.code(),
                cause = HttpResult.SERVER_ERROR
            )

            else -> Resource.error(
                data = null,
                message = "Undefined error",
                code = throwable.code(),
                cause = HttpResult.NOT_DEFINED
            )
        }
    }
}