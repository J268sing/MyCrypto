package com.example.mycrypto.ui.utilities


import android.app.Application
import android.content.Context
import com.example.mycrypto.data.AppDatabase
import com.example.mycrypto.data.CryptocurrencyRepository
import com.example.mycrypto.ui.cryptocurrency.CryptocurrencyViewModelFactory
//import com.example.mycrypto.ui.cryptocurrency.CryptocurrencyViewModelFactory
//import com.example.mycrypto.ui.cryptocurrency.CryptocurrencyViewModelFactory
import com.example.mycrypto.ui.home.HomeViewModelFactory

/**
 * Static methods used to inject classes needed for various Activities and Fragments.
 */
object InjectorUtils {

    private fun getCryptocurrencyRepository(context: Context): CryptocurrencyRepository {
        return CryptocurrencyRepository.getInstance(
                AppDatabase.getInstance(context).cryptocurrencyDao())
    }

    fun provideHomeViewModelFactory(
            application: Application
    ): HomeViewModelFactory {
        val repository = getCryptocurrencyRepository(application)
        return HomeViewModelFactory(application, repository)
    }


    fun provideCryptocurrencyViewModelFactory(
            application: Application
    ): CryptocurrencyViewModelFactory {
        val repository = getCryptocurrencyRepository(application)
        return CryptocurrencyViewModelFactory(application,repository)
    }
}