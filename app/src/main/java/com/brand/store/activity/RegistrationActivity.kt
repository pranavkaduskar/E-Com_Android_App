package com.brand.store.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.brand.store.R
import com.brand.store.data.DatabaseHelper
import com.brand.store.data.User

class RegistrationActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        databaseHelper = DatabaseHelper(this)

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val mobileEditText = findViewById<EditText>(R.id.mobileEditText)
        val dobEditText = findViewById<EditText>(R.id.dobEditText)
        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val registerButton = findViewById<Button>(R.id.registerButton)

        registerButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val mobile = mobileEditText.text.toString()
            val dob = dobEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (username.isNotEmpty() && mobile.isNotEmpty() && dob.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                val user = User(username = username, mobile = mobile, dob = dob, email = email, password = password)
                val userId = databaseHelper.insertUser(user)
                if (userId > -1) {
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
