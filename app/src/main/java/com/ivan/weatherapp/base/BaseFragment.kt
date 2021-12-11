package com.ivan.weatherapp.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

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