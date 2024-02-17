package com.example.pizzasushiwok.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.pizzasushiwok.R
import com.example.pizzasushiwok.databinding.FragmentCategoryBinding


class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCategoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pizzaNavBtn.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_sizeFragment)
        }
        binding.backFromCategoryBtn.setOnClickListener {
            findNavController().navigate(R.id.action_categoryFragment_to_mapFragment)
        }
    }
}