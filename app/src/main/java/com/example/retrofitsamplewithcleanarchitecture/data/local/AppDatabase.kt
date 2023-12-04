package com.example.retrofitsamplewithcleanarchitecture.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.retrofitsamplewithcleanarchitecture.data.local.model.AuthTokenEntity
import com.example.retrofitsamplewithcleanarchitecture.util.Constants
import com.example.retrofitsamplewithcleanarchitecture.util.JsonConverter
import timber.log.Timber

@Database(entities = [AuthTokenEntity::class], version = 1, exportSchema = false)
@TypeConverters(value = [JsonConverter::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAuthTokenDao(): AuthTokenDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, Constants.DATABASE_NAME)
                .allowMainThreadQueries()
                .enableMultiInstanceInvalidation()
                .fallbackToDestructiveMigration()
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Timber.d("buildDataclasses")
                    }
                })
                .build()
        }
    }
}