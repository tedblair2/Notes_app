package com.example.notes.Repository

import androidx.lifecycle.LiveData
import com.example.notes.Database.NotesDao
import com.example.notes.Model.Note
import javax.inject.Inject

class AppRepository @Inject constructor(private val notesDao: NotesDao) {

    suspend fun addNote(note: Note)=notesDao.addNote(note)

    val notes:LiveData<List<Note>> = notesDao.getNotes()
    val arrangebyId:LiveData<List<Note>> = notesDao.arrangeId()

    suspend fun updateNote(note: Note)=notesDao.updateNote(note)

    suspend fun deleteNote(note: Note)=notesDao.deleteNote(note)

    suspend fun deleteAllNotes()=notesDao.deleteAllNotes()

}