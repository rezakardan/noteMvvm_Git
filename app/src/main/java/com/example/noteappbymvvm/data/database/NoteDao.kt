package com.example.noteappbymvvm.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.noteappbymvvm.data.model.NoteEntity
import com.example.noteappbymvvm.util.NOTE_TABLE
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun saveNotes(noteEntity: NoteEntity)

@Update
suspend fun updateNotes(noteEntity: NoteEntity)

@Delete
suspend fun deleteNotes(noteEntity: NoteEntity)


@Query("SELECT* FROM $NOTE_TABLE")
fun getAllNotes():Flow<MutableList<NoteEntity>>


@Query("SELECT* FROM $NOTE_TABLE WHERE title LIKE '%' ||:search||'%'")
fun searchNotes(search:String):Flow<MutableList<NoteEntity>>


@Query("SELECT* FROM $NOTE_TABLE WHERE id==:id")
fun getOneNotesById(id:Int):Flow<NoteEntity>


@Query("SELECT * FROM $NOTE_TABLE WHERE priority==:priority")
fun filterBYPriority(priority:String):Flow<MutableList<NoteEntity>>

}