package com.example.notes.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.Model.Note
import com.example.notes.Repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repository: AppRepository):ViewModel() {

    val notes:LiveData<List<Note>> =repository.notes
    val arrangebyId:LiveData<List<Note>>

    init {
        arrangebyId=repository.arrangebyId
    }

    fun addNote(note: Note)=viewModelScope.launch(Dispatchers.IO) {
        repository.addNote(note)
    }

    fun updateNote(note: Note)=viewModelScope.launch(Dispatchers.IO) {
        repository.updateNote(note)
    }

    fun deleteNote(note: Note)=viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(note)
    }

    fun deleteAllNotes()=viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllNotes()
    }

}