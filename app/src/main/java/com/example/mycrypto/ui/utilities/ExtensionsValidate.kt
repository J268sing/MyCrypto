package com.example.mycrypto.ui.utilities


import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Extension functions allow you to add behaviour to a class without the need of getting to its
 * source code, since it can be declared outside the scope of its class.
 */

// If the text changes, do some actions.
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    })
}

// Check if edit text is empty or not and invoke actions accordingly.
fun EditText.nonEmpty(onEmpty: (() -> Unit), onNotEmpty: (() -> Unit)) {
    if (this.text.toString().isEmpty()) onEmpty.invoke()
    this.afterTextChanged {
        if (it.isEmpty()) onEmpty.invoke()
        if (it.isNotEmpty()) onNotEmpty.invoke()
    }
}

// Validate user input with custom validator and show error if validation did not pass.
fun EditText.validate(validator: (String) -> Boolean, message: String):Boolean {
    val isValid = validator(this.text.toString())
    this.error = if (isValid) null else message
    return isValid
}