package com.innocyber.instachat.SignIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.innocyber.instachat.MainActivity
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

    override fun onStart() {
        super.onStart()

        if(FirebaseAuth.getInstance().currentUser!=null){
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}
