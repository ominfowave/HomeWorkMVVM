package com.imaginato.homeworkmvvm.data.remote.login.response

import com.google.gson.annotations.SerializedName

data class ModelLoginData constructor(

    @SerializedName("userId")
    var userId: String?,

    @SerializedName("userName")
    var userName: String?,

    @SerializedName("isDeleted")
    var isDeleted: Boolean

)