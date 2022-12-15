package com.imaginato.homeworkmvvm.data.local.login

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "Login")
data class Login constructor(
    @ColumnInfo(name = "X_Acc")
    var x_acc: String,

    @ColumnInfo(name = "userId")
    var userId: String,
    @ColumnInfo(name = "userName")
    var userName: String,
    @ColumnInfo(name = "isDeleted")
    var isDeleted: String

) {
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L
}
