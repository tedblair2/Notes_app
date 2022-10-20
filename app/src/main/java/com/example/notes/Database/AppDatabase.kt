package com.example.notes.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.Model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun notesdao():NotesDao
}