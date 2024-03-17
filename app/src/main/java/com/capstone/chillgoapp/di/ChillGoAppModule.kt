package com.capstone.chillgoapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.capstone.chillgoapp.data.AppData
import com.capstone.chillgoapp.data.DefaultDispatcherProvider
import com.capstone.chillgoapp.data.DispatcherProvider
import com.capstone.chillgoapp.data.local.AppDB
import com.capstone.chillgoapp.data.local.PlaceDao
import com.capstone.chillgoapp.data.repository.GeocodeRepository
import com.capstone.chillgoapp.data.repository.PlacesRepository
import com.capstone.chillgoapp.data.repository.impl.GeocodeRepositoryImpl
import com.capstone.chillgoapp.data.repository.impl.PlacesRepositoryImpl
import com.capstone.chillgoapp.data.source.GeocodeDataSource
import com.capstone.chillgoapp.data.source.PlaceDataSource
import com.capstone.chillgoapp.data.source.RecommendationSource
import com.capstone.chillgoapp.ui.common.DefaultLocationClient
import com.capstone.chillgoapp.ui.common.LocationClient
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChillGoAppModule {
    @Provides
    internal fun providesPlacesSource(): PlaceDataSource =
        //AppData.placesDataSource("myappcc-qugap3elba-et.a.run.app/api") //Gunakan BuildConfig agar tidak terekspose //sudah direvoke
        AppData.placesDataSource("3fe6-2001-448a-3053-25ac-c130-10dd-5914-ae69.ngrok-free.app//api")
    @Provides
    internal fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    internal fun providesGeocodingSource(): GeocodeDataSource =
        AppData.geocodeDataSource("api.bigdatacloud.net/data")

    @Provides
    internal fun providesRecommendationSource(): RecommendationSource =
        AppData.recommendationDataSource("myappml-qugap3elba-et.a.run.app") //sudah direvoke

    @Provides
    internal fun providesPlaceRepository(
        dispatcherProvider: DispatcherProvider,
        placesDataSource: PlaceDataSource,
        placeDao: PlaceDao,
        recommendationSource: RecommendationSource,
        store: DataStore<Preferences>
    ): PlacesRepository {
        return PlacesRepositoryImpl(
            dispatcherProvider,
            placeApi = placesDataSource,
            placeDao = placeDao,
            store = store,
            recommendationPlaceApi = recommendationSource
        )
    }

    @Provides
    internal fun providesGeocodeRepository(
        dispatcherProvider: DispatcherProvider,
        dataSource: GeocodeDataSource,
        store: DataStore<Preferences>
    ): GeocodeRepository = GeocodeRepositoryImpl(
        dispatcher = dispatcherProvider,
        geocodeApi = dataSource,
        store = store
    )

    @Provides
    internal fun provideLocationClient(
        @ApplicationContext context: Context,
    ): LocationClient = DefaultLocationClient(context)

    @Provides
    internal fun providesFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context,
        dispatcherProvider: DispatcherProvider
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = CoroutineScope(dispatcherProvider.io()),
        produceFile = {
            File("${context.cacheDir.path}/myapp.preferences_pb")
        })

    @Provides
    internal fun localDatabase(
        @ApplicationContext appContext: Context
    ): AppDB = AppData.initializeDatabase(appContext)

    @Provides
    internal fun provideWaistDao(appDb: AppDB): PlaceDao = appDb.placeDao()
}