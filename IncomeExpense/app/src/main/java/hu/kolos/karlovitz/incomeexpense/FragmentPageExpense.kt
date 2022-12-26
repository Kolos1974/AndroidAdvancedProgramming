package hu.kolos.karlovitz.incomeexpense

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hu.kolos.karlovitz.incomeexpense.data.AppDatabase
import hu.kolos.karlovitz.incomeexpense.data.InOut
import hu.kolos.karlovitz.incomeexpense.databinding.FragmentPageExpenseBinding
import hu.kolos.karlovitz.incomeexpense.databinding.FragmentPageIncomeBinding

class FragmentPageExpense : Fragment() {

    private var _binding : FragmentPageExpenseBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment_page_expense, container, false)
        _binding = FragmentPageExpenseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.buttonExpense?.setOnClickListener{

            val sumOfIncome = InOut(null, "OUT", _binding!!.edittextOut.text.toString().toInt())
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
            FragmentPageExpense()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}