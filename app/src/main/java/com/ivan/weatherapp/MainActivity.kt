package com.ivan.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint

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