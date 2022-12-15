package com.imaginato.homeworkmvvm.data.local.login

import androidx.annotation.RequiresPermission.Read
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLoginData(login: Login)
}
