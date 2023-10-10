package com.example.brewmaster.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Parcelize
@Entity(tableName = "Users")
data class User (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "username")val username : String,
    @ColumnInfo(name = "password")val password : String,
    @ColumnInfo(name = "weightUnit")val weightUnit : String = "Gramos"
) : Parcelable{
}