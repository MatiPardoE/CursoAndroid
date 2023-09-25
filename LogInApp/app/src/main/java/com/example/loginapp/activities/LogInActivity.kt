package com.example.loginapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.loginapp.R
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.database.UserDao
import com.example.loginapp.entities.User
import com.google.android.material.snackbar.Snackbar

class LogInActivity : AppCompatActivity() {
    private lateinit var inputUsername : EditText
    private lateinit var inputPassword : EditText
    private lateinit var buttonLogIn: Button
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var userList : MutableList<User?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        inputUsername = findViewById(R.id.inputUsername)
        inputPassword = findViewById(R.id.inputPassword)
        buttonLogIn = findViewById(R.id.buttonLogIn)

        WindowCompat.setDecorFitsSystemWindows(window, false)

    }

    override fun onStart() {
        super.onStart()

        db = AppDatabase.getInstance(this)
        userDao = db?.userDao()

        userDao?.fetchAllUsers()

        userList = userDao?.fetchAllUsers().orEmpty().toMutableList()


        buttonLogIn.setOnClickListener {
            val username: String = inputUsername.text.toString()
            val password: String = inputPassword.text.toString()
            //val action = LogInFragmentDirections.actionLogInFragmentToCoffeeRecipeFragment()
            val snackbarEmpty = Snackbar.make(it, "Fill the field", Snackbar.LENGTH_SHORT)
            val snackbarNotUser = Snackbar.make(it, "User Not Found", Snackbar.LENGTH_SHORT)
            val snackbarWrongPass = Snackbar.make(it, "Wrong Password", Snackbar.LENGTH_SHORT)


            when (checkCredentials(userList, username, password)) {
                EMPTY_FIELDS -> snackbarEmpty.show()
                USER_NOT_FOUND -> snackbarNotUser.show()
                WRONG_PASSWORD -> snackbarWrongPass.show()
                LOGIN_OK -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else -> Log.d("Error", "Algo salio mal con la funcion checkCredential")
            }
        }
    }

    private fun checkCredentials(users: MutableList<User?>?, inputUsername: String, inputPassword: String): Int {
        if (users != null){
            if (inputUsername.isEmpty() || inputPassword.isEmpty()) return EMPTY_FIELDS
            if (!users.any { it!!.username == inputUsername }) return USER_NOT_FOUND

            val userTarget = users.firstOrNull{ it!!.username == inputUsername}
            if(userTarget!!.password != inputPassword) return WRONG_PASSWORD
        }else{
            return USER_NOT_FOUND
        }
        return LOGIN_OK
    }

    companion object {
        const val EMPTY_FIELDS = 0
        const val USER_NOT_FOUND = 1
        const val WRONG_PASSWORD = 2
        const val LOGIN_OK = 3
    }
}