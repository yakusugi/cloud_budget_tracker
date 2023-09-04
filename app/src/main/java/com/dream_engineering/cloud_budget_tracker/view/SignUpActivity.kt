package com.dream_engineering.cloud_budget_tracker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.dream_engineering.cloud_budget_tracker.R
import com.dream_engineering.cloud_budget_tracker.dao.UserRegistrationDao
import com.dream_engineering.cloud_budget_tracker.dto.UserDto
import java.io.IOException

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signUpBtn: Button = findViewById(R.id.login_btn)

        signUpBtn.setOnClickListener{
            val userName : String = findViewById<EditText>(R.id.edit_text_sign_up_user_name).text.toString()
            val email : String = findViewById<EditText>(R.id.edit_text_sign_up_user_email).text.toString()
            val password : String = findViewById<EditText>(R.id.edit_text_sign_up_user_pw).text.toString()

            try {
                val userDto = UserDto(userName, password, email)
                val userRegistrationDao = UserRegistrationDao(this)
                userRegistrationDao.insertIntoUser(userDto)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}