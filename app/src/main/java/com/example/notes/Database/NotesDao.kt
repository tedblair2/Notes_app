package com.example.notes.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notes.Model.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(note:Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY date DESC")
    fun getNotes():LiveData<List<Note>>

    @Query("SELECT * FROM notes_table ORDER BY id ASC")
    fun arrangeId():LiveData<List<Note>>

    @Query("DELETE FROM notes_table")
    suspend fun deleteAllNotes()

    @Delete
    suspend fun deleteNote(note: Note)
}