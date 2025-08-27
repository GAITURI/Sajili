package com.mark.data


import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{
    @Binds
    @Singleton
   abstract fun bindTokenRepository(
        tokenRepositoryImpl:SharedPreferencesTokenRepository

    ):TokenRepository
//@Provides
//@Singleton
//fun provideTokenRepository(sharedPreferences: SharedPreferences): TokenRepository {
//    return SharedPreferencesTokenRepository(sharedPreferences)
//}
}