package com.example.examen_kotlin

import android.os.Parcelable;
import kotlinx.parcelize.Parcelize


@Parcelize
class User (
    var nickname: String,
    var typeUser: TypeUser = TypeUser.WRITTER,
    var password: String,
    var userImage: Int,
) : Parcelable