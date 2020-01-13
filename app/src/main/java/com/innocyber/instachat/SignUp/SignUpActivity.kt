package com.innocyber.instachat.SignUp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.innocyber.instachat.MainActivity
import com.innocyber.instachat.R
import com.innocyber.instachat.SignIn.SignInActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signin_button.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }

        signup_button.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        val fullName = full_name_signup.text.toString()
        val userName = username_signup.text.toString()
        val email = email_signup.text.toString()
        val password = password_signup.text.toString()

        when{
            TextUtils.isEmpty(fullName) -> Toast.makeText(this,"Fullname cannot be empty",Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(userName) -> Toast.makeText(this," Username cannot be empty",Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(password) -> Toast.makeText(this,"Password cannot be empty",Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this,"Email cannot be empty",Toast.LENGTH_SHORT).show()
        else -> {

            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("SignUp")
            progressDialog.setMessage("Registering user...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
            mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task ->
                    if (task.isSuccessful){
                        saveUserInfo(fullName,userName,email,progressDialog)
                    }else{
                        val errorMessage = task.exception!!.toString()
                        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
                        mAuth.signOut()
                        progressDialog.dismiss()
                    }
                }
        }
    } }

    private fun saveUserInfo(fullName: String, userName: String, email: String, progressDialog: ProgressDialog) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String,Any>()
        userMap["uid"] = currentUserID
        userMap["fullname"] = currentUserID
        userMap["username"] = currentUserID
        userMap["email"] = currentUserID
        userMap["bio"] = "Hey i'm using innocents instagram clone app"
        userMap ["image"] = "https://firebasestorage.googleapis.com/v0/b/instachat-564a7.appspot.com/o/Default%20images%2Fprofile.png?alt=media&token=a003aa76-2aaa-4811-8fb6-6931d6fcd90e"

        userRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    progressDialog.dismiss()
                    Toast.makeText(this,"Account created successfully",Toast.LENGTH_SHORT).show()

                    val intent = Intent(this,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }else{
                    val errorMessage = task.exception!!.toString()
                    Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }
    }


}
