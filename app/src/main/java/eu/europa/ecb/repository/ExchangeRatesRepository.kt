package eu.europa.ecb.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import eu.europa.ecb.AppExecutors
import eu.europa.ecb.vo.Envelope
import eu.europa.ecb.api.EcbService
import eu.europa.ecb.db.CubeItemDao
import eu.europa.ecb.db.CubeTimeDao
import eu.europa.ecb.vo.CubeItem
import eu.europa.ecb.vo.CubeTime
import eu.europa.ecb.vo.Resource
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.round

/**
 * Repository that handles Rates instances.
 *
 * unfortunate naming :/ .
 * CubeTime - value object name
 * Repository - type of this class.
 */
@Singleton
class ExchangeRatesRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val cubeItemDao: CubeItemDao,
        private val cubeTimeDao: CubeTimeDao,
        private val ecbService: EcbService
) {

    fun load(): LiveData<Resource<List<CubeItem>>> {
        return object : NetworkBoundResource<List<CubeItem>, Envelope>(appExecutors) {
            override fun saveCallResult(item: Envelope) {
                cubeItemDao.insertItems(item.cube?.cube?.cube!!)
                cubeTimeDao.insert(CubeTime(item.cube?.cube?.time, System.currentTimeMillis()))
            }

            override fun shouldFetch(data: List<CubeItem>?): Boolean {
                return true
            }

            override fun loadFromDb() = cubeItemDao.loadItems()

            override fun createCall() = ecbService.getEcb()

            override fun onFetchFailed() {
            }
        }.asLiveData()
    }

    fun loadTime(): LiveData<CubeTime> {
        return cubeTimeDao.load()
    }
}