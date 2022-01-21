package com.example.mycrypto.api


import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor used to intercept the actual request and
 * to supply your API Key in REST API calls via a custom header.
 */
class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()
                // TODO: Use your API Key provided by CoinMarketCap Professional API Developer Portal.
                .addHeader("CMC_PRO_API_KEY", "e66959dc-16a4-4317-b3fb-0225df37951a")
                .build()
        return chain.proceed(newRequest)
    }
}