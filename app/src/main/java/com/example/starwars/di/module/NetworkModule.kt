package com.example.starwars.di.module

import com.example.starwars.api.CharacterAPI
import com.example.starwars.api.PlanetAPI
import com.example.starwars.api.StarshipAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL: String = "https://swapi.dev/api/"

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val httpClient = buildHTTPClient()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    @Provides
    fun providesCharacterApi(retrofit: Retrofit): CharacterAPI = retrofit.create(CharacterAPI::class.java)

    @Provides
    fun providesPlanetApi(retrofit: Retrofit): PlanetAPI = retrofit.create(PlanetAPI::class.java)

    @Provides
    fun providesStarshipApi(retrofit: Retrofit): StarshipAPI = retrofit.create(StarshipAPI::class.java)

    private fun buildHTTPClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(2, TimeUnit.MINUTES)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
}