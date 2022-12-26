package com.example.webunihw002

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.webunihw002.MainActivity
import com.example.webunihw002.data.AppDatabase
import com.example.webunihw002.data.InOut
import com.example.webunihw002.databinding.FragmentPageOneBinding

class FragmentPageOne : Fragment() {

    private var _binding : FragmentPageOneBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPageOneBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding?.buttonIn?.setOnClickListener{

            val sumOfIn = InOut(null, _binding!!.edittextIn.text.toString().toInt(),0)
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
            FragmentPageOne()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}