package com.example.noteappbymvvm.data.repository

import com.example.noteappbymvvm.data.database.NoteDao
import com.example.noteappbymvvm.data.model.NoteEntity
import javax.inject.Inject

class NoteRepository@Inject constructor(private val dao:NoteDao) {

suspend fun saveNotes(noteEntity: NoteEntity)=dao.saveNotes(noteEntity)


    suspend fun updateNote(noteEntity: NoteEntity)=dao.updateNotes(noteEntity)


    fun getOneNoteById(id:Int)=dao.getOneNotesById(id)

}