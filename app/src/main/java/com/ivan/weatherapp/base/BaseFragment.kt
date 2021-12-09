package com.ivan.weatherapp.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices

abstract class BaseFragment: Fragment() {

    protected open fun setupListeners() {}
    protected open fun initViewModels() {}
    protected open fun subscribeToViewModels() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModels()
        subscribeToViewModels()
        setupListeners()
    }
}