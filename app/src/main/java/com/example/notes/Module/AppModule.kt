package com.example.notes.Module

import android.content.Context
import androidx.room.Room
import com.example.notes.Database.AppDatabase
import com.example.notes.Database.NotesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideDao(appDatabase: AppDatabase):NotesDao{
        return appDatabase.notesdao()
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "notes_database2").build()
    }
}