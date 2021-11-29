package com.ivan.weatherapp.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.ivan.weatherapp.base.BaseFragment
import com.ivan.weatherapp.databinding.FragmentMainBinding
import com.ivan.weatherapp.viewmodel.WeatherUIState
import com.ivan.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun setupListeners() {
        with(binding.searchView) {
            editText.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    // Open keyboard
                    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(
                        editText,
                        InputMethodManager.SHOW_FORCED
                    )
                } else {
                    // Close keyboard
                    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                        editText.windowToken,
                        0
                    )
                }
            }
            searchButton.setOnClickListener {
                weatherViewModel.fetchWeatherByCity(editText.text.toString())
                editText.clearFocus()
            }
        }
    }

    override fun initViewModels() {
        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun subscribeToViewModels() {
        lifecycleScope.launchWhenStarted {
            weatherViewModel.uiStateFlow.collect {
                when(it){
                    is WeatherUIState.NewState -> {
                        // show new state
                    }
                    is WeatherUIState.Loading -> {
                        Snackbar.make(binding.root, LOADING_MSG, Snackbar.LENGTH_SHORT).show()
                    }
                    is WeatherUIState.Success -> {
                        // show success it.weather
                        binding.weatherView.weather = it.weather
                        if(it.weather.resourceId != null)
                            binding.weatherView.illustration.setImageResource(it.weather.resourceId);
                    }
                    is WeatherUIState.Failure -> {
                        Snackbar.make(binding.root, ERROR_MSG, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

const val ERROR_MSG = "Произошла ошибка"
const val LOADING_MSG = "Началась загрузка"