package com.example.mycrypto.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mycrypto.data.CryptocurrencyRepository


class HomeViewModelFactory(private val application: Application, private val repository: CryptocurrencyRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(application, repository) as T
    }
}