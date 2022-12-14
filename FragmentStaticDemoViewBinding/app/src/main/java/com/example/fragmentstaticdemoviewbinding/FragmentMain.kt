package com.example.fragmentstaticdemoviewbinding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fragmentstaticdemoviewbinding.databinding.FragmentMainBinding
import java.util.*

class FragmentMain : Fragment() {

    private var _binding: FragmentMainBinding? = null

    private val binding
        // Ez a binding property getter metódusa!
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Ez a régebbi beállítás volt:
        // val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        // return rootView
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnShow.setOnClickListener {
            binding.tvData.text = Date(System.currentTimeMillis()).toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}