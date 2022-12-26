package hu.kolos.karlovitz.incomeexpense

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.kolos.karlovitz.incomeexpense.data.AppDatabase
import hu.kolos.karlovitz.incomeexpense.data.InOut
import hu.kolos.karlovitz.incomeexpense.databinding.FragmentPageIncomeBinding


class FragmentPageIncome : Fragment() {

    private var _binding : FragmentPageIncomeBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment_page_income, container, false)
        _binding = FragmentPageIncomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.buttonIncome?.setOnClickListener{

            val sumOfIncome = InOut(null, "IN", _binding!!.edittextIn.text.toString().toInt())
            val dbThread = Thread {
                AppDatabase.getInstance(requireContext()).inoutDao().insertInOuts(sumOfIncome)
            }
            dbThread.start()

            val intent =  Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FragmentPageIncome()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}