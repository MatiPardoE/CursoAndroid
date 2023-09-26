package com.example.loginapp.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.loginapp.R
import com.example.loginapp.activities.LogInActivity
import com.example.loginapp.activities.SettingsActivity

class UserDetailFragment : Fragment() {

    private val PREF_NAME = "myPreferences"
    private lateinit var v : View
    private var username : String = ""

    private lateinit var tvUserName : TextView
    private lateinit var tvUserEmail : TextView
    private lateinit var btnLogOut : Button
    private lateinit var btnSettings : Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user_detail, container, false)

        val sharedPref: SharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        username = sharedPref.getString("USER","default").toString()

        tvUserName = v.findViewById(R.id.textViewUserName)
        tvUserEmail = v.findViewById(R.id.textUserEmail)
        btnLogOut = v.findViewById(R.id.btnLogOut)
        btnSettings = v.findViewById(R.id.btnSettings)

        tvUserName.text = username
        tvUserEmail.text = "$username@gmail.com"

        btnLogOut.setOnClickListener {
            val editor = sharedPref.edit()

            editor.putString("USER", "")
            editor.apply()

            startActivity(Intent(activity, LogInActivity::class.java))
            activity?.finish()
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(context, SettingsActivity::class.java))
        }

        return v
    }


}