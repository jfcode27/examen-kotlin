package com.example.examen_kotlin

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Article (
    var name: String,
    var content: String,
    var img: Int
):Parcelable