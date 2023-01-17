package com.bale.Estudent.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.bale.Estudent.R
import com.bale.Estudent.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkUser()
        auth = Firebase.auth
        binding.login.setOnClickListener {
            signInUser()
        }

        binding.registerUser.setOnClickListener {
            val intent = Intent(this,CreateAccount::class.java)
            startActivity(intent)
        }
    }

    private fun checkUser() {
        val accountPreference = getSharedPreferences(AccountInfo.PREFERENCE_FILE_NAME, MODE_PRIVATE)

        if(accountPreference.getString(AccountInfo.STUDENT_NAME,"").isNullOrEmpty()){
            binding.registerUser.visibility = View.VISIBLE
        }
        else {
            binding.registerUser.visibility = View.GONE
        }

    }

    private fun signInUser() {
        val email = binding.StudentMail.text.toString()
        val pwd = binding.studentPass.text.toString()
        if(email.isEmpty() ){
            binding.email.setError("Email required")
        }
        else if ( pwd.isEmpty()){
            binding.idinputlayout.setError("Password required")

        }
        else {
            binding.progresss.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    binding.progresss.visibility = View.GONE

                    val intent = Intent(this,RegisteredUNits::class.java)
                    startActivity(intent)

                }

                else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }


        }
    }
}