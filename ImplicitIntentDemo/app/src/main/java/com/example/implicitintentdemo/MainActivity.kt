package com.example.implicitintentdemo

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.implicitintentdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        binding.btnIntentSearch.setOnClickListener {
            intentSearch()
        }
        */

        val b = intent.extras
        if (b != null && b.containsKey(Intent.EXTRA_STREAM)) {
            val uri = b[Intent.EXTRA_STREAM] as Uri
            binding.ivData.setImageURI(uri)
        }

    }

    fun intentSearch(v: View) {
        val intentSearch = Intent(Intent.ACTION_WEB_SEARCH)
        intentSearch.putExtra(SearchManager.QUERY, "Balaton")
        startActivity(intentSearch)
    }

    fun intentDial(v: View) {
        // val intentDial = Intent(Intent.ACTION_CALL, Uri.parse("tel:+36123456789"))
        val intentDial = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+36123456789"))
        startActivity(intentDial)
    }

    fun intentSend(v: View) {
        val intentSend = Intent(Intent.ACTION_SEND)
        intentSend.type = "text/plain"
        //intentSend.`package` = "com.facebook.katana"
        intentSend.putExtra(Intent.EXTRA_TEXT, "Jee  Tanfolyam!")
        startActivity(intentSend)

        // Mingig felhozza az alkalmazás kiválasztó listát, akkor is, ha korábban már hozzárendeltük!
        // startActivity(Intent.createChooser(intentSend, "Select share app"));
    }

    fun intentWaze(v: View) {
        //String wazeUri = "waze://?favorite=Home&navigate=yes";
        //val wazeUri = "waze://?ll=40.761043, -73.980545&navigate=yes"
        val wazeUri = "waze://?q=BME&navigate=yes"

        val intentTest = Intent(Intent.ACTION_VIEW)
        intentTest.data = Uri.parse(wazeUri)
        startActivity(intentTest)
    }

    fun intentStreetView(v: View) {
        val gmmIntentUri = Uri.parse(
            "google.streetview:cbll=29.9774614,31.1329645&cbp=0,30,0,0,-15"
        )
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }
}