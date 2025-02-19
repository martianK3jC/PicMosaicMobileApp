package com.android.picmosaic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

//        val emailEditText = findViewById<EditText>(R.id.registerEmailEditText)
//        val passwordEditText = findViewById<EditText>(R.id.registerPasswordEditText)
//        val confirmPasswordEditText = findViewById<EditText>(R.id.confirmPasswordEditText)
//        val registerButton = findViewById<Button>(R.id.registerButton)
          val loginButton = findViewById<Button>(R.id.registerPageloginButton)

//        registerButton.setOnClickListener {
//            val email = emailEditText.text.toString()
//            val password = passwordEditText.text.toString()
//            val confirmPassword = confirmPasswordEditText.text.toString()
//
//            when {
//                email.isBlank() || password.isBlank() || confirmPassword.isBlank() -> {
//                    Toast.makeText(this, "All fields are required", Toast.LENGTH_LONG).show()
//                }
//                !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
//                    Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_LONG).show()
//                }
//                password.length < 6 -> {
//                    Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_LONG).show()
//                }
//                password != confirmPassword -> {
//                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show()
//                }
//                else -> {
//                    // TODO: Add actual registration logic here
//                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(this, LoginActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//            }
//        }

        loginButton.setOnClickListener{
            Log.e("CSIT284", "PicMosaic")
            Toast.makeText(this, "Opening login page", Toast.LENGTH_LONG).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}