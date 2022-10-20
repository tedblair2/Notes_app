package com.example.notes.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val note:String,
    val category:String,
    val date:Long,
    val isSelected:Boolean
)
