package com.android.picmosaic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)

        //if the edit texts are empty when you click the login button, it will display an error message
        loginButton.setOnClickListener{
            val username = emailEditText.text
            val password = passwordEditText.text

            if(username.isNullOrBlank() && password.isNullOrBlank()){
                Toast.makeText(this, "Username and Password cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else if(username.isNullOrBlank()){
                Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }else{
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            //this line will not be executed if the username or/and password will be blank

            //if its false
            //You validate the username and password according to you set username and password
            //This is from the server data
            //Or you can have it static

            val intent = Intent(this, DummyHomeActivity::class.java)
            startActivity(intent)


        }
//        linking sign_up id
        val signupButton = findViewById<Button>(R.id.signupButton)
        signupButton.setOnClickListener{
            Log.e("CSIT284", "PicMosaic")
            Toast.makeText(this, "Opening register page", Toast.LENGTH_LONG).show()

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}
