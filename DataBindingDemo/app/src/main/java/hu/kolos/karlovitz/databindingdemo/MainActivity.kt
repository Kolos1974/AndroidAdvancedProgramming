package hu.kolos.karlovitz.databindingdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import hu.kolos.karlovitz.databindingdemo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // binding.demoString = "Hello Android Data Binding"

        // Csak akkor működik, ha az elem nincs összekötve a viewbinding-el!!
        // binding.tvData.text = "Hello "

        binding.car = Car("Volvo", "2020.11.12")

        var rand = Random()
        binding.btnDemo.setOnClickListener {
            Toast.makeText(this, "${binding.cityName}", Toast.LENGTH_LONG).show()

            binding.imageUrl = "https://picsum.photos/200/200/?image=${rand.nextInt(1000)}"
        }

    }
}

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    if (url != null && url.isNotBlank()) {
        Glide.with(this.context)
            .load(url)
            .into(this)
    }
}