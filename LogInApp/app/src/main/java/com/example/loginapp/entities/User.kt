package com.example.loginapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (val Username : String,
            val Password : String) : Parcelable{
}