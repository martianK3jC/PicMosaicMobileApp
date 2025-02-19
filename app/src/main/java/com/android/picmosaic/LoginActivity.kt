package com.android.picmosaic

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.button.MaterialButton

class LoginActivity : Activity() {
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var loginButton: MaterialButton
    private lateinit var sharedPreferences: SharedPreferences

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)



        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("PicMosaic", MODE_PRIVATE)

        // Check if user is already logged in
        val currentUserEmail = sharedPreferences.getString("current_user_email", null)
        if (currentUserEmail != null) {
            navigateToHome()
            return
        }

        // Handle login button click
        loginButton.setOnClickListener {
            val email = emailEditText.text?.toString()?.trim() ?: ""
            val password = passwordEditText.text?.toString()?.trim() ?: ""

            if (email.isNullOrBlank() && password.isNullOrBlank()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if(password.isNullOrBlank()){
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if(email.isNullOrBlank()){
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validate credentials
            if (DummyUserData.validateCredentials(email, password)) {
                // Save login state
                sharedPreferences.edit()
                    .putString("current_user_email", email)
                    .apply()

                navigateToHome()
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToHome() {
        val intent = Intent(this, DummyHomeActivity::class.java)
        startActivity(intent)
        finish() // Closes login activity
    }
}
