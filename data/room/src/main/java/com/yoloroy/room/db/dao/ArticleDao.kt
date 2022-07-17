package com.yoloroy.room.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yoloroy.room.db.entity.ArticleEntity

@Dao
interface ArticleDao {
    @Query("select * from articles where article_id = :id")
    fun getById(id: Long): ArticleEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg articles: ArticleEntity): Array<Long>
}
