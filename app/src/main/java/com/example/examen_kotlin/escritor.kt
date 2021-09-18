package com.example.examen_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible


class escritor : Fragment(R.layout.fragment_escritor) {
    lateinit var userImage: ImageView
    lateinit var txtUserType: TextView
    lateinit var txtNickname: TextView
    lateinit var txtArtCount: TextView
    lateinit var btnShow: Button
    lateinit var btnEditar: Button
    lateinit var btnEliminar: Button
    lateinit var imgArt: ImageView
    lateinit var btnNext: Button
    lateinit var btnPrevious: Button
    lateinit var txtName: TextView

    lateinit var user: User
    lateinit var articles: Array<Article>
    var index = 0

    override fun onResume() {
        super.onResume()

        user = requireArguments().getParcelable("LoginUser")!!
        articles = kotlin.arrayOf(
            Article("Próximas películas", "2021", R.drawable.art11)
        )

        views()
        evts()
    }

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
            childFragmentManager.beginTransaction().replace(R.id.render, articulo().apply {
                arguments = Bundle().apply {
                    putString("tipo", "craer")
                }
            }).addToBackStack(articulo().tag).commit()
        }

        btnEditar.setOnClickListener {
            childFragmentManager.beginTransaction().replace(R.id.render, articulo().apply {
                arguments = Bundle().apply {
                    putString("tipo", "actualizar")
                    putParcelable("articulo", articles[index])
                }
            }).addToBackStack(articulo().tag).commit()
        }

        btnEliminar.setOnClickListener {
            TODO("Eliminar")
        }
    }

    private fun cambiarArticle() {
        txtName.text = articles[index].name
        imgArt.setImageResource(articles[index].img)
    }

    private fun views() {
        userImage = requireView().findViewById(R.id.userImage)
        txtUserType = requireView().findViewById(R.id.txtUserType)
        txtNickname = requireView().findViewById(R.id.txtNickname)
        txtArtCount = requireView().findViewById(R.id.txtArtCount)
        btnEliminar = requireView().findViewById(R.id.btnEliminar)
        btnEditar = requireView().findViewById(R.id.btnEditar)
        btnShow = requireView().findViewById(R.id.btnEliminar)
        imgArt = requireView().findViewById(R.id.imgArt)
        btnNext = requireView().findViewById(R.id.btnNext)
        btnPrevious = requireView().findViewById(R.id.btnPrevious)
        txtName = requireView().findViewById(R.id.txtName)

        userImage.setImageResource(user.userImage)
        txtUserType.text = user.typeUser.toString()
        txtNickname.text = user.nickname


        txtName.text = articles[index].name
        imgArt.setImageResource(articles[index].img)

        if ( user.typeUser != TypeUser.WRITTER )
            enabledViewer()

    }

    private fun enabledViewer() {
        btnShow.isVisible = false
        btnEditar.isVisible = false
        btnEliminar.isVisible = false
    }
}