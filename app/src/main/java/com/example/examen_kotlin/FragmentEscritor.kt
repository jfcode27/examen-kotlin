package com.example.examen_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton


class FragmentEscritor : Fragment(R.layout.fragment_escritor) {
    lateinit var userImage: ImageView
    lateinit var txtUserType: TextView
    lateinit var txtNickname: TextView
    lateinit var txtArtCount: TextView
    lateinit var btnShow: MaterialButton
    lateinit var btnEditar: MaterialButton
    lateinit var btnEliminar: MaterialButton
    lateinit var imgArt: ImageView
    lateinit var btnNext: ImageView
    lateinit var btnPrevious: ImageView
    lateinit var txtName: TextView

    lateinit var user: User
    lateinit var list: articlesList
    var index = 0
    lateinit var articles: Array<Article>
    lateinit var main: MainActivity

    override fun onResume() {
        super.onResume()
        main = activity as MainActivity
        user = requireArguments().getParcelable("loginUser")!!
        list = getArticles()
        views()
        evts()
    }

    private fun getArticlesWritter(): Array<Article> =
        list.articlesArray.filter { it.idWritter == user.id }.toTypedArray()

    private fun getArticles(): articlesList =
        main.sharedPreferences.getString(main.DOC_ARTICLES, null)?.let {
            return@let try {
                main.moshi.adapter(articlesList::class.java).fromJson(it)
            } catch (e: Exception) {
                articlesList()
            }
        } ?: articlesList()

    private fun evts() {
        btnPrevious.setOnClickListener {
            if ( index == 0 )
                index = articles.size-1
            else
                index--
            cambiarArticle()
        }

        btnNext.setOnClickListener {
            if ( index == (articles.size-1) )
                index = 0
            else
                index++
            cambiarArticle()
        }

        btnShow.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.render, FragmentArticulo().apply {
                arguments = Bundle().apply {
                    putString("tipo", "crear")
                    putParcelable("class", list)
                    putParcelable("user", user)
                }
            }).addToBackStack(FragmentArticulo().tag).commit()
        }

        btnEditar.setOnClickListener {
            val i = index
            if ( articles.size == 0 ) return@setOnClickListener
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.render, FragmentArticulo().apply {
                arguments = Bundle().apply {
                    putString("tipo", "actualizar")
                    putParcelable("articulo", articles[i])
                    putParcelable("class", list)
                }
            }).addToBackStack(FragmentArticulo().tag).commit()
        }

        btnEliminar.setOnClickListener {
            if ( articles.size == 0 ) return@setOnClickListener
            list.delete(articles[index])

            saveShared()
            list = getArticles()
            index = 0
            views()
            evts()

            Toast.makeText(context, "Artículo eliminado", Toast.LENGTH_LONG).show()
        }

        imgArt.setOnClickListener {
            if ( user.typeUser == TypeUser.READER )
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.render, FragmentArticulo().apply {
                    arguments = Bundle().apply {
                        putString("tipo", "ver")
                        putParcelable("articulo", articles[index])
                        putParcelable("class", list)
                        putParcelable("user", user)
                    }
                }).addToBackStack(FragmentArticulo().tag).commit()
        }
    }

    private fun saveShared() {
        main.sharedPreferences.edit().putString(main.DOC_ARTICLES, main.moshi.adapter(articlesList::class.java).toJson(list)).commit()
    }

    private fun cambiarArticle() {
        txtName.text = articles[index].name
        imgArt.setImageResource(articles[index].img)
    }

    private fun views() {
        if ( user.typeUser == TypeUser.WRITTER ) {
            articles = getArticlesWritter()
        } else {
            articles = list.articlesArray
        }

        userImage = requireView().findViewById(R.id.userImage)
        txtUserType = requireView().findViewById(R.id.txtUserType)
        txtNickname = requireView().findViewById(R.id.txtNickname)
        txtArtCount = requireView().findViewById(R.id.txtArtCount)
        btnEliminar = requireView().findViewById(R.id.btnEliminar)
        btnEditar = requireView().findViewById(R.id.btnEditar)
        btnShow = requireView().findViewById(R.id.btnShow)
        imgArt = requireView().findViewById(R.id.imgArt)
        btnNext = requireView().findViewById(R.id.btnNext)
        btnPrevious = requireView().findViewById(R.id.btnPrevious)
        txtName = requireView().findViewById(R.id.txtName)

        userImage.setImageResource(user.userImage)
        txtUserType.text = user.typeUser.toString()
        txtNickname.text = user.nickname


        if ( articles.size > 0 ) {
            txtName.text = articles[index].name
            imgArt.setImageResource(articles[index].img)
        }


        if ( user.typeUser == TypeUser.WRITTER ) {
            txtArtCount.text = articles.size.toString()
        } else {
            var likes = 0
            articles.forEach {
                it.userFavs.forEach {
                    if ( it.id == user.id )
                        likes++
                }
            }
            txtArtCount.text = likes.toString()
        }

        if ( user.typeUser != TypeUser.WRITTER )
            enabledViewer()
    }

    private fun enabledViewer() {
        btnShow.isVisible = false
        btnEditar.isVisible = false
        btnEliminar.isVisible = false
    }
}