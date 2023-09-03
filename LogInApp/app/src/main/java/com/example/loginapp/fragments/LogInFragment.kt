package com.example.loginapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.loginapp.R
import com.example.loginapp.entities.User
import com.google.android.material.snackbar.Snackbar

class LogInFragment : Fragment() {

    lateinit var inputUsername : EditText
    lateinit var inputPassword : EditText
    lateinit var buttonLogIn: Button
    lateinit var v : View
    val userList = listOf(
        User("User1", "123"),
        User("User2", "456"),
        User("User3", "789")
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_log_in, container, false)

        inputUsername = v.findViewById(R.id.inputUsername)
        inputPassword = v.findViewById(R.id.inputPassword)
        buttonLogIn = v.findViewById(R.id.buttonLogIn)

        return v
    }

    override fun onStart() {
        super.onStart()

        buttonLogIn.setOnClickListener {
            val username : String = inputUsername.text.toString()
            val password : String = inputPassword.text.toString()
            val action = LogInFragmentDirections.actionLogInFragmentToCoffeeRecipeFragment()
            val snackbarEmpty = Snackbar.make(v,"Fill the field",Snackbar.LENGTH_SHORT)
            val snackbarNotUser = Snackbar.make(v,"User Not Found", Snackbar.LENGTH_SHORT)
            val snackbarWrongPass = Snackbar.make(v,"Wrong Password", Snackbar.LENGTH_SHORT)

            when(checkCredentials(userList,username,password)){
                EMPTY_FIELDS -> snackbarEmpty.show()
                USER_NOT_FOUND -> snackbarNotUser.show()
                WRONG_PASSWORD -> snackbarWrongPass.show()
                LOGIN_OK -> findNavController().navigate(action)
                else -> Log.d("Error","Algo salio mal con la funcion checkCredential")
            }
        }
    }

    private fun checkCredentials(users: List<User>, inputUsername: String, inputPassword: String): Int {
        if (inputUsername.isEmpty() || inputPassword.isEmpty()) return EMPTY_FIELDS

        if (!users.any { it.Username == inputUsername }) return USER_NOT_FOUND

        val userTarget = users.firstOrNull{it.Username == inputUsername}
        if(userTarget!!.Password != inputPassword) return WRONG_PASSWORD

        return LOGIN_OK
    }

    companion object {
        const val EMPTY_FIELDS = 0
        const val USER_NOT_FOUND = 1
        const val WRONG_PASSWORD = 2
        const val LOGIN_OK = 3
    }

}