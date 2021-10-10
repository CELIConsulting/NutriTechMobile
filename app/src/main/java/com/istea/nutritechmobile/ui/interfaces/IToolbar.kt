package com.istea.nutritechmobile.ui.interfaces

import com.google.android.material.bottomnavigation.BottomNavigationView

interface IToolbar {
    fun setupBottomNavigationBar(bottomNavigationView: BottomNavigationView)
    fun goToDailyRegistryView()
    fun goToRecipesView()
    fun goToProgressView()
    fun goToProfileView()
}