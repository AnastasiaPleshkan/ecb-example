package eu.europa.ecb.di

import eu.europa.ecb.ui.rates.ExchangeRatesFragment

import dagger.Module
import dagger.android.ContributesAndroidInjector
import eu.europa.ecb.ui.animation.AnimatedFragment

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeRatesFragment(): ExchangeRatesFragment
    @ContributesAndroidInjector
    abstract fun contributeAnimatedFragment(): AnimatedFragment
}
