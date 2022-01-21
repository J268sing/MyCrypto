package com.example.mycrypto.ui.home
import android.content.ContentValues
import androidx.lifecycle.Observer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycrypto.R
import androidx.lifecycle.ViewModelProvider
import com.example.mycrypto.api.ApiService
import com.example.mycrypto.api.AuthenticationInterceptor
import com.example.mycrypto.api.CryptocurrenciesLatest
import com.example.mycrypto.data.CryptocurrencyItemHome
import com.example.mycrypto.databinding.FragmentHomeBinding
import com.example.mycrypto.ui.utilities.InjectorUtils
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var itemAdapter: HomeItemAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyListView: View




    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_home, container, false)
        val v: View  = binding.root
        recyclerView = v.findViewById(R.id.recycler_view)
        emptyListView = v.findViewById(R.id.layout_fragment_main_list_empty)

        Log.i(ContentValues.TAG, "bjashan vinding bond")

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initRecyclerView(recyclerView)
        subscribeUi(requireActivity())
    }

    private fun initRecyclerView(recyclerView:RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAdapter = HomeItemAdapter()
            adapter = itemAdapter
        }
     }

    private fun subscribeUi(activity: FragmentActivity) {
        val factory = InjectorUtils.provideHomeViewModelFactory(activity.application)
        homeViewModel = ViewModelProvider(this,factory).get(HomeViewModel::class.java)
        binding.cryptocurrencyDatabindingHome = homeViewModel

        val btc: CryptocurrencyItemHome = CryptocurrencyItemHome("Bitcoin", 1, 0.62248, "BTC",92.0,3962.16, 0.225, -134.05, -0.455, -421.79)
        homeViewModel.liveDataMyCryptocurrencyList.value.
        // Update the list when the data changes by observing data on the ViewModel, exposed as a LiveData.
        homeViewModel.liveDataMyCryptocurrencyList.observe(viewLifecycleOwner, Observer<List<CryptocurrencyItemHome>> { data ->
            if (data != null && data.isNotEmpty()) {
                emptyListView.visibility = View.GONE
                Toast.makeText(context, data.size.toString(), Toast.LENGTH_SHORT).show()
                recyclerView.visibility = View.VISIBLE
               // setupRetrofitTemporarily()
               itemAdapter.setData(data)
            } else {
                recyclerView.visibility = View.GONE
                emptyListView.visibility = View.VISIBLE
            }
        })
    }




    private fun setupRetrofitTemporarily() {



        // We need to prepare a custom OkHttp client because need to use our custom call interceptor.
        // to be able to authenticate our requests.
        val builder = OkHttpClient.Builder()
        // We add the interceptor to OkHttpClient.
        // It will add authentication headers to every call we make.
        builder.interceptors().add(AuthenticationInterceptor())
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
                    Toast.makeText(context, "Call OK.", Toast.LENGTH_LONG).show()


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

                    recyclerView.visibility = View.VISIBLE
                    itemAdapter.setData(adapterData)

                }
                // Else if the response is unsuccessful it will be defined by some special HTTP
                // error code, which we can show for the user.
                /*  else Snackbar.make(findViewById(android.R.id.content),
                      "Call error with HTTP status code " + response.code() + "!",
                      Snackbar.LENGTH_INDEFINITE).show()*/

            }

        })

    }

}