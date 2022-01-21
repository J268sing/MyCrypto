package com.example.mycrypto.ui.cryptocurrency

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mycrypto.data.CryptocurrencyRepository


/**
 * Factory for creating a [AddSearchViewModel] with a constructor that takes a
 * [CryptocurrencyRepository].
 */


class CryptocurrencyViewModelFactory(private val application: Application, private val repository: CryptocurrencyRepository) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CryptocurrencyViewModel(application,repository) as T
    }
}