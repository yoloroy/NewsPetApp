@file:Suppress("LocalVariableName")

package com.yoloroy.retrofit.news

import com.yoloroy.data.common.Resource
import com.yoloroy.domain.model.NewsPredicate
import com.yoloroy.domain.model.NewsShort
import com.yoloroy.retrofit.news.model.Article
import com.yoloroy.retrofit.news.model.ArticlesRetrievingResult
import com.yoloroy.retrofit.news.model.Source
import com.yoloroy.retrofit.util.NoConnectionException
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class NewsRemoteSourceImplTest {

    private val someArticle get() = Article(
        "author",
        "content",
        "description",
        "someday",
        Source(null, "nowhere"),
        "title",
        "link",
        null/*image*/
    )

    @Test
    fun searchNews_OK() = runBlocking {
        val articles = List(10) { someArticle }
        val api = FakeApi(articles)
        val source = NewsRemoteSourceImpl(api)

        val result = source.searchNews(NewsPredicate.Empty)
        assert(result is Resource.Success)
        assertEquals(
            articles.size,
            (result as Resource.Success).data.size
        )
    }

    @Test
    fun searchNews_OK_FILTERED() = runBlocking {
        val articles = List(100) { i -> someArticle.copy(title = (i % 2).toString()) }
        val api = FakeApi(articles)
        val source = NewsRemoteSourceImpl(api)

        val titleContains_0 = NewsPredicate.TitleContains("0")
        val result = source.searchNews(titleContains_0)
        assert(result is Resource.Success)
        assertEquals(
            articles.filter { it.title.contains("0") }.size,
            (result as Resource.Success).data.size
        )
    }

    @Test
    fun searchNews_HTTP_EXCEPTION() = runBlocking {
        val api = FakeApi(exception = HttpException(
            Response.error<String>(404, ResponseBody.create(
                MediaType.parse("text/plain"), "this is very bad")
            )
        ))
        val source = NewsRemoteSourceImpl(api)

        assert(
            source.searchNews(NewsPredicate.Empty) is Resource.HttpError<List<NewsShort>>
        )
    }

    @Test
    fun searchNews_NO_INTERNET() = runBlocking {
        val api = FakeApi(exception = NoConnectionException())
        val source = NewsRemoteSourceImpl(api)

        assert(
            source.searchNews(NewsPredicate.Empty) is Resource.NoInternetError<List<NewsShort>>
        )
    }

    @Test
    fun searchNews_EMPTY() = runBlocking {
        val articles = emptyList<Article>()
        val api = FakeApi(articles)
        val source = NewsRemoteSourceImpl(api)

        assertEquals(Resource.Success(articles.map { it.Short() }), source.searchNews(NewsPredicate.Empty))
    }

    @Test
    fun searchNews_UNKNOWN_ERROR() = runBlocking {
        val api = FakeApi(exception = Exception())
        val source = NewsRemoteSourceImpl(api)

        assert(source.searchNews(NewsPredicate.Empty) is Resource.UnknownError<List<NewsShort>>)
    }

    inner class FakeApi(
        private val articles: List<Article> = emptyList(),
        private val exception: Throwable? = null
    ) : NewsApi {

        override fun getAll(): ArticlesRetrievingResult {
            exception?.let { throw it }
            return ArticlesRetrievingResult(articles, "OK", articles.size)
        }
    }
}
