package com.example.firebaseauth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)

        mAuth = FirebaseAuth.getInstance()

        tv_login.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btn_signUp.setOnClickListener {
            registerUser()
        }
    }

    fun registerUser(){
        val email = findViewById<EditText>(R.id.et_email).text.toString()
        val password = findViewById<EditText>(R.id.et_password).text.toString()
        println("$email and $password")

        if (email.isEmpty()){
            et_email.error = "Email is required"
            et_email.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.error = "Plz enter a valid email address"
            et_email.requestFocus()
            return
        }

        if (password.isEmpty()){
            et_password.error = "Password is required"
            et_password.requestFocus()
            return
        }

        if (password.length < 6){
            et_password.error = "Minimum length of password should be six"
            et_password.requestFocus()
            return
        }
        progressbar.visibility = View.VISIBLE

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnFailureListener{exception -> println("Exception"+exception.message) }
            .addOnCompleteListener { task ->
                progressbar.visibility = View.GONE
                println("isCompleted")
                if (task.isSuccessful){
                    println("isSuccessful")
                    Toast.makeText(this, "Registration is successful", Toast.LENGTH_SHORT).show()
                }else{
                    println("isFailed")
                    println(task.exception)
                    Toast.makeText(this, "${task.exception}",Toast.LENGTH_SHORT).show()
                }
            }
    }
}
