package com.brand.store.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.brand.store.R
import com.brand.store.data.DatabaseHelper

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        databaseHelper = DatabaseHelper(this)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val newPasswordEditText = findViewById<EditText>(R.id.newPasswordEditText)
        val resetPasswordButton = findViewById<Button>(R.id.resetPasswordButton)

        resetPasswordButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()

            if (email.isNotEmpty() && newPassword.isNotEmpty()) {
                val user = databaseHelper.getUserByEmail(email)
                if (user != null) {
                    databaseHelper.updatePassword(email, newPassword)
                    Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Email not found", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter email and new password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
