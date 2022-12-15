package com.imaginato.homeworkmvvm.data.remote.login.response

import com.google.gson.annotations.SerializedName

data class LoginResponse constructor(

    @SerializedName("errorCode")
    var errorCode: String?,

    @SerializedName("errorMessage")
    var errorMessage: String?,

    @SerializedName("data")
    var data: ModelLoginData?,

    var x_acc: String?

)