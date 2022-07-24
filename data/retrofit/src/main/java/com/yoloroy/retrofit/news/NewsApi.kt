package com.yoloroy.retrofit.news

import com.yoloroy.retrofit.news.model.ArticlesRetrievingResult
import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {

    @GET("top-headlines/category/science/in.json")
    fun getAll(): Call<ArticlesRetrievingResult>
}
