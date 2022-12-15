package com.imaginato.homeworkmvvm.data.remote.login

import android.widget.Toast
import com.imaginato.homeworkmvvm.data.remote.login.response.LoginResponse
import com.imaginato.homeworkmvvm.data.remote.login.response.ModelLoginData
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject

class LoginDataRepository constructor(
    private var api: LoginApi
) : LoginRepository {
    companion object {
        private const val URL_LOGIN = "https://private-222d3-homework5.apiary-mock.com/api/login"
        private const val NOTHING_GET = "Nothing get!"
    }


    override suspend fun getLoginData(username: String, password: String) = flow<LoginResponse> {

        val loginObj = JSONObject()

        try {
            loginObj.put("username", username)
            loginObj.put("password", password)
        } catch (e: Exception) {
            e.printStackTrace();
        }

        val requestHeaders = HashMap<String, String>()
        requestHeaders.put("Content-Type", "application/json")
        requestHeaders.put("IMSI", "357175048449937")
        requestHeaders.put("IMEI", "510110406068589")

        val response = api.getLoginDataAsync(requestHeaders, URL_LOGIN, loginObj.toString()).await()
        val headers = response.headers()
        val body = response.body();

        val loginResponse: LoginResponse =
            LoginResponse(
                errorCode = body?.errorCode,
                errorMessage = body?.errorMessage,
                data = body?.data,
                x_acc = headers.get("X-Acc")
            )

        emit(loginResponse)

    }.flowOn(Dispatchers.IO)

}