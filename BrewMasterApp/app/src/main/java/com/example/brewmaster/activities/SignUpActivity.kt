package com.example.brewmaster.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import com.example.brewmaster.R
import com.example.brewmaster.database.FirestoreDataSource
import com.example.brewmaster.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private val preferenceName = "myPreferences"
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            if(checkAllField()){
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    //if successfull
                    if(it.isSuccessful){
                        Toast.makeText(this,"Account created successfully",Toast.LENGTH_SHORT).show()

                        val sharedPref: SharedPreferences = this.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)
                        val editor = sharedPref.edit()

                        editor.putString("USER", email)
                        editor.apply()

                        //Create User and add Default CoffeeRecipes
                        val parentJob = Job()
                        val scope = CoroutineScope(Dispatchers.IO + parentJob)
                        val fireStoreDB = FirestoreDataSource()
                        scope.launch {
                            it.result.user?.let { it1 -> fireStoreDB.addUser(it1.uid,email) }
                        }
                        startActivity(Intent(this, SignInActivity::class.java))
                        finish()
                    }else{
                        val errorCode = it.exception?.message.toString()
                        Toast.makeText(this, errorCode,Toast.LENGTH_SHORT).show()
                        Log.e("error: ",it.exception.toString())
                    }
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
        super.onBackPressed()
    }

    private fun checkAllField():Boolean {
        val email = binding.etEmail.text.toString()
        var isError = false

        //Reset errors
        binding.textInputLayoutEmail.error = null
        binding.textInputLayoutPassword.error = null
        binding.textInputLayoutConfirmPassword.error = null

        if(binding.etEmail.text.toString() == ""){
            binding.textInputLayoutEmail.error = "This is required field"
            isError = true
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.textInputLayoutEmail.error = "Check Email Format"
            isError = true
        }


        if(binding.etPassword.text.toString() == ""){
            binding.textInputLayoutPassword.error = "This is a required field"
            binding.textInputLayoutPassword.errorIconDrawable = null
            isError = true
        }

        if(binding.etPassword.length() <= 7){
            binding.textInputLayoutPassword.error = "Pass at least 8 characters long"
            binding.textInputLayoutPassword.errorIconDrawable = null
            isError = true
        }
        if(binding.etConfirmPassword.text.toString() == ""){
            binding.textInputLayoutConfirmPassword.error = "This is a required field"
            binding.textInputLayoutPassword.errorIconDrawable = null
            isError = true
        }

        if(binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString()){
            binding.textInputLayoutConfirmPassword.error = "Passwords don't match"
            isError = true
        }

        if (isError)return false
        return true
    }
}