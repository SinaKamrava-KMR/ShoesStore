package com.mainway.store.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mainway.store.data.Product
import com.mainway.store.data.repo.source.ProductLocalDataSource

@Database(entities = [Product::class], version = 1, exportSchema = false)
 abstract class AppDatabase : RoomDatabase(){

  abstract fun productDao():ProductLocalDataSource
}