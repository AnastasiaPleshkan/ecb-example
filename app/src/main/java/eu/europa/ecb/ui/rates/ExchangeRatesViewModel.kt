package eu.europa.ecb.ui.rates

import androidx.lifecycle.*
import eu.europa.ecb.AppExecutors
import eu.europa.ecb.repository.ExchangeRatesRepository
import eu.europa.ecb.util.AbsentLiveData
import eu.europa.ecb.vo.CubeItem
import eu.europa.ecb.vo.CubeTime
import eu.europa.ecb.vo.Resource
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject

class ExchangeRatesViewModel
@Inject constructor(private var appExecutors: AppExecutors,
                    private var exchangeRatesRepository: ExchangeRatesRepository) : ViewModel() {

    private val df : DecimalFormat = DecimalFormat("#,###.##")

    private var selectedIn: CubeItem? = null
    private var selectedOut: CubeItem? = null

    private val _value = MutableLiveData<String>()
    private val query: LiveData<String> = _value

    val result: LiveData<String> = Transformations
            .switchMap(_value) { value ->
                if (value.isNullOrBlank()) {
                    AbsentLiveData.create()
                } else {
                    calculate(query.value!!, selectedIn, selectedOut)
                }
            }

    fun setValue(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == _value.value) {
            return
        }
        _value.value = input
    }

    fun setSelectedIn(value: CubeItem) {
        selectedIn = value
        refresh()
    }

    fun setSelectedOut(value: CubeItem) {
        selectedOut = value
        refresh()
    }

    private fun refresh() {
        _value.value?.let {
            _value.value = it
        }
    }

    private fun calculate(value: String, selectedIn: CubeItem?, selectedOut: CubeItem?): LiveData<String> {
        val result = MediatorLiveData<String>()
        appExecutors.diskIO().execute {
            val rateOut = selectedOut?.rate?.toDouble()
            if (rateOut == null) {
                result.postValue("Select current currency")
                return@execute
            }
            val rateIn = selectedIn?.rate?.toDouble()
            if (rateIn == null) {
                result.postValue("Select convert currency")
                return@execute
            }
            val roundedValue = ((rateOut / rateIn) * value.toDouble()).round(2)
            val formatted = df.format(roundedValue)
                    .replace(",", " ")
                    .replace(".", ",")
            result.postValue("$formatted ${selectedOut.currency}")
        }
        return result
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }

    fun load(): LiveData<Resource<List<CubeItem>>> {
        return exchangeRatesRepository.load()
    }

    fun loadUpdatedTime() :LiveData <CubeTime> {
        return exchangeRatesRepository.loadTime()
    }
}
