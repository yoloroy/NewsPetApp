package com.yoloroy.room.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoloroy.room.db.dao.ArticleDao
import com.yoloroy.room.db.entity.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 2,
    autoMigrations = [AutoMigration(from = 1, to = 2)],
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}
