package com.ivan.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ivan.domain.usecase.WeatherUseCase
import com.ivan.weatherapp.R
import com.ivan.weatherapp.viewmodel.WeatherUIState
import com.ivan.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initNavController()
//        val viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
//        lifecycleScope.launch {
//            viewModel.uiStateFlow.collect {
//                when(it){
//                    is WeatherUIState.NewState -> {
//                        // show new state
//                    }
//                    is WeatherUIState.Loading -> {
//                        // show loading
//                    }
//                    is WeatherUIState.Success -> {
//                        // show success it.weather
//                    }
//                    is WeatherUIState.Failure ->{
//                        // show fail
//                    }
//                }
//            }
        //}
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        navController = navHostFragment.navController
    }
}