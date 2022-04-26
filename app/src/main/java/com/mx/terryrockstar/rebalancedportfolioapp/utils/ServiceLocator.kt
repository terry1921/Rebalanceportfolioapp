package com.mx.terryrockstar.rebalancedportfolioapp.utils

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.mx.terryrockstar.rebalancedportfolioapp.BuildConfig
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.DataSource
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.DefaultRepository
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.Repository
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.local.AppDatabase
import com.mx.terryrockstar.rebalancedportfolioapp.data.source.local.LocalDataSource

object ServiceLocator {

    private val lock = Any()
    private var database: AppDatabase? = null
    @Volatile
    var repository: Repository? = null
        @VisibleForTesting set

    fun provideTasksRepository(context: Context): Repository {
        synchronized(this) {
            return repository ?: repository ?: createRepository(context)
        }

    }

    private fun createRepository(context: Context): Repository {
        return DefaultRepository(createLocalDataSource(context))
    }

    private fun createLocalDataSource(context: Context): DataSource {
        val database = database ?: createDataBase(context)
        return LocalDataSource(database.appDao())
    }

    private fun createDataBase(context: Context): AppDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, BuildConfig.DATABASE_NAME
        ).build()
        database = result
        return result
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            repository = null
        }
    }

}