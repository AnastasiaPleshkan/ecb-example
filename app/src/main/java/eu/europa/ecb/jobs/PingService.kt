package eu.europa.ecb.jobs

import android.app.Service
import android.content.Intent

import androidx.lifecycle.LifecycleService
import eu.europa.ecb.di.AppInjector
import timber.log.Timber

class PingService : LifecycleService() {

    override fun onCreate() {
        super.onCreate()
        Timber.d("Created")
        AppInjector.appComponent.inject(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        DailyWorker.start(applicationContext)

        stopSelf(startId)

        return Service.START_NOT_STICKY
    }
}
