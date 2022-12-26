package com.example.webunihw002

import android.content.Intent
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.webunihw002.data.AppDatabase
import com.example.webunihw002.data.InOut
import com.example.webunihw002.databinding.ActivityMainBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate



class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonGotoIn.setOnClickListener() {
            val intent = Intent(this, AddNewAmountActivity::class.java)
            startActivity(intent)
        }

        binding.buttonGotoOut.setOnClickListener() {
            val intent = Intent(this, AddNewAmountActivity::class.java)
            intent.putExtra("PAGE", 1)
            startActivity(intent)
        }

    }//ONCREATE


    override fun onStart() {
        super.onStart()
        drawPieChart()
    }

    fun drawPieChart() {
        // val inout = InOut(null, 500, 350)
        var sumOfIn: Int = 0
        var sumOfOut: Int = 0

        val dbThread = Thread {

//            AppDatabase.getInstance(this@MainActivity).inoutDao().insertGrades(inout)
            sumOfIn = AppDatabase.getInstance(this@MainActivity).inoutDao().sumOfIncomes()
            sumOfOut = AppDatabase.getInstance(this@MainActivity).inoutDao().sumOfExpenses()

            Log.e("összegek", sumOfIn.toString() + " " + sumOfOut.toString())

            runOnUiThread {
                setPieChart(sumOfOut,sumOfIn)
            }
        }
        dbThread.start()
    }

    private fun setPieChart(sumIn: Int, sumOut: Int) {
        //Setup pie Entries
        val pieEntries = arrayListOf<PieEntry>()
        pieEntries.add(PieEntry(sumIn.toFloat(), ""))
        pieEntries.add(PieEntry(sumOut.toFloat(), ""))

        //Setup Pie Chart Animation
        binding.pieChart.animateXY(1000, 1000)

        val colors = ArrayList<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            //colors.add(color)
            colors.add(RED)
            colors.add(GREEN)
        }

//        for (color in ColorTemplate.VORDIPLOM_COLORS) {
//            colors.add(color)
//        }

        //setup value format

        //Setup Piechart Entries Colors
        val pieDataSet = PieDataSet(pieEntries, "Insiders")
        pieDataSet.valueTextSize = 20f
        pieDataSet.valueTextColor = R.color.black
        pieDataSet.colors = colors

        //Now setup text in PieChart Center
        binding.pieChart.centerText = ""
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.setCenterTextColor(R.color.black)
        binding.pieChart.setCenterTextSize(15f)
        binding.pieChart.setEntryLabelTextSize(8f)

        // Set Pie Data Set in PieData

        val pieData = PieData(pieDataSet)

        pieData.setValueFormatter(PercentFormatter(binding.pieChart))

        // Now Lets Hide the PieChar Entries Tags
        binding.pieChart.legend.isEnabled = false

        //Now Hide the description of pieChart
        binding.pieChart.description.isEnabled = false
        binding.pieChart.holeRadius = 80f


        //This enabled the on each pieEntry
        pieData.setDrawValues(true)
        binding.pieChart.data = pieData
    }

}