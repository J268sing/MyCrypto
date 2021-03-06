package com.example.mycrypto.ui.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.example.mycrypto.data.CryptocurrencyItemHome
import com.example.mycrypto.data.CryptocurrencyRepository
import com.example.mycrypto.ui.utilities.roundValue
import com.example.mycrypto.ui.utilities.ValueType
import javax.inject.Inject


class HomeViewModel(application: Application, cryptocurrencyRepository: CryptocurrencyRepository) : ViewModel() {

    private var currentCryptoCurrencyCode: String
    private var currentCryptoCurrencySign: String

    private val liveDataCurrentCryptocurrency: LiveData<CryptocurrencyItemHome>
    val liveDataMyCryptocurrencyList: LiveData<List<CryptocurrencyItemHome>>

    private val liveDataTotalHoldingsValueFiat: LiveData<Double>
    private val liveDataTotalHoldingsValueCrypto: LiveData<Double>
    private val liveDataTotalHoldingsValueFiat24h: LiveData<Double>
    val liveDataTotalHoldingsValueCryptoText: LiveData<String>


    val cryptoSymbolColumn:LiveData<List<String>>
    init {
        currentCryptoCurrencyCode = "JSHN"
        currentCryptoCurrencySign = "$$"
        liveDataMyCryptocurrencyList = cryptocurrencyRepository.getAllCryptocurrencyLiveDataList()
        liveDataCurrentCryptocurrency = cryptocurrencyRepository.getSpecificCryptocurrencyLiveData(currentCryptoCurrencyCode)
        liveDataTotalHoldingsValueFiat24h = Transformations.switchMap(liveDataMyCryptocurrencyList) { _ -> MutableLiveData<Double>().apply { value = liveDataMyCryptocurrencyList.value?.sumByDouble { it.amountFiatChange24h } } }
        liveDataTotalHoldingsValueFiat = Transformations.switchMap(liveDataMyCryptocurrencyList) { _ -> MutableLiveData<Double>().apply { value = liveDataMyCryptocurrencyList.value?.sumByDouble { it.amountFiat } } }
        liveDataTotalHoldingsValueCrypto = countTotalHoldingsValueCrypto(liveDataTotalHoldingsValueFiat, liveDataCurrentCryptocurrency)
        cryptoSymbolColumn = cryptocurrencyRepository.getSymbolCryptocurrencyColumn()
        liveDataTotalHoldingsValueCryptoText = Transformations.switchMap(liveDataTotalHoldingsValueCrypto) { MutableLiveData<String>().apply { value = String.format("$currentCryptoCurrencySign ${roundValue(liveDataTotalHoldingsValueCrypto.value!!, ValueType.Crypto)}") } }
    }

    // When you want to combine multiple sources of LiveData use MediatorLiveData.
    private fun countTotalHoldingsValueCrypto(totalHoldingsValueFiat: LiveData<Double>, currentCryptocurrency: LiveData<CryptocurrencyItemHome>): LiveData<Double> {

        // Nested function to take program modularization further.
        fun combineLatestData(): Double {

            val totalFiat = totalHoldingsValueFiat.value
            val currentCrypto = currentCryptocurrency.value

            // Don't send a success until we have both results.
            if (totalFiat == null || currentCrypto == null) {
                return 0.0
            }
            return totalFiat
        }
        val result = MediatorLiveData<Double>()

        result.addSource(totalHoldingsValueFiat) { value ->
            result.value = combineLatestData()
        }
        result.addSource(currentCryptocurrency) { value ->
            result.value = combineLatestData()
        }
        return result
    }
}