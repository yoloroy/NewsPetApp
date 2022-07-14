package com.yoloroy.retrofit.util

import android.content.Context
import android.net.ConnectivityManager
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    private val isConnected: Boolean get() { // TODO fix deprecated
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    override fun intercept(chain: Interceptor.Chain): Response = when {
        !isConnected -> throw NoConnectionException()
        else -> {
            val builder = chain.request().newBuilder()
            chain.proceed(builder.build())
        }
    }
}
