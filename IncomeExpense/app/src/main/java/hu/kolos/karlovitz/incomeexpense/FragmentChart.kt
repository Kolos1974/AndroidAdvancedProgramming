package hu.kolos.karlovitz.incomeexpense

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import hu.kolos.karlovitz.incomeexpense.data.AppDatabase
import hu.kolos.karlovitz.incomeexpense.databinding.FragmentChartBinding

class FragmentChart : Fragment() {


    private var _binding: FragmentChartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentChartBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnEdit.setOnClickListener {
            binding.root.findNavController().navigate(
                R.id.action_fragmentChart_to_fragmentDetail)

            /*
            binding.root.findNavController().navigate(
                // Ezt az osztályt generáljuk!!
                FragmentMainDirections.actionFragmentMainToFragmentDetail(
                    Person(
                        binding.etName.text.toString(),
                        binding.etAddress.text.toString()
                    )
                ))

             */

        }
    }


    override fun onStart() {
        super.onStart()
        drawPieChart()
    }

    fun drawPieChart() {
        // val inout = InOut(null, 500, 350)
        var sumOfIncome: Int = 0
        var sumOfExpense: Int = 0

        val dbThread = Thread {

//            AppDatabase.getInstance(this@MainActivity).inoutDao().insertGrades(inout)
            sumOfIncome = AppDatabase.getInstance(requireContext()).inoutDao().sumOfInOuts("IN")
            sumOfExpense = AppDatabase.getInstance(requireContext()).inoutDao().sumOfInOuts("OUT")

            Log.e("összegek", sumOfIncome.toString() + " " + sumOfExpense.toString())

            //runOnUiThread {
            activity?.runOnUiThread{
                setPieChart(sumOfExpense,sumOfIncome)
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
        binding.chartBalance.animateXY(1000, 1000)

        val colors = ArrayList<Int>()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            //colors.add(color)
            colors.add(Color.RED)
            colors.add(Color.GREEN)
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
        binding.chartBalance.centerText = ""
        binding.chartBalance.setUsePercentValues(true)
        binding.chartBalance.setCenterTextColor(R.color.black)
        binding.chartBalance.setCenterTextSize(15f)
        binding.chartBalance.setEntryLabelTextSize(8f)

        // Set Pie Data Set in PieData

        val pieData = PieData(pieDataSet)

        pieData.setValueFormatter(PercentFormatter(binding.chartBalance))

        // Now Lets Hide the PieChar Entries Tags
        binding.chartBalance.legend.isEnabled = false

        //Now Hide the description of pieChart
        binding.chartBalance.description.isEnabled = false
        binding.chartBalance.holeRadius = 80f


        //This enabled the on each pieEntry
        pieData.setDrawValues(true)
        binding.chartBalance.data = pieData
    }



}