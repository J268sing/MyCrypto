package com.example.mycrypto.ui.cryptocurrency

import android.app.Application
import androidx.lifecycle.*
import com.example.mycrypto.data.CryptocurrencyItemHome
import com.example.mycrypto.data.CryptocurrencyRepository
import com.example.mycrypto.ui.utilities.roundValue
import javax.inject.Inject


class CryptocurrencyViewModel (application: Application,cryptocurrencyRepository: CryptocurrencyRepository) : ViewModel() {
   /* private var currentCryptoCurrencyCode: String
    private var currentCryptoCurrencySign: String

    private val liveDataCurrentCryptocurrency: LiveData<CryptocurrencyItemHome>
    val liveDataMyCryptocurrencyList: LiveData<List<CryptocurrencyItemHome>>

    private val liveDataTotalHoldingsValueFiat: LiveData<Double>
    private val liveDataTotalHoldingsValueCrypto: LiveData<Double>
    private val liveDataTotalHoldingsValueFiat24h: LiveData<Double>
    val liveDataTotalHoldingsValueCryptoText: LiveData<String>


    init {
        currentCryptoCurrencyCode = "JSHN"
        currentCryptoCurrencySign = "$$"
        liveDataMyCryptocurrencyList = cryptocurrencyRepository.getMyCryptocurrencyLiveDataList()
        liveDataCurrentCryptocurrency = cryptocurrencyRepository.getSpecificCryptocurrencyLiveData(currentCryptoCurrencyCode)
        liveDataTotalHoldingsValueFiat24h = Transformations.switchMap(liveDataMyCryptocurrencyList) { _ -> MutableLiveData<Double>().apply { value = liveDataMyCryptocurrencyList.value?.sumByDouble { it.amountFiatChange24h } } }
        liveDataTotalHoldingsValueFiat = Transformations.switchMap(liveDataMyCryptocurrencyList) { _ -> MutableLiveData<Double>().apply { value = liveDataMyCryptocurrencyList.value?.sumByDouble { it.amountFiat } } }
        liveDataTotalHoldingsValueCrypto = countTotalHoldingsValueCrypto(liveDataTotalHoldingsValueFiat, liveDataCurrentCryptocurrency)
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
    }*/

    val liveData1 = MutableLiveData<ArrayList<CryptocurrencyItemHome>>()
    // Helper variable to store temporary cryptocurrency which user clicked on to add. It is stored
    // in view model to avoid loosing value during configuration change, e.g. device rotation.
    var selectedCryptocurrency: CryptocurrencyItemHome? = null

    val data: LiveData<ArrayList<CryptocurrencyItemHome>>
        get() = liveData1

    init {
        val tempData = ArrayList<CryptocurrencyItemHome>()


        val btc: CryptocurrencyItemHome = CryptocurrencyItemHome("Bitcoin", 1, 0.56822348, "BTC",6972.90,3962.16, 0.225, -134.05, -0.455, -421.79)
        val eth: CryptocurrencyItemHome = CryptocurrencyItemHome("Etherium", 2, 6.0, "ETH", 407.45, 24414.70, 0.131, -130.96, 0.143, 34.17)
        val xrp: CryptocurrencyItemHome = CryptocurrencyItemHome("XRP", 3, 0.0, "XRP", 0.423225, 0.10, -0.012, -35.30, -1.438, 0.0)


        val dgc: CryptocurrencyItemHome = CryptocurrencyItemHome("DogeCoin", 4, 0.56822348, "DGC",6972.90,3962.16, 0.225, -134.05, -0.455, -421.79)
        val bch: CryptocurrencyItemHome = CryptocurrencyItemHome("Bitcoin Cash", 5, 6.0, "BCH", 407.45, 24414.70, 0.131, -130.96, 0.143, 34.17)


      //  tempData.add(btc)
        //tempData.add(eth)
       // tempData.add(dgc)
        tempData.add(bch)



        liveData1.value = tempData
    }


}