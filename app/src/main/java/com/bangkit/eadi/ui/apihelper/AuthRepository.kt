package com.bangkit.eadi.ui.auth.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bangkit.eadi.R
import com.bangkit.eadi.ui.apihelper.LoginResponse
import com.bangkit.eadi.ui.apihelper.RegisterResponse
import com.bangkit.eadi.ui.apihelper.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    fun provideAuthRepository(context: Context) : AuthRepository {
        return AuthRepository(context)
    }
}

class AuthRepository @Inject constructor(private val context: Context) {

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult: LiveData<Boolean> = _registerResult


    fun login(email: String, password: String) {
        RetrofitClient.instance.login(
            email, password
        ).enqueue(object: Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val user = response.body()
                if (user?.error == false) {
                    val token = user.loginResult.token
                    token?.let {
                        saveToken(it)
                        _loginResult.value = true
                        return
                    }
                }
                _loginResult.value = false
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("error", t.message.toString())
            }
        })
    }

    fun register(name: String, email: String, password: String) {
        RetrofitClient.instance.register(
            name, email, password
        ).enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val user = response.body()
                _registerResult.value = user?.error == false
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("error", t.message.toString())
            }

        })
    }

    fun logout() {
        clearToken()
    }

    private fun saveToken(token: String) {
        val sharedPref = context.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("TOKEN_KEY", token)
        editor.apply()
    }

    fun getToken(): String? {
        val sharedPref = this.context.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)
        return sharedPref.getString("TOKEN_KEY", null)
    }

    fun clearToken() {
        val sharedPref = context.getSharedPreferences(R.string.preference_file_key.toString(), Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.remove("TOKEN_KEY")
        editor.apply()
    }
}