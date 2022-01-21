package com.example.mycrypto.ui.settings

import android.app.AlertDialog
import android.content.ContentValues
import android.content.DialogInterface
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mycrypto.R
import com.example.mycrypto.ui.cryptocurrency.CryptocurrencyAmountDialog
import com.example.mycrypto.ui.cryptocurrency.CryptocurrencyAmountDialog.Companion.DIALOG_CRYPTOCURRENCY_AMOUNT_TAG


class SettingsFragment : Fragment(){

    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
                ViewModelProvider(this).get(SettingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
      //  val textView: TextView = root.findViewById(R.id.text_settings)
        //settingsViewModel.text.observe(viewLifecycleOwner, Observer {
          //  textView.text = it
        //})

        val button: Button = root.findViewById(R.id.button_id)
        button.setOnClickListener(View.OnClickListener {
            Toast.makeText(context, "dialog", Toast.LENGTH_LONG).show()


          //  val clicketItem = itemAdapter.getItem(position)
            showDeleteConfirmationDialog2()


    })
                return root

}
    private fun showDeleteConfirmationDialog2() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
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
    }

}