package com.imaginato.homeworkmvvm.data.remote.login

import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun getLoginData(username: String, password: String): Flow<LoginResponse>
}