package com.yoloroy.newsapp.ui.mapper

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommonProblemMapperTest {

    private val context: Context by lazy { InstrumentationRegistry.getInstrumentation().targetContext }

    @Test
    fun map() {
        val mapper: Mapper<Result, String?> = CommonProblemMapper(
            context,
            UnknownProblem::class.java,
            NoInternetConnectionProblem::class.java
        )

        for (problem in listOf(UnknownProblem(), NoInternetConnectionProblem())) {
            val expected = problem.string
            val actual = mapper.map(problem)
            assertEquals(problem.name, expected, actual)
        }
        assertNull(mapper.map(NoProblem()))
    }

    abstract inner class Result(val name: String, val string: String = name)

    inner class NoProblem : Result("NoProblem")
    inner class UnknownProblem : Result(
        name = "UnknownProblem",
        string = context.getString(com.yoloroy.newsapp.R.string.unknown_problem)
    )
    inner class NoInternetConnectionProblem : Result(
        name = "NoInternetConnectionProblem",
        string = context.getString(com.yoloroy.newsapp.R.string.no_internet_connection)
    )
}
