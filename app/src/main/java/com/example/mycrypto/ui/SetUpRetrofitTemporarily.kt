package com.example.mycrypto.ui

import android.view.View
import android.widget.Toast
import com.example.mycrypto.api.ApiService
import com.example.mycrypto.api.AuthenticationInterceptor
import com.example.mycrypto.api.CryptocurrenciesLatest
import com.example.mycrypto.data.CryptocurrencyItemHome
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.app.AlertDialog

 class SetUpRetrofitTemporarily() {

    // We need to prepare a custom OkHttp client because need to use our custom call interceptor.
    // to be able to authenticate our requests.
    val builder = OkHttpClient.Builder()
    // We add the interceptor to OkHttpClient.
    // It will add authentication headers to every call we make.
     val j = builder.interceptors().add(AuthenticationInterceptor())
    val client = builder.build()



    // replace https://sandbox-api.coinmarketcap.com/ with  https://pro-api.coinmarketcap.com/
    val api = Retrofit.Builder() // Create retrofit builder.
            .baseUrl("https://sandbox-api.coinmarketcap.com/") // Base url for the api has to end with a slash.
            .addConverterFactory(GsonConverterFactory.create()) // Use GSON converter for JSON to POJO object mapping.
            .client(client) // Here we set the custom OkHttp client we just created.
            .build().create(ApiService::class.java) // We create an API using the interface we defined.


    val adapterData: MutableList<CryptocurrencyItemHome> = ArrayList<CryptocurrencyItemHome>()

    val currentFiatCurrencyCode = "EUR"

    // Let's make asynchronous network request to get all latest cryptocurrencies from the server.
    // For query parameter we pass "EUR" as we want to get prices in euros.
    val call = api.getAllCryptocurrencies("USD",5000)
    val result = call.enqueue(object : Callback<CryptocurrenciesLatest> {

        // You will always get a response even if something wrong went from the server.
        override fun onFailure(call: Call<CryptocurrenciesLatest>, t: Throwable) {

            /*   Snackbar.make(findViewById(android.R.id.content),
                   // Throwable will let us find the error if the call failed.
                   "Call failed! " + t.localizedMessage,
                   Snackbar.LENGTH_INDEFINITE).show()*/
        }

        override fun onResponse(call: Call<CryptocurrenciesLatest>, response: Response<CryptocurrenciesLatest>) {

            // Check if the response is successful, which means the request was successfully
            // received, understood, accepted and returned code in range [200..300).
            if (response.isSuccessful) {

                // If everything is OK, let the user know that.
                //Toast.makeText(context, "Call OK.", Toast.LENGTH_LONG).show()


                // Than quickly map server response data to the ListView adapter.
                val cryptocurrenciesLatest: CryptocurrenciesLatest? = response.body()
                cryptocurrenciesLatest!!.data.forEach {
                    val cryptocurrency = CryptocurrencyItemHome(it.name, it.cmcRank.toShort(),
                            0.0, it.symbol, it.quote.currency.price,
                            0.0, it.quote.currency.percentChange1h,
                            it.quote.currency.percentChange7d, it.quote.currency.percentChange24h,
                            0.0)
                    adapterData.add(cryptocurrency)
                }
                /* Toast.makeText(context, adapterData[9].price.toString(), Toast.LENGTH_SHORT).show()
                 Toast.makeText(context, adapterData[9].symbol, Toast.LENGTH_SHORT).show()
                 Toast.makeText(context, adapterData[10].price.toString(), Toast.LENGTH_SHORT).show()
                 Toast.makeText(context, adapterData[10].symbol, Toast.LENGTH_LONG).show()*/



            }
            // Else if the response is unsuccessful it will be defined by some special HTTP
            // error code, which we can show for the user.
            /*  else Snackbar.make(findViewById(android.R.id.content),
                  "Call error with HTTP status code " + response.code() + "!",
                  Snackbar.LENGTH_INDEFINITE).show()*/

        }

    })

}