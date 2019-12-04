package com.example.firebaseauth

import android.app.ActivityManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        window.statusBarColor = resources.getColor(R.color.colorPrimaryDark)

        tv_sign_up.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        btn_login.setOnClickListener {
            btn_login.startAnimation()
            val email = findViewById<EditText>(R.id.et_email).text.toString()
            val password = findViewById<EditText>(R.id.et_password).text.toString()
            println("email & password -->> $email $password")
            loginUser(email, password)
        }
    }
    fun loginUser(email: String, password: String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                println("isCompleted")
                btn_login.revertAnimation {
                    if (task.isSuccessful){
                        btn_login.background = resources.getDrawable(R.drawable.button_bg)
                        btn_login.text = "Successful"
                        println("isSuccessful")
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, SaveUserInformation::class.java))
                    }else{
                        btn_login.background = resources.getDrawable(R.drawable.button_bg)
                        btn_login.text = "Try Again"
                        println("isFailed")
                        Toast.makeText(this, "${task.exception}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
