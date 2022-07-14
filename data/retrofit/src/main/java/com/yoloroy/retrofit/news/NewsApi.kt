package com.yoloroy.retrofit.news

import com.yoloroy.retrofit.news.model.ArticlesRetrievingResult
import retrofit2.http.GET

interface NewsApi {

    @GET("https://saurav.tech/NewsAPI/top-headlines/category/science/in.json")
    fun getAll(): ArticlesRetrievingResult
}
