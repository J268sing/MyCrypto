package com.example.mycrypto.data


import androidx.lifecycle.LiveData
import android.content.ContentValues
import android.util.Log

/**
 * The class for managing multiple data sources.
 */
class CryptocurrencyRepository private constructor(
        private val cryptocurrencyDao: CryptocurrencyDao
) {

    fun getMyCryptocurrencyLiveDataList(): LiveData<List<CryptocurrencyItemHome>> {
        return cryptocurrencyDao.getMyCryptocurrencyLiveDataList()
    }

    fun getAllCryptocurrencyLiveDataList(): LiveData<List<CryptocurrencyItemHome>> {
        return cryptocurrencyDao.getMyCryptocurrencyLiveDataList()
    }

    fun getSpecificCryptocurrencyLiveData(specificCryptoCode: String): LiveData<CryptocurrencyItemHome> {
        return cryptocurrencyDao.getSpecificCryptocurrencyLiveData(specificCryptoCode)
    }


    fun getSymbolCryptocurrencyColumn(): LiveData<List<String>> {
        return cryptocurrencyDao.getSymbolCryptocurrencyColumn()
    }

    companion object {
        // Marks the JVM backing field of the annotated property as volatile, meaning that writes to this field are immediately made visible to other threads.
        @Volatile
        private var instance: CryptocurrencyRepository? = null

        // For Singleton instantiation.
        fun getInstance(cryptocurrencyDao: CryptocurrencyDao) =
                instance ?: synchronized(this) {
                    instance
                            ?: CryptocurrencyRepository(cryptocurrencyDao).also { instance = it }
                }
    }
}