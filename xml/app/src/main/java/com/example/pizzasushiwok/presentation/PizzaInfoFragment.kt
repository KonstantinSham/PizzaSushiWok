package com.example.pizzasushiwok.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pizzasushiwok.R
import com.example.pizzasushiwok.databinding.FragmentPizzaInfoBinding


class PizzaInfoFragment : Fragment() {
    private lateinit var binding: FragmentPizzaInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPizzaInfoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backFromPizzaInfoBtn.setOnClickListener {
            findNavController().navigate(R.id.action_pizzaInfoFragment_to_sizeFragment)
        }
    }
}