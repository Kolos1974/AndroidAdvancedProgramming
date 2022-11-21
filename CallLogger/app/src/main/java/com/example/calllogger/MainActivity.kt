package com.example.calllogger

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.calllogger.adapter.OutCallAdapter
import com.example.calllogger.data.AppDatabase
import permissions.dispatcher.RuntimePermissions

//@RuntimePermissions
class MainActivity : AppCompatActivity() {
    private lateinit var adapter: OutCallAdapter

    companion object {
        const val REQUEST_CALL_STATE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestNeededPermission()

        initDB()
    }

    private fun initDB() {
        adapter = OutCallAdapter(this)
        findViewById<RecyclerView>(R.id.recyclerCalls).adapter = adapter

        AppDatabase.getInstance(this).outCallDAO().getAllCalls()
            .observe(this, Observer { items ->
                adapter.submitList(items)
            })
    }

    private fun requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.PROCESS_OUTGOING_CALLS),
                101)
        } else {
        }


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Javítani!!
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)) {
                showRationaleDialog(
                    "Engedély kérés",
                    "Szükség van rá, mivel ezt teszteljük",
                    android.Manifest.permission.CALL_PHONE,
                    REQUEST_CALL_STATE
                )
            } else {
                // Javítani!!
                requestPermissions(
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    REQUEST_CALL_STATE
                )
            }
        } else {
            // Permission is already granted
        }
    }

    private fun showRationaleDialog(
        title: String,
        message: String,
        permission: String,
        requestCode: Int
    ) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("Ok") { _, _ ->
                // Javítani!!
                requestPermissions(arrayOf(permission), requestCode)
            }
        builder.create().show()
    }

    /*
    private fun requestPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (shouldShowRequestPermissionRationale(android.Manifest.permission.CALL_PHONE)) {
                showRationaleDialog(
                    "Engedély kérés",
                    "Szükség van rá, mivel ezt teszteljük",
                    android.Manifest.permission.CALL_PHONE,
                    REQUEST_CALL_STATE
                )
            } else {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    REQUEST_CALL_STATE
                )
            }
        } else {
            // Permission is already granted
        }
    }
    */

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            101 -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this@MainActivity, "perm granted", Toast.LENGTH_SHORT).show()


                } else {
                    Toast.makeText(this@MainActivity,
                        "perm not granted", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

}