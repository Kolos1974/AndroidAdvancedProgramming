package com.example.webunihw002

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.webunihw002.MainActivity
import com.example.webunihw002.R
import com.example.webunihw002.data.AppDatabase
import com.example.webunihw002.data.InOut
import com.example.webunihw002.databinding.FragmentPageTwoBinding

class FragmentPageTwo : Fragment() {

    private var _binding : FragmentPageTwoBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPageTwoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //kiadás hozzáadása
        _binding?.buttonOut?.setOnClickListener{
            val sumOfIn = InOut(null, 0,_binding!!.edittextOut.text.toString().toInt())
            val dbThread = Thread {
                AppDatabase.getInstance(requireContext()).inoutDao().insertGrades(sumOfIn)
            }
            dbThread.start()

            val intent =  Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            FragmentPageTwo()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}