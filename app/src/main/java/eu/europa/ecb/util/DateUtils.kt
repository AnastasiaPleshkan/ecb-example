package eu.europa.ecb.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @JvmStatic
    fun format(date: Date): String {
        val format = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
}