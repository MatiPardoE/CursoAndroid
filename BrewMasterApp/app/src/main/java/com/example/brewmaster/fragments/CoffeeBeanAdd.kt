package com.example.brewmaster.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.brewmaster.R

class CoffeeBeanAdd : Fragment() {

    private lateinit var viewModel: CoffeeBeanAddViewModel
    private lateinit var v: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_coffee_bean_add, container, false)

        return v
    }

}