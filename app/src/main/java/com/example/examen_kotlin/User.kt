package com.example.examen_kotlin

import android.os.Parcelable;
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
class User (
    var id: Int,
    var nickname: String,
    var typeUser: TypeUser = TypeUser.WRITTER,
    var password: String,
    var userImage: Int,
    var favs: Int = 0
) : Parcelable