package com.yoloroy.retrofit.util

import android.content.Context
import com.yoloroy.retrofit.news.NewsApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {

    private var okHttpClient: OkHttpClient? = null
    private var retrofit: Retrofit? = null
    private var newsApi: NewsApi? = null

    fun provideOkHttpClient(context: Context) = okHttpClient
        ?: OkHttpClient().newBuilder()
            .addInterceptor(NetworkConnectionInterceptor(context))
            .build()
            .also { okHttpClient = it }

    fun provideRetrofit(
        context: Context,
        baseUrl: String = "https://saurav.tech/NewsAPI/",
        okHttpClient: OkHttpClient = provideOkHttpClient(context)
    ) = retrofit
        ?: Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .also { retrofit = it }

    fun provideNewsApi(retrofit: Retrofit) = newsApi
        ?: retrofit.create(NewsApi::class.java)
            .also { newsApi = it }
}
