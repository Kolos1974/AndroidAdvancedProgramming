package com.example.firebaseforumdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.firebaseforumdemo.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // A push üzenetek észleléséhez fel kell iratkozni a topic-ra!!
        FirebaseMessaging.getInstance().subscribeToTopic("forumpushes")
    }


    fun loginClick(v: View){
        // Hibát generáltunk a Crashlytics-hez
        //throw RuntimeException("Ez elszállt!!")

        if (!isFormValid()) {
            return
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            binding.etEmail.text.toString(), binding.etPassword.text.toString()
        ).addOnSuccessListener {
            startActivity(Intent(this@MainActivity, ForumActivity::class.java))
        }.addOnFailureListener{
            Toast.makeText(
                this@MainActivity,
                "Login error: ${it.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun registerClick(v: View){
        if (!isFormValid()){
            return
        }

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            binding.etEmail.text.toString(), binding.etPassword.text.toString()
        ).addOnSuccessListener {
            Toast.makeText(
                this@MainActivity,
                "Registration OK",
                Toast.LENGTH_LONG
            ).show()
        }.addOnFailureListener{
            Toast.makeText(
                this@MainActivity,
                "Error: ${it.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }



    fun isFormValid(): Boolean {
        return when {
            binding.etEmail.text.isEmpty() -> {
                binding.etEmail.error = "This field can not be empty"
                false
            }
            binding.etPassword.text.isEmpty() -> {
                binding.etPassword.error = "The password can not be empty"
                false
            }
            else -> true
        }
    }
}