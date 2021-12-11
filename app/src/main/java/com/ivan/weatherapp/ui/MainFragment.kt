package com.ivan.weatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.ivan.weatherapp.base.BaseFragment
import com.ivan.weatherapp.databinding.FragmentMainBinding
import com.ivan.weatherapp.viewmodel.HourlyWeatherUIState
import com.ivan.weatherapp.viewmodel.HourlyWeatherViewModel
import com.ivan.weatherapp.viewmodel.WeatherUIState
import com.ivan.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private lateinit var hourlyWeatherViewModel: HourlyWeatherViewModel
    private lateinit var weatherViewModel: WeatherViewModel

    @Inject
    lateinit var adapter: HourlyWeatherAdapter

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("MissingPermission")
    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val fusedLocationProvider = LocationServices.getFusedLocationProviderClient(context)
                val lastKnownLocation = fusedLocationProvider.lastLocation
                lastKnownLocation.addOnSuccessListener {
                    weatherViewModel.fetchWeatherByLocation(it.latitude, it.longitude)
                    hourlyWeatherViewModel.fetchHourlyWeatherByLocation(it.latitude, it.longitude)
                }
            } else {
                Snackbar.make(binding.root, NO_PERMISSION, Snackbar.LENGTH_SHORT).show()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.recycleView.adapter = adapter
        return binding.root
    }

    override fun setupListeners() {
        with(binding.searchView) {
            editText.setOnFocusChangeListener { _, hasFocus ->
                val inputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                if (hasFocus) {
                    inputMethodManager.showSoftInput(
                        editText,
                        InputMethodManager.SHOW_FORCED
                    )
                } else {
                    inputMethodManager.hideSoftInputFromWindow(
                        editText.windowToken,
                        0
                    )
                }
            }

            searchButton.setOnClickListener {
                weatherViewModel.fetchWeatherByCity(editText.text.toString())
                editText.clearFocus()
            }

            searchByLocationButton.setOnClickListener {
                activity?.let {
                    requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                }
            }
        }
    }

    override fun initViewModels() {
        weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        hourlyWeatherViewModel = ViewModelProvider(this)[HourlyWeatherViewModel::class.java]
    }

    override fun subscribeToViewModels() {
        lifecycleScope.launchWhenStarted {
            weatherViewModel.uiStateFlow.collect {
                Log.d("Tagging", "old collect: ")
                when (it) {
                    is WeatherUIState.NewState -> {
                        // show new state
                    }
                    is WeatherUIState.Loading -> {
                        Snackbar.make(binding.root, LOADING_MSG, Snackbar.LENGTH_SHORT).show()
                    }
                    is WeatherUIState.Success -> {
                        // show success it.weather
                        binding.weatherView.weather = it.weather
                        if (it.weather.resourceId != null)
                            binding.weatherView.illustration.setImageResource(it.weather.resourceId);
                    }
                    is WeatherUIState.Failure -> {
                        Snackbar.make(binding.root, ERROR_MSG, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            hourlyWeatherViewModel.uiStateFlow.collect {
                when (it) {
                    is HourlyWeatherUIState.NewState -> {
                        binding.recycleView.visibility = View.GONE
                    }
                    is HourlyWeatherUIState.Loading -> {
                        Snackbar.make(binding.root, LOADING_MSG, Snackbar.LENGTH_SHORT).show()
                    }
                    is HourlyWeatherUIState.Success -> {
                        binding.recycleView.visibility = View.VISIBLE
                        adapter.items = it.weather

                    }
                    is HourlyWeatherUIState.Failure -> {
                        Snackbar.make(binding.root, ERROR_MSG, Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

const val ERROR_MSG = "Произошла ошибка"
const val LOADING_MSG = "Началась загрузка"
const val NO_PERMISSION = "Нет разрешения на установку локации"