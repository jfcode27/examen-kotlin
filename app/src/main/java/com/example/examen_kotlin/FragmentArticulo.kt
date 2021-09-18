package com.example.examen_kotlin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible


class FragmentArticulo : Fragment(R.layout.fragment_articulo) {

    lateinit var artImage: ImageView
    lateinit var btnLike: ImageView
    lateinit var txtLikesArt: TextView
    lateinit var btnNext2: Button
    lateinit var btnPrevious2: Button
    lateinit var artTitle: EditText
    lateinit var artContent: EditText
    lateinit var btnAction: Button

    lateinit var article: Article
    var lstImages = arrayOf(
        R.drawable.art11,
        R.drawable.art21,
        R.drawable.art31,
        R.drawable.art4,
        R.drawable.art5
    )
    var index = 0
    lateinit var scndList: articlesList
    lateinit var main: MainActivity
    lateinit var scndUser: User

    override fun onResume() {
        super.onResume()

        views()
        evts()

        val tipo = requireArguments().getString("tipo")
        scndList = requireArguments().getParcelable("class")!!
        main = activity as MainActivity


        if ( tipo == "crear" ) {
            scndUser = requireArguments().getParcelable("user")!!
            btnAction.setOnClickListener {
                if ( artTitle.text.isEmpty() || artContent.text.isEmpty() )
                    Toast.makeText(context, "No deje campos vacios", Toast.LENGTH_LONG).show()
                else {
                    var id = 0
                    if ( scndList.arrArticles.size > 0 )
                        id = scndList.arrArticles[scndList.arrArticles.size-1].id++
                    val newArticle = Article(
                        id,
                        scndUser.id,
                        artTitle.text.toString(),
                        artContent.text.toString(),
                        lstImages[index]
                    )
                    scndList.push(newArticle)
                    println(newArticle.name)
                    saveShared()
                    requireActivity().supportFragmentManager.popBackStack()
                    Toast.makeText(context, "creado correctamente correctamente", Toast.LENGTH_LONG).show()
                }
            }
            btnLike.isVisible = false
            txtLikesArt.isVisible = false
            artImage.setImageResource(lstImages[index])
        } else if ( tipo == "actualizar" ) {
            article = requireArguments().getParcelable("articulo")!!
            artTitle.setText(article.name)
            artContent.setText(article.content)

            var ind = 0
            var flag = false
            lstImages.forEach {
                ind++
                if ( flag ) ind--
                if ( it == article.img )
                    flag = true
            }

            index = ind--
            artImage.setImageResource(lstImages[index])

            btnAction.setOnClickListener {
                if ( artTitle.text.isEmpty() || artContent.text.isEmpty() )
                    Toast.makeText(context, "No deje campos vacios", Toast.LENGTH_LONG).show()
                else {
                    article.name = artTitle.text.toString()
                    article.content = artContent.text.toString()
                    article.img = lstImages[index]
                    scndList.update(article)
                    saveShared()
                    requireActivity().supportFragmentManager.popBackStack()
                    Toast.makeText(context, "Actualizado correctamente", Toast.LENGTH_LONG).show()
                }
            }
            btnLike.isVisible = false
            txtLikesArt.isVisible = false
        } else if ( tipo == "ver" )  {
            scndUser = requireArguments().getParcelable("user")!!
            article = requireArguments().getParcelable("articulo")!!
            visibleLector()
            txtLikesArt.text = article.favs.size.toString()

            val isFav = article.favs.filter { it.id == scndUser.id }

            if ( isFav.size > 0 )
                btnLike.setImageResource(R.drawable.like)
            else
                btnLike.setImageResource(R.drawable.liked)

            btnLike.setOnClickListener {
                if ( isFav.size > 0 ) {
                    val newLikes = article.favs.filter { it.id != scndUser.id }
                    article.favs = newLikes.toTypedArray()
                    saveShared()
                    btnLike.setImageResource(R.drawable.liked)
                    txtLikesArt.text = article.favs.size.toString()
                } else {
                    article.favs = arrayOf(*article.favs, scndUser)
                    saveShared()
                    btnLike.setImageResource(R.drawable.like)
                    txtLikesArt.text = article.favs.size.toString()
                }
            }

        }

    }

    private fun saveShared() {
        main.sharedPreferences.edit().putString(main.DOC_ARTICLES, main.moshi.adapter(lstArticles::class.java).toJson(scndList)).commit()
    }
    }

    private fun visibleLector() {
        btnAction.isVisible = false
        btnNext2.isVisible = false
        btnPrevious2.isVisible = false
        artTitle.isEnabled = false
        artContent.isEnabled = false
    }

    private fun evts() {
        btnPrevious2.setOnClickListener {
            if ( index == 0 )
                index = lstImages.size-1
            else
                index--
            cambiarArticle()
        }

        btnNext2.setOnClickListener {
            if ( index == (lstImages.size-1) )
                index = 0
            else
                index++
            cambiarArticle()
        }
    }

    private fun cambiarArticle() {
        artImage.setImageResource(lstImages[index])
    }

    private fun views() {
        artImage = requireView().findViewById(R.id.artImage)
        btnLike = requireView().findViewById(R.id.btnLike)
        txtLikesArt = requireView().findViewById(R.id.txtLikesArt)
        btnNext2 = requireView().findViewById(R.id.btnNext2)
        btnPrevious2 = requireView().findViewById(R.id.btnPrevious2)
        artTitle = requireView().findViewById(R.id.artTitle)
        artContent = requireView().findViewById(R.id.artContent)
        btnAction = requireView().findViewById(R.id.btnAction)
    }

}