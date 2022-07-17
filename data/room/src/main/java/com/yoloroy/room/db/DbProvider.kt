package com.yoloroy.room.db

import android.content.Context
import androidx.room.Room

object DbProvider {
    fun provide(context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "app+database"
    ).build()
}
