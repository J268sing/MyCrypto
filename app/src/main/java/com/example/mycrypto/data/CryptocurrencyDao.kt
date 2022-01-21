package com.example.mycrypto.data
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * The Data Access Object for the [Cryptocurrency] class.
 */
@Dao
interface CryptocurrencyDao {

    @Query("SELECT * FROM cryptocurrency_itemhome WHERE amount > 0")
    fun getMyCryptocurrencyLiveDataList(): LiveData<List<CryptocurrencyItemHome>>

    @Query("SELECT * FROM cryptocurrency_itemhome")
    fun getAllCryptocurrencyLiveDataList(): LiveData<List<CryptocurrencyItemHome>>

    @Query("SELECT * FROM cryptocurrency_itemhome WHERE symbol = :specificCryptoCode")
    fun getSpecificCryptocurrencyLiveData(specificCryptoCode: String): LiveData<CryptocurrencyItemHome>

    @Query("SELECT symbol FROM cryptocurrency_itemhome")
    fun getSymbolCryptocurrencyColumn(): LiveData<List<String>>

    @Delete
    fun delete(data: List<CryptocurrencyItemHome>)

    @Insert
    fun insertDataToAllCryptocurrencyList(data: List<CryptocurrencyItemHome>):List<Long>

    @Transaction
    open fun reloadCryptocurrencyList(itemList: List<CryptocurrencyItemHome>) {
        delete(itemList)
        insertDataToAllCryptocurrencyList(itemList)
    }
}