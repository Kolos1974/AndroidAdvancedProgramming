package com.example.webunihw002

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.webunihw002.databinding.ActivityPinlogBinding
import com.goodiebag.pinview.Pinview

class PinLogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPinlogBinding
    private val CODE = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinlogBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.pinview.setPinViewEventListener(object : Pinview.PinViewEventListener {
            override fun onDataEntered(pinview: Pinview?, fromUser: Boolean) {

                if (pinview!!.value.equals(CODE)) {
                    val intent = Intent(this@PinLogActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else  Toast.makeText(this@PinLogActivity, pinview.value + " helytelen kód", Toast.LENGTH_SHORT).show()
            }
        })


    }//ONCREATE


}