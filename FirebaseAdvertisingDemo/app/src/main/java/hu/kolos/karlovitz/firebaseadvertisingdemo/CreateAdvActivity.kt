package hu.kolos.karlovitz.firebaseadvertisingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import hu.kolos.karlovitz.firebaseadvertisingdemo.data.Adv
import hu.kolos.karlovitz.firebaseadvertisingdemo.databinding.ActivityCreateAdvBinding

class CreateAdvActivity : AppCompatActivity() {

    companion object {
        final const val COLLECTION_ADVS = "advs"
        // final const val PERMISSION_REQUEST_CODE = 1001
        // final const val CAMERA_REQUEST_CODE = 1002
    }

    lateinit var binding: ActivityCreateAdvBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_create_adv)

        binding = ActivityCreateAdvBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun sendClick(v: View) {
        try {
            uploadAdv()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun uploadAdv(imageUrl: String = "") {
        val adv = Adv(
            FirebaseAuth.getInstance().currentUser!!.uid,
            FirebaseAuth.getInstance().currentUser!!.email!!,
            binding.etTitle.text.toString(),
            binding.etText.text.toString(),
            binding.etPrice.text.toString().toInt(),
            binding.etEmail.text.toString(),
            binding.etPhone.text.toString(),
            System.currentTimeMillis()
        )

        var advsCollection = FirebaseFirestore.getInstance().collection(
            COLLECTION_ADVS
        )

        advsCollection.add(adv)
            .addOnSuccessListener {
                Toast.makeText(
                    this@CreateAdvActivity,
                    "Post SAVED", Toast.LENGTH_LONG
                ).show()

                finish()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this@CreateAdvActivity,
                    "Error ${it.message}", Toast.LENGTH_LONG
                ).show()
            }
    }
}