package com.example.brewmaster.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.brewmaster.R
import com.example.brewmaster.databinding.ActivitySignInBinding
import com.example.brewmaster.databinding.ActivitySignUpBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    private val preferenceName = "myPreferences"
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if (checkAllField()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, "Successfully sign in", Toast.LENGTH_SHORT).show()

                        //TODO NExt Activity

                        val sharedPref: SharedPreferences =
                            this.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
                        val editor = sharedPref.edit()

                        editor.putString("USER", email)
                        editor.apply()

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Sign In error, check credentials", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("error:", it.exception.toString())
                    }
                }
            }
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }

    private fun checkAllField(): Boolean {
        val email = binding.etEmail.text.toString()
        var isError = false

        //Reset errors
        binding.textInputLayoutEmail.error = null
        binding.textInputLayoutPassword.error = null

        if (binding.etEmail.text.toString() == "") {
            binding.textInputLayoutEmail.error = "This is required field"
            isError = true
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.textInputLayoutEmail.error = "Check Email Format"
            isError = true
        }


        if (binding.etPassword.text.toString() == "") {
            binding.textInputLayoutPassword.error = "This is a required field"
            binding.textInputLayoutPassword.errorIconDrawable = null
            isError = true
        }

        if (isError) return false
        return true
    }
}