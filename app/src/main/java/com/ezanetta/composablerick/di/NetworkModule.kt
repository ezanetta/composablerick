package com.ezanetta.composablerick.di

import com.ezanetta.composablerick.BuildConfig
import com.ezanetta.composablerick.data.network.RickAndMortyApiClient
import com.ezanetta.composablerick.data.network.RickAndMortyApiService
import com.ezanetta.composablerick.data.network.RickAndMortyApiServiceImpl
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRepoApiClient(): RickAndMortyApiClient {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(OkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory.create())
            .build()
            .create(RickAndMortyApiClient::class.java)
    }

    @Provides
    fun providesApiService(
        rickAndMortyApiClient: RickAndMortyApiClient
    ): RickAndMortyApiService {
        return RickAndMortyApiServiceImpl(rickAndMortyApiClient)
    }
}