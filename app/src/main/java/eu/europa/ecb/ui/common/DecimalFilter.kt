package eu.europa.ecb.ui.common

import android.text.Editable
import android.text.TextWatcher
import eu.europa.ecb.util.DecimalFormatter

/**
 * Created by Nastya on 08.10.13.
 */
open class DecimalFilter : TextWatcher {

    private var isFormatting: Boolean = false

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable) {
        if (!isFormatting) {
            isFormatting = true

            DecimalFormatter.format(s)
            isFormatting = false
        }
    }
}