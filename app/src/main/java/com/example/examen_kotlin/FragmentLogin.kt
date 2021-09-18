package com.example.examen_kotlin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView

class FragmentLogin : Fragment() {

    lateinit var imvCircular : ImageView
    lateinit var etUser : EditText
    lateinit var etPassword : EditText
    lateinit var button : Button

    val users = arrayOf(
        User(0, nickname = "Nathaniel Rice", TypeUser.WRITTER, password = "123", R.drawable.escritor),
        User(1, nickname = "Harold Matus", TypeUser.WRITTER, password = "1234", R.drawable.escritor2),
        User(2, nickname = "Jon Mendoza", TypeUser.WRITTER, password = "12345", R.drawable.escritor3),
        User(3, nickname = "Erik Welch", TypeUser.READER, password = "123456", R.drawable.lector),
        User(4, nickname = "Jackie Harrison", TypeUser.READER, password = "321", R.drawable.lector2),
        User(5, nickname = "Bobby Little", TypeUser.READER, password = "3210", R.drawable.lector3)
    )

    override fun onResume() {
        super.onResume()

        views()
        evts()
    }

    private val wtch = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            var user: User? = null
            users.forEach {
                if ( it.nickname == p0.toString() ) {
                    user = it
                }
            }
            if ( user != null ) {
                button.isEnabled = true
                etPassword.isEnabled = true
                imvCircular.setImageResource(user!!.userImage)
            } else {
                button.isEnabled = false
                etPassword.isEnabled = false
                imvCircular.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }
        override fun afterTextChanged(p0: Editable?) {

        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    private fun evts() {
        etUser.addTextChangedListener(wtch)
        button.setOnClickListener {
            users.forEach {
                if ( it.password == etPassword.text.toString() )
                    requireActivity().supportFragmentManager.beginTransaction().replace(R.id.render, FragmentEscritor().apply {
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