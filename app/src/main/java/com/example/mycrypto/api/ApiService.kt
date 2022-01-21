package com.example.mycrypto.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * REST API access points.
 */
interface ApiService {

    // The @GET annotation tells retrofit that this request is a get type request.
    // The string value tells retrofit that the path of this request is
    // baseUrl + v1/cryptocurrency/listings/latest + query parameter.
    @GET("v1/cryptocurrency/listings/latest")
    // Annotation @Query is used to define query parameter for request. Finally the request url will
    // look like that https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?.
    fun getAllCryptocurrencies(@Query("convert") currency: String,
                               @Query("limit") size: Int = 5000): Call<CryptocurrenciesLatest>
    // The return type for this function is Call with its type CryptocurrenciesLatest.



    //https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?convert=EUR&symbol=1,1027
    @GET("v1/cryptocurrency/quotes/latest")
    fun getCryptocurrenciesById(@Query("convert") currency: String,
                                @Query("symbol") symbol: String):Call<CryptocurrenciesLatest>

}