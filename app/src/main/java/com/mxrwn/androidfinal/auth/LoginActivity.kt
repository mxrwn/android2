package com.mxrwn.androidfinal.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.mxrwn.androidfinal.MainActivity
import com.mxrwn.androidfinal.R
import com.mxrwn.androidfinal.databinding.ActivityLoginBinding
import com.mxrwn.androidfinal.databinding.ActivityLoginBinding.inflate

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding;
    private lateinit var firebaseAuth: FirebaseAuth;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("teest")
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.login.setOnClickListener{
            val email = binding.editTextTextEmailAddress3.text.toString()
            val password = binding.editTextTextPassword3.text.toString()
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if(it.isSuccessful){
                    var intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent);
                }else{
                    Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
        binding.textView3.setOnClickListener{
            Log.d("LOGIN","test")
            val intent = Intent(this, RegisterActivity::class.java)
            this.startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            this.startActivity(intent)
        }
    }
}