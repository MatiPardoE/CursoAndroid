package com.example.loginapp.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.loginapp.R
import com.example.loginapp.entities.User

class WelcomeFragment : Fragment() {

    private lateinit var arg : User
    private lateinit var textWelcome : TextView
    private lateinit var v : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_welcome, container, false)

        textWelcome = v.findViewById(R.id.textWelcome)

        return v
    }

    override fun onStart() {
        super.onStart()

        arg = WelcomeFragmentArgs.fromBundle(requireArguments()).userFromLogIn
        textWelcome.text = "Welcome ${arg.Username}"


    }

}