package com.example.retrofitsamplewithcleanarchitecture.di

import android.content.Context
import androidx.room.Room
import com.example.retrofitsamplewithcleanarchitecture.data.local.AppDatabase
import com.example.retrofitsamplewithcleanarchitecture.data.local.model.AuthTokenEntityMapper
import com.example.retrofitsamplewithcleanarchitecture.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAuthTokenEntityMapper(): AuthTokenEntityMapper
            = AuthTokenEntityMapper()

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideAuthTokenDao(db: AppDatabase) = db.getAuthTokenDao()

}