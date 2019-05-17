package eu.europa.ecb.di

import android.app.Application
import androidx.room.Room
import eu.europa.ecb.api.EcbService
import eu.europa.ecb.db.EcbDb
import eu.europa.ecb.db.CubeTimeDao
import eu.europa.ecb.util.LiveDataCallAdapterFactory
import dagger.Module
import dagger.Provides
import eu.europa.ecb.db.CubeItemDao
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.BuildConfig
import timber.log.Timber


@Module(includes = [ViewModelModule::class])
class AppModule {

    @Provides
    fun provideOkHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggerInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { it ->
            Timber.d(it)
        })
        loggerInterceptor.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.HEADERS
        return loggerInterceptor
    }

    @Singleton
    @Provides
    fun provideEcbService(logInterceptor: HttpLoggingInterceptor): EcbService {
        val httpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
        httpClientBuilder.addInterceptor(logInterceptor)
        val client = httpClientBuilder.build()

        return Retrofit.Builder()
                .baseUrl("https://www.ecb.europa.eu/stats/eurofxref/")
                .client(client)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
                .create(EcbService::class.java)
    }

    @Singleton
    @Provides
    fun provideDb(app: Application): EcbDb {
        return Room
                .databaseBuilder(app, EcbDb::class.java, "ecb.db")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Singleton
    @Provides
    fun provideCubeTimeDao(db: EcbDb): CubeTimeDao {
        return db.cubeTimeDao()
    }

    @Singleton
    @Provides
    fun provideCubeItemDao(db: EcbDb): CubeItemDao{
        return db.cubeItemDao()
    }
}
