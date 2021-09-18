package com.example.examen_kotlin

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
@Parcelize
class Article (
    var idWritter: Int,
    var id : Int,
    var name: String,
    var content: String,
    var img: Int,
    var favs: Int = 0,
    var userFavs: Array<User> = arrayOf()
):Parcelable

@JsonClass(generateAdapter = true)
@Parcelize
class articlesList(
    var articlesArray: Array<Article> = arrayOf()
):Parcelable {
    fun push(article : Article) {
        articlesArray = arrayOf(*articlesArray, article)
    }

    fun delete(article: Article){
        val auxArr = articlesArray.filter { it.id == article.id}
    }
}