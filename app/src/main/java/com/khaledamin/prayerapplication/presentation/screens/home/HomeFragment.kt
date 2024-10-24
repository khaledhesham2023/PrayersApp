package com.khaledamin.prayerapplication.presentation.screens.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.khaledamin.prayerapplication.R
import com.khaledamin.prayerapplication.databinding.FragmentHomeBinding
import com.khaledamin.prayerapplication.presentation.abstracts.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    override val layout: Int
        get() = R.layout.fragment_home
    override val viewModelClass: Class<HomeViewModel>
        get() = HomeViewModel::class.java

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    override fun setupObservers() {
//        TODO("Not yet implemented")
    }

    private fun setupListeners(){
        viewBinding.showQiblaBtn.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMapsFragment())
        }
    }

}