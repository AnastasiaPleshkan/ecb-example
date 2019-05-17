package eu.europa.ecb.di

import android.app.Application
import eu.europa.ecb.EcbApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import eu.europa.ecb.jobs.DailyWorker
import eu.europa.ecb.jobs.PingService
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(ecbApp: EcbApp)
    fun inject(worker: DailyWorker)
    fun inject(service: PingService)
}
