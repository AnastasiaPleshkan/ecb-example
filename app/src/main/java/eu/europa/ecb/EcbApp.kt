package eu.europa.ecb

import android.app.Activity
import android.app.Application
import eu.europa.ecb.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import eu.europa.ecb.jobs.PingService
import eu.europa.ecb.util.ServiceUtil
import timber.log.Timber
import javax.inject.Inject


class EcbApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)

        // Register ping restart service
        ServiceUtil.cancel(baseContext, PingService::class.java)
        ServiceUtil.registerService(baseContext, PingService::class.java, 30000)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}
