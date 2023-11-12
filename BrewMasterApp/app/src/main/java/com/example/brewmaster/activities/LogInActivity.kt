package com.example.brewmaster.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.brewmaster.R
import com.example.brewmaster.database.AppDatabase
import com.example.brewmaster.database.UserDao
import com.example.brewmaster.entities.User
import com.google.android.material.snackbar.Snackbar

class LogInActivity : AppCompatActivity() {

    private val preferenceName = "myPreferences"

    private lateinit var inputUsername : EditText
    private lateinit var inputPassword : EditText
    private lateinit var buttonLogIn: Button
    private lateinit var buttonSignIn: Button
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var userList : MutableList<User?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        inputUsername = findViewById(R.id.inputUsername)
        inputPassword = findViewById(R.id.inputPassword)
        buttonLogIn = findViewById(R.id.buttonLogIn)
        buttonSignIn = findViewById(R.id.buttonSignIn)

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
                    val sharedPref: SharedPreferences = this.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()

                    editor.putString("USER", username)
                    editor.apply()

                    startActivity(Intent(this, SignUpActivity::class.java))
                    finish()
                }
                else -> Log.d("Error", "Algo salio mal con la funcion checkCredential")
            }
        }

        buttonSignIn.setOnClickListener {
            val username: String = inputUsername.text.toString()
            val password: String = inputPassword.text.toString()
            //val action = LogInFragmentDirections.actionLogInFragmentToCoffeeRecipeFragment()
            val snackbarEmpty = Snackbar.make(it, "Fill the field", Snackbar.LENGTH_SHORT)
            val snackbarUserUsed = Snackbar.make(it, "User already exist", Snackbar.LENGTH_SHORT)
            val snackbarSignInOk = Snackbar.make(it, "User created successful", Snackbar.LENGTH_SHORT)



            when (checkCredentialsSignIn(userList, username, password)) {
                EMPTY_FIELDS -> snackbarEmpty.show()
                USER_EXIST -> snackbarUserUsed.show()
                SIGNIN_OK -> {
                    userDao?.insertUser(User(0,username,password))
                    snackbarSignInOk.show()
                    userList = userDao?.fetchAllUsers().orEmpty().toMutableList() //Leo de nuevo la DB
                }
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

    private fun checkCredentialsSignIn(users: MutableList<User?>?, inputUsername: String, inputPassword: String): Int {
        if (users != null){
            if (inputUsername.isEmpty() || inputPassword.isEmpty()) return EMPTY_FIELDS
            return if (!users.any { it!!.username == inputUsername }){
                SIGNIN_OK
            }else{
                USER_EXIST
            }
        }else{
            return EMPTY_DB
        }
    }

    companion object {
        const val EMPTY_FIELDS = 0
        const val USER_NOT_FOUND = 1
        const val WRONG_PASSWORD = 2
        const val LOGIN_OK = 3
        const val EMPTY_DB = 4
        const val SIGNIN_OK = 5
        const val USER_EXIST = 6
    }
}