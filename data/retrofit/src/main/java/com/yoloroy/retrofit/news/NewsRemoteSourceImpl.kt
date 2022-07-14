package com.yoloroy.retrofit.news

import android.util.Log
import com.yoloroy.data.common.Resource
import com.yoloroy.data.news.NewsRemoteSource
import com.yoloroy.domain.model.NewsPredicate
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.retrofit.news.model.toNewsFilterData
import com.yoloroy.retrofit.util.NoConnectionException
import retrofit2.HttpException

class NewsRemoteSourceImpl(
    private val api: NewsApi
) : NewsRemoteSource {

    companion object {
        private const val TAG = "NewsRetroRemoteSource"
    }

    override suspend fun searchNews(predicate: NewsPredicate): Resource<List<NewsShort>> {
        return try {
            val newsShorts = api.getAll().articles
                .filter { predicate.test(it.toNewsFilterData()) }
                .map { it.Short() }
            Resource.Success(newsShorts)
        } catch (e: HttpException) {
            Log.w(TAG, "code: ${e.code()} message: ${e.message()}")
            Resource.HttpError()
        } catch (e: NoConnectionException) {
            Log.i(TAG, "no connection!")
            Resource.NoInternetError()
        } catch (e: Exception) {
            Log.e(TAG, e.stackTraceToString())
            Resource.UnknownError()
        }
    }
}
