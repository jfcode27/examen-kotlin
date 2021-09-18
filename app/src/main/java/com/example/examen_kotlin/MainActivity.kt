package com.example.examen_kotlin

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.moshi.Moshi

class MainActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    var APP_CONFIG = "SAVE_PREFERENCES"
    var DOC_ARTICLES = "DOC_ARTICLES"
    var moshi = Moshi.Builder().build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(APP_CONFIG, Context.MODE_PRIVATE)

        supportFragmentManager.beginTransaction().add(R.id.render, FragmentLogin()).commit()
    }
}