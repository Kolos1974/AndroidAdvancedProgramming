package hu.kolos.karlovitz.incomeexpense

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.broooapps.otpedittext2.OnCompleteListener
import hu.kolos.karlovitz.incomeexpense.databinding.ActivityPinBinding

class PinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPinBinding

    private val PINCODE = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPinBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        binding.etPIN.setOnCompleteListener(OnCompleteListener { value ->
            Toast.makeText(
                this,
                "Completed $value",
                Toast.LENGTH_SHORT
            ).show()

            if (value.equals(PINCODE)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                binding.etPIN.setText("")
            }
            else
                Toast.makeText(
                    this,
                    "Hibás PIN!! $value",
                    Toast.LENGTH_SHORT
                ).show()

        })

    }
}