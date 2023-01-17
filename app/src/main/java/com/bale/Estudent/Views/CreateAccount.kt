package com.bale.Estudent.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bale.Estudent.R
import com.bale.Estudent.databinding.ActivityCreateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateAccount : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private  lateinit var binding: ActivityCreateAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        binding.create.setOnClickListener {
            createNewUser()
        }

    }

    private fun createNewUser() {
        val userEmail = binding.StudentMail.text.toString()
        val pwd = binding.studentPass.text.toString()
        auth.createUserWithEmailAndPassword(userEmail,pwd).addOnCompleteListener { task ->
            if (task.isSuccessful){
                val intent = Intent(this,AccountInfo::class.java)
                startActivity(intent)
                Toast.makeText(this,"User succesfully created",Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
            }
        }

    }
}