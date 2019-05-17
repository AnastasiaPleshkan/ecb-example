package eu.europa.ecb.ui.common

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.KeyEvent

/**
 * User: Nastya
 * Date: 25.08.12
 * Time: 17:14
 */
class NumberDecimalInput(context: Context, attrs: AttributeSet) : AInput(context, attrs) {
    init {

        addTextChangedListener(DecimalFilter())
        setOnTouchListener { _, _ ->
            val ind = text.toString().length - 1
            if (ind > -1)
                setSelection(ind)
            false
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (KeyEvent.KEYCODE_ENTER == keyCode) {
            format()
        }
        return super.onKeyUp(keyCode, event)
    }

    override fun acceptedChars(): CharArray {
        return charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.', ',', ' ')
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) {
            format()
        }
    }

    private fun format() {
        val value = text.toString()
        if (value.isNotEmpty()) {
            setText(value)
        }
        val ind = value.length
        if (ind > -1)
            setSelection(ind)
    }

    fun getValue(): String {
        return text.toString()
                .replace(" ", "")
                .replace(",", ".")
    }
}
