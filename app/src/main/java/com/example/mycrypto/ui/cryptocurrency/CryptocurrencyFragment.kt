package com.example.mycrypto.ui.cryptocurrency

import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycrypto.R
import com.example.mycrypto.api.ApiService
import com.example.mycrypto.api.AuthenticationInterceptor
import com.example.mycrypto.api.CryptocurrenciesLatest
import com.example.mycrypto.data.CryptocurrencyItemHome
import com.example.mycrypto.ui.SetUpRetrofitTemporarily
import com.example.mycrypto.ui.home.CryptoItemAdapter
import com.example.mycrypto.ui.utilities.InjectorUtils
import com.example.mycrypto.ui.utilities.nonEmpty
import com.example.mycrypto.ui.utilities.validate
import kotlinx.android.synthetic.main.dialog_add_crypto_amount.view.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//import com.example.mycrypto.databinding.FragmentCryptocurrencyBinding

class CryptocurrencyFragment :  Fragment(),CellClickListener/*,CryptocurrencyAmountDialog.CryptocurrencyAmountDialogListener*/ {
    private lateinit var itemAdapter: CryptoItemAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var cryptocurrencyViewModel: CryptocurrencyViewModel
   // lateinit var binding: FragmentCryptocurrencyBinding
   private var valueAmount: Double = 0.0

    private lateinit var editTextAmount: EditText

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Manage fragment with data binding.
    //    binding = DataBindingUtil.inflate(inflater ,R.layout.fragment_cryptocurrency, container, false)
        Log.i(ContentValues.TAG, "bjashan vinding bond")

       // val v: View  = binding.root
       // recyclerView = v.findViewById(R.id.recycler_view_crypto)

        //return v
        val root = inflater.inflate(R.layout.fragment_cryptocurrency, container, false)
        recyclerView = root.findViewById(R.id.recycler_view_crypto)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        initRecyclerView(recyclerView)
        subscribeUi(requireActivity())

        // Later we will setup Retrofit correctly, but for now we do all in one place just for quick start.
     //   setupRetrofitTemporarily()
    }

    private fun initRecyclerView(recyclerView:RecyclerView) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            itemAdapter = CryptoItemAdapter(this@CryptocurrencyFragment)
            adapter = itemAdapter
        }
    }


    override fun onCellClickListener(position: Int) {
        val clickedItem = itemAdapter.getItem(position)

            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setTitle("How many "+clickedItem.name + " coins do you have?")
        cryptocurrencyViewModel.selectedCryptocurrency = CryptocurrencyItemHome(clickedItem.name,clickedItem.rank,clickedItem.amount,clickedItem.symbol,
                clickedItem.price,clickedItem.amountFiat,clickedItem.pricePercentChange1h,clickedItem.pricePercentChange7d,clickedItem.pricePercentChange24h,clickedItem.amountFiatChange24h)

        Toast.makeText(context, clickedItem.price.toString(), Toast.LENGTH_SHORT).show()

        // Pass null as the parent view because its going in the dialog layout.
            val dialogView = layoutInflater.inflate(R.layout.dialog_add_crypto_amount, null)

        // Set hint for edit text.
        dialogView.edit_text_amount.hint = "Enter Amount"
        // Set the layout for the dialog.
            builder.setView(dialogView)

            builder.setCancelable(true)

        // Set the alert dialog positive/ok button.
            builder.setPositiveButton("OK") { _, _ ->

            } // We will setup listener later.

        // Set the alert dialog neutral/cancel button.
            builder.setNeutralButton("Cancel") { _, _ ->
                // Send the neutral button event back to the host activity.
               // mListener.onCryptocurrencyAmountDialogCancel()
                Toast.makeText(context, "setNeutralButton", Toast.LENGTH_SHORT).show()
            }

            editTextAmount = dialogView.edit_text_amount

             // Initialize the AlertDialog using builder object.
        val alertDialog: AlertDialog = builder.create()
          alertDialog.show()



        alertDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)


        alertDialog.setOnShowListener {
            val buttonPositive = alertDialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
            editTextAmount.nonEmpty(
                    { buttonPositive.isEnabled = false },
                    { buttonPositive.isEnabled = true })

            buttonPositive.setOnClickListener {
                if (onValidateAndConfirm("Valid number is required!")) {

                    // Send the positive button event back to the host activity.
                //    mListener.onCryptocurrencyAmountDialogConfirmButtonClick(this)
                }
            }
        }
        /*dialog.setOnShowListener {
            val buttonPositive = dialog.getButton(androidx.appcompat.app.AlertDialog.BUTTON_POSITIVE)
            editTextAmount.nonEmpty(
                    { buttonPositive.isEnabled = false },
                    { buttonPositive.isEnabled = true })

            buttonPositive.setOnClickListener {
                if (onValidateAndConfirm(error)) {

                    // Send the positive button event back to the host activity.
                    mListener.onCryptocurrencyAmountDialogConfirmButtonClick(this)
                }
            }*/

