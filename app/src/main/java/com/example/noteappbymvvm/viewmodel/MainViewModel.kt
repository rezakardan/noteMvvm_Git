package com.example.noteappbymvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteappbymvvm.data.model.NoteEntity
import com.example.noteappbymvvm.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(private val repo:MainRepository):ViewModel() {

val allNotes=MutableLiveData<MutableList<NoteEntity>>()

    val emptyList=MutableLiveData<Boolean>()

    val searchList=MutableLiveData<MutableList<NoteEntity>>()


    val filterPriority=MutableLiveData<MutableList<NoteEntity>>()

    fun getAllNotes()=viewModelScope.launch{

        repo.getAllNotes().collect{


            if (it.isNotEmpty()){

                allNotes.postValue(it)
emptyList.postValue(false)

            }else{

                emptyList.postValue(true)
            }



        }




    }



    fun searchNotes(search:String)=viewModelScope.launch {

        repo.searchNotes(search).collect{


            if (it.isNotEmpty()){
                searchList.postValue(it)
emptyList.postValue(false)


            }else{


                emptyList.postValue(true)
            }







        }


    }


    fun filterByPriority(priority:String)=viewModelScope.launch {

        repo.filterByPriority(priority).collect{

            if (it.isNotEmpty()){

                filterPriority.postValue(it)
                emptyList.postValue(false)


            }else{

                emptyList.postValue(true)
            }





        }




    }




    fun deleteNote(noteEntity: NoteEntity,isEditing:Boolean)=viewModelScope.launch {

        if (isEditing){

            repo.update(noteEntity)


        }else{

            repo.deleteNotes(noteEntity)

        }




    }






}