package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import io.reactivex.Single
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface LoginApi {
    @POST
    fun getLoginDataAsync(
        @HeaderMap headers: Map<String, String>,
        @Url url: String,
        @Body body: String
    ): Deferred<Response<LoginResponse>>
}