/*

            builder.setMessage("Add to Favourites?")
            builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id ->
                Toast.makeText(context, "Added to Favourites", Toast.LENGTH_SHORT).show()
            })
            builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                if (dialog != null) {
                }
            })
            val alertDialog: AlertDialog = builder.create()
            alertDialog.show()

*/

    /*    val clicketItem = itemAdapter.getItem(position)
        val cryptocurrencyAmountDialog =
                CryptocurrencyAmountDialog.newInstance(
                        title = String.format(getString(R.string.dialog_cryptocurrency_amount_title), clicketItem.name),
                        hint = getString(R.string.dialog_cryptocurrency_amount_hint),
                        confirmButton = getString(R.string.dialog_cryptocurrency_amount_confirm_button),
                        cancelButton = getString(R.string.dialog_cryptocurrency_amount_cancel_button),
                        error = getString(R.string.dialog_cryptocurrency_amount_error))

        cryptocurrencyViewModel.selectedCryptocurrency = CryptocurrencyItemHome(clicketItem.name,clicketItem.rank,clicketItem.amount,clicketItem.symbol,
        clicketItem.price,clicketItem.amountFiat,clicketItem.pricePercentChange1h,clicketItem.pricePercentChange7d,clicketItem.pricePercentChange24h,clicketItem.amountFiatChange24h)

        // Display the alert dialog.
        //ethe error ho sakda wa, childfragmentmanager likhan de karke
        cryptocurrencyAmountDialog.show(requireActivity().supportFragmentManager, DIALOG_CRYPTOCURRENCY_AMOUNT_TAG)*/
        //Log.i(ContentValues.TAG,"this item clicked:   " +itemAdapter.getItem(position).toString())
    }



    // We check if user entered amount number is valid before confirmation actions.
    private fun onValidateAndConfirm(errorMsg: String): Boolean {
        return editTextAmount.validate({ text ->
            try {
                valueAmount = text.toDouble()
                true
            } catch (e: Throwable) {
                false
            }
        }, errorMsg)
    }



    /* override fun onCryptocurrencyAmountDialogCancel() {
         // User touched somewhere that dismissed dialog.
         Log.i(ContentValues.TAG,"onCryptocurrencyAmountDialogCancel")

         cryptocurrencyViewModel.selectedCryptocurrency = null
     }

     // The dialog fragment receives a reference to this Activity through the
     // Fragment.onAttach() callback, which it uses to call the following methods
     // defined by the CryptocurrencyAmountDialog.CryptocurrencyAmountDialogListener interface.
     override fun onCryptocurrencyAmountDialogConfirmButtonClick(cryptocurrencyAmountDialog: CryptocurrencyAmountDialog) {
         // User touched the dialog's positive button.

         cryptocurrencyViewModel.selectedCryptocurrency = null

     }*/

    private fun subscribeUi(activity: FragmentActivity) {

        val factory = InjectorUtils.provideCryptocurrencyViewModelFactory(activity.application)

        // Obtain ViewModel from ViewModelProviders, using this fragment as LifecycleOwner.
        Log.i(TAG, "hello:innntooo no jashan")

        cryptocurrencyViewModel = ViewModelProvider(this,factory).get(CryptocurrencyViewModel::class.java)

      //  binding.cryptocurrencyDatabindingCrypto = cryptocurrencyViewModel
        Log.i(TAG, "hello:innntooo crypto fragmentjashan")
     //   binding.cryptocurrencyDatabindingCrypto = cryptocurrencyViewModel
        Log.i(TAG, "hello:innntooo cryoti fra jashan")

        //Toast.makeText(context,cryptocurrencyViewModel.liveDataMyCryptocurrencyList.value?.size.toString(),Toast.LENGTH_LONG).show()
        // Observe data on the ViewModel, exposed as a LiveData
        cryptocurrencyViewModel.liveData1.observe(viewLifecycleOwner, Observer<List<CryptocurrencyItemHome>> { data ->
            // Set the data exposed by the LiveData
            if (data != null && data.isNotEmpty() ) {
                recyclerView.visibility = View.GONE
                Log.i(TAG, "toast happen")
             //   Toast.makeText(context, data.size.toString(), Toast.LENGTH_LONG).show()

                Log.i(TAG, "hello:innn jashan")
             //   Log.i(TAG, data[0].symbol)
                //  Log.i(TAG, (data != null).toString())
                itemAdapter.setData(data)
                recyclerView.visibility = View.GONE
                setupRetrofitTemporarily()


            }else {
                recyclerView.visibility = View.GONE
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



        // url:   https://sandbox-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?CMC_PRO_API_KEY=e66959dc-16a4-4317-b3fb-0225df37951a&convert=USD&id=1,1027
        // replace https://sandbox-api.coinmarketcap.com/ with  https://pro-api.coinmarketcap.com/
        val api = Retrofit.Builder() // Create retrofit builder.
            .baseUrl(" https://sandbox-api.coinmarketcap.com/") // Base url for the api has to end with a slash.
            .addConverterFactory(GsonConverterFactory.create()) // Use GSON converter for JSON to POJO object mapping.
            .client(client) // Here we set the custom OkHttp client we just created.
            .build().create(ApiService::class.java) // We create an API using the interface we defined.


        val adapterData: MutableList<CryptocurrencyItemHome> = ArrayList<CryptocurrencyItemHome>()

        val currentFiatCurrencyCode = "EUR"

        // Let's make asynchronous network request to get all latest cryptocurrencies from the server.
        // For query parameter we pass "EUR" as we want to get prices in euros.
         val call = api.getCryptocurrenciesById( "USD","BTC")
       //  val call = api.getAllCryptocurrencies("USD", 2)
        Log.i(TAG, "call aa wa:   " + call.toString())

        val result = call.enqueue(object : Callback<CryptocurrenciesLatest> {


            // You will always get a response even if something wrong went from the server.
            override fun onFailure(call: Call<CryptocurrenciesLatest>, t: Throwable) {
             //   Log.i(TAG,"response body:  " + response.body())

                Log.i(TAG, "aa response aeya wa " + call.toString())
           //     Toast.makeText(context, "not OK.", Toast.LENGTH_LONG).show()

             /*   Snackbar.make(findViewById(android.R.id.content),
                    // Throwable will let us find the error if the call failed.
                    "Call failed! " + t.localizedMessage,
                    Snackbar.LENGTH_INDEFINITE).show()*/
            }

            override fun onResponse(call: Call<CryptocurrenciesLatest>, response: Response<CryptocurrenciesLatest>) {

                // Check if the response is successful, which means the request was successfully
                // received, understood, accepted and returned code in range [200..300).
                Toast.makeText(context, "start response check", Toast.LENGTH_LONG).show()
                Log.i(TAG,"response body:  " + response.body())


                if (response.isSuccessful && response.body()!=null) {
                    Log.i(TAG,"response body:  " + response.body())

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

                } else{
                    Log.i(TAG,"response body:  " + response.body())

                    Log.i(TAG, "response not sexful, response.code()= "+response.code() + ",   response =  " + response.toString() )


                }
                // Else if the response is unsuccessful it will be defined by some special HTTP
                // error code, which we can show for the user.
              /*  else Snackbar.make(findViewById(android.R.id.content),
                    "Call error with HTTP status code " + response.code() + "!",
                    Snackbar.LENGTH_INDEFINITE).show()*/

            }

        })

    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView



        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               // hideKeyboard()
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {

                return true
            }
        })

        searchView.setOnSuggestionListener(object: SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
              //  hideKeyboard()

                // Do something with selection
                return true
            }

        })

        super.onCreateOptionsMenu(menu, inflater)
    }
}