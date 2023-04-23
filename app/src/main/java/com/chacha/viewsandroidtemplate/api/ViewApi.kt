package com.chacha.viewsandroidtemplate.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ViewApi {
    @POST("")
    suspend fun firstTimeLogin(
        @Body login: Login
    ):Response<Any>

    suspend fun firstTimerLogin(
        @Body login: Login
    ):ApiResponse<Any>

    data class Login(
        val Email: String?,
        val Password:String,
        val PhoneNumber: String?
    )
}