package com.example.examen_kotlin

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentLogin.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentLogin : Fragment() {

    lateinit var imvCircular : ImageView
    lateinit var etUser : EditText
    lateinit var etPassword : EditText
    lateinit var button : Button





    val users = arrayOf(
        User(nickname = "Nathaniel Rice", TypeUser.WRITTER, password = "thisIsNoPassword", R.drawable.escritor),
        User(nickname = "Nathaniel Rice", TypeUser.WRITTER, password = "thisIsNoPassword", R.drawable.escritor2),
        User(nickname = "Jon Mendoza", TypeUser.WRITTER, password = "thisIsNoPassword", R.drawable.escritor3),
        User(nickname = "Erik Welch", TypeUser.WRITTER, password = "thisIsNoPassword", R.drawable.lector),
        User(nickname = "Jackie Harrison", TypeUser.WRITTER, password = "thisIsNoPassword", R.drawable.lector2),
        User(nickname = "Bobby Little", TypeUser.WRITTER, password = "thisIsNoPassword", R.drawable.lector3)
    )

    override fun onResume() {
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun evts() {
        etUser.addTextChangedListener(wtch)
        button.setOnClickListener {
            users.forEach {
                if ( it.password == etPassword.text.toString() )
                    childFragmentManager.beginTransaction().replace(R.id.render, escritor().apply {
                        arguments = Bundle().apply {
                            putParcelable("loginUser", it)
                        }
                    }).commit()
            }
        }
    }



    private fun views() {
        imvCircular = requireView().findViewById(R.id.imvCircular)
        etUser = requireView().findViewById(R.id.etUser)
        etPassword = requireView().findViewById(R.id.etPassword)
       button = requireView().findViewById(R.id.button)
    }
}