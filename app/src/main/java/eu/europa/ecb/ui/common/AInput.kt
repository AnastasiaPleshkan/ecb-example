package eu.europa.ecb.ui.common

import android.content.Context
import android.text.method.KeyListener
import android.text.method.NumberKeyListener
import android.util.AttributeSet
import android.widget.EditText

/**
 * User: Nastya
 * Date: 22.12.15
 * Time: 16:27
 */
abstract class AInput protected constructor(context: Context, attrs: AttributeSet) : EditText(context, attrs) {

    private val currentInputType: Int
        get() = this.inputType

    init {
        Init()
    }

    private fun Init() {
        val keyListener = object : NumberKeyListener() {
            override fun getInputType(): Int {
                return currentInputType
            }

            override fun getAcceptedChars(): CharArray {
                return acceptedChars()
            }
        }
        setKeyListener(keyListener)

    }

    abstract fun acceptedChars(): CharArray

    @Deprecated("")
    override fun setKeyListener(input: KeyListener) {
        super.setKeyListener(input)
    }

}
