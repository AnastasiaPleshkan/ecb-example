package eu.europa.ecb.jobs

import android.content.Context
import androidx.work.*
import eu.europa.ecb.AppExecutors
import eu.europa.ecb.api.EcbService
import eu.europa.ecb.db.CubeItemDao
import eu.europa.ecb.db.CubeTimeDao
import eu.europa.ecb.di.AppInjector
import eu.europa.ecb.repository.ExchangeRatesRepository
import javax.inject.Inject
import java.util.concurrent.TimeUnit
import androidx.work.PeriodicWorkRequest
import eu.europa.ecb.vo.CubeTime
import timber.log.Timber

class DailyWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {


    companion object {
        private const val tag: String = "DailyWorker"
        fun start(appContext: Context) {
            Timber.d("DailyWorker start")
//            val dayWorkBuilder = PeriodicWorkRequest.Builder(DailyWorker::class.java, 30, TimeUnit.SECONDS)
            // Add Tag to workBuilder
            val dayWorkBuilder = OneTimeWorkRequest.Builder(DailyWorker::class.java)
            dayWorkBuilder.addTag(tag)
            // Create the actual work object:
            val dayWork = dayWorkBuilder.build()
            // Then enqueue the recurring task:
            WorkManager.getInstance(appContext).enqueue(dayWork)
        }
    }

    @Inject
    lateinit var cubeTimeDao: CubeTimeDao
    @Inject
    lateinit var cubeItemDao: CubeItemDao
    @Inject
    lateinit var service: EcbService

    override fun doWork(): Result {
        Timber.d("Do work")
        AppInjector.appComponent.inject(this)

        val response = service.callEcb().execute().body()
        Timber.d("$response")

        cubeItemDao.insertItems(response?.cube?.cube?.cube!!)
        cubeTimeDao.insert(CubeTime(response.cube?.cube?.time, System.currentTimeMillis()))

        return Result.success()
    }
}
