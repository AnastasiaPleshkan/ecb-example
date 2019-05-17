
package eu.europa.ecb.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

object ServiceUtil {

    fun registerService(context: Context, aClass: Class<*>, repeat: Long) {
        val intent = Intent(context, aClass)
        val pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        if (repeat > 0) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + repeat, repeat, pendingIntent)
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis(), pendingIntent)
        }
    }

    fun cancel(context: Context, aClass: Class<*>) {
        val intent = Intent(context, aClass)
        val pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}
