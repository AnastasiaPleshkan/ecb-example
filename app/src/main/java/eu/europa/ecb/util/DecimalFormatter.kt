package eu.europa.ecb.util

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Nastya on 08.10.13.
 */
open class DecimalFormatter {

    companion object {
        fun format(editable: Editable) {

            while (editable.toString().contains(whiteSpace.toString())) {
                val st = editable.toString().indexOf(whiteSpace.toString())
                editable.delete(st, st + 1)
            }

            if (editable.toString().contains(dot.toString())) {
                val st = editable.toString().indexOf(dot.toString())
                editable.replace(st, st + 1, separator.toString())
            }

            var dotPos = editable.toString().indexOf(separator.toInt().toChar())
            while (dotPos != editable.toString().lastIndexOf(separator.toInt().toChar()) && dotPos > -1) {
                val st = editable.toString().lastIndexOf(separator.toString())
                editable.delete(st, st + 1)
            }

            while (dotPos > -1 && dotPos + AFTER_DOT + 1 < editable.length) {
                editable.delete(editable.length - 1, editable.length)
            }

            if (dotPos > BEFORE_DOT || dotPos == -1 && editable.length > BEFORE_DOT) {
                editable.delete(BEFORE_DOT, editable.length)
            }

            dotPos = editable.toString().indexOf(separator.toInt().toChar()) // СЃРЅРѕРІР°
            var countBeforeDot = 0
            val beforePrefixLength = editable.toString().length
            if (dotPos == -1 && beforePrefixLength != 7) {
                for (i in beforePrefixLength - 1 downTo 1) {
                    countBeforeDot++
                    if (countBeforeDot % 3 == 0) {
                        editable.insert(i, whiteSpace.toString())
                    }
                }
            } else {
                if (beforePrefixLength == 7 && dotPos == -1) {
                    editable.insert(beforePrefixLength - 1, separator.toString())
                }
                dotPos = editable.toString().indexOf(separator.toInt().toChar())
                for (i in dotPos - 1 downTo 1) {
                    countBeforeDot++
                    if (countBeforeDot % 3 == 0) {
                        editable.insert(i, whiteSpace.toString())
                    }
                }
            }

            if (editable.length == 1 && editable[0] == separator) {
                editable.insert(0, zero.toString())
            }
            if (editable.length > 1 && editable[0] == zero && editable[1] != separator) {
                editable.delete(0, 1)
            }
        }


        private val separator = ','
        private val whiteSpace = ' '
        private val dot = '.'
        private val zero = '0'

        // limits
        private val BEFORE_DOT = 6
        private val AFTER_DOT = 2
    }
}