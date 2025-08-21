package com.mark.data

import android.content.Context
import android.content.SharedPreferences
import androidx.test.espresso.core.internal.deps.dagger.Module
import androidx.test.espresso.core.internal.deps.dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesTokenRepository
@Inject constructor(
    private val sharedPreferences: SharedPreferences
):TokenRepository{
    companion object{
        private const val KEY_AUTH_TOKEN="auth_token"
    }
    override fun getToken(): String? {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null)
    }

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(KEY_AUTH_TOKEN,token).apply()
    }

    override fun clearToken() {
        sharedPreferences.edit().remove(KEY_AUTH_TOKEN).apply()
    }

}
@Module
@InstallIn(SingletonComponent::class)
object StorageModule {
    private const val PREFS_NAME = "sajili_prefs"

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
}