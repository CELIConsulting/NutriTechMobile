package com.istea.nutritechmobile.ui.interfaces

import com.google.android.material.bottomnavigation.BottomNavigationView

interface IToolbar {
    fun setupBottomNavigationBar(bottomNavigationView: BottomNavigationView)
    fun goToDailyRegistryView()
    fun goToHomeView()
    fun goToProgressView()
    fun goToProfileView()
    fun goToLoginView()
}