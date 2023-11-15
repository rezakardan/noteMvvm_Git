package com.example.noteappbymvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappbymvvm.data.model.NoteEntity
import com.example.noteappbymvvm.data.repository.NoteRepository
import com.example.noteappbymvvm.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel@Inject constructor(private val repo:NoteRepository):ViewModel() {



    val categoryList=MutableLiveData<MutableList<String>>()

    val priorityList=MutableLiveData<MutableList<String>>()

    val getNoteById=MutableLiveData<NoteEntity>()



    fun categorySpinner(){

        val list= mutableListOf(WORK, HOME, EDUCATION, HEALTH)

        categoryList.postValue(list)




    }


    fun prioritySpinner(){


        val list= mutableListOf(HIGH, NORMAL, LOW)

        priorityList.postValue(list)

    }


    fun saveOrEditNotes(noteEntity: NoteEntity,isEditing:Boolean)=viewModelScope.launch {


        if (isEditing){

            repo.updateNote(noteEntity)


        }else{


            repo.saveNotes(noteEntity)

        }



    }




 fun getOneNoteById(id:Int)=viewModelScope.launch {


     repo.getOneNoteById(id).collect{

         getNoteById.postValue(it)




     }


 }







}