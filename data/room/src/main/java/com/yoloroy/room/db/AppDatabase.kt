package com.yoloroy.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoloroy.room.db.dao.ArticleDao
import com.yoloroy.room.db.entity.ArticleEntity

@Database(entities = [ArticleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}
