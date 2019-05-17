package eu.europa.ecb.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import eu.europa.ecb.ui.rates.ExchangeRatesViewModel
import eu.europa.ecb.viewmodel.EcbViewModelFactory

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ExchangeRatesViewModel::class)
    abstract fun bindSearchViewModel(exchangeRatesViewModel: ExchangeRatesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: EcbViewModelFactory): ViewModelProvider.Factory
}
