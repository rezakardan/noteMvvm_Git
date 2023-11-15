package com.example.noteappbymvvm.data.repository

import androidx.room.Update
import com.example.noteappbymvvm.data.database.NoteDao
import com.example.noteappbymvvm.data.model.NoteEntity
import javax.inject.Inject

class MainRepository@Inject constructor(private val dao:NoteDao) {


    fun getAllNotes()=dao.getAllNotes()


    fun searchNotes(search:String)=dao.searchNotes(search)

fun filterByPriority(priority:String)=dao.filterBYPriority(priority)



    suspend fun deleteNotes(noteEntity: NoteEntity)=dao.deleteNotes(noteEntity)

    @Update
    suspend fun update(noteEntity: NoteEntity)=dao.updateNotes(noteEntity)




}