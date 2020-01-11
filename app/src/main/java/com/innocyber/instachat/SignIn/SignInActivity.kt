package com.innocyber.instachat.SignIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.innocyber.instachat.R
import com.innocyber.instachat.SignUp.SignUpActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signup_button.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
}