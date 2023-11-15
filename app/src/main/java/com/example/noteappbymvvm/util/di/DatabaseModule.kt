package com.example.noteappbymvvm.util.di

import android.content.Context
import androidx.room.Room
import com.example.noteappbymvvm.data.database.NoteDatabase
import com.example.noteappbymvvm.data.model.NoteEntity
import com.example.noteappbymvvm.util.NOTE_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, NoteDatabase::class.java,
        NOTE_DATABASE
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun noteDao(db: NoteDatabase) = db.noteDao()


    @Provides
    @Singleton
    fun provideEntity() = NoteEntity()


}