package com.example.noteappbymvvm.ui.main.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.example.noteappbymvvm.R
import com.example.noteappbymvvm.data.model.NoteEntity
import com.example.noteappbymvvm.databinding.FragmentNoteBinding
import com.example.noteappbymvvm.util.*
import com.example.noteappbymvvm.util.di.*
import com.example.noteappbymvvm.viewmodel.NoteViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NoteFragment : BottomSheetDialogFragment() {

    private var mainBinding: FragmentNoteBinding? = null


    private val viewModel: NoteViewModel by viewModels()



    lateinit var categoryList:MutableList<String>

    lateinit var priorityList:MutableList<String>

    private val binding get() = mainBinding

    var category = ""
    var priority = ""

    var noteId = 0

    var type = ""
    @Inject
    lateinit var adapter:NoteAdapter


    @Inject
    lateinit var entity: NoteEntity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mainBinding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       noteId=arguments?.getInt(BUNDLE_ID)?:0

        if (noteId>0){


         type=   EDIT


        }else{

          type=  NEW
        }









        viewModel.categorySpinner()
        viewModel.categoryList.observe(viewLifecycleOwner) {


            spinnerCategory(it)


        }


        viewModel.prioritySpinner()
        viewModel.priorityList.observe(viewLifecycleOwner) {

            prioritySpinner(it)


        }

        binding?.imgClose?.setOnClickListener { dismiss() }
        binding?.saveNote?.setOnClickListener {


            val title = binding?.edtTitle?.text.toString()
            val desc = binding?.edtDesc?.text.toString()


            entity.id = noteId
            entity.title = title
            entity.desc = desc
            entity.priority = priority
            entity.category = category

            if (title.isNotEmpty() && desc.isNotEmpty()) {

                if (type == NEW) {

                    viewModel.saveOrEditNotes(entity, false)

                } else {

                    viewModel.saveOrEditNotes(entity, true)
                }


            }

            dismiss()


        }




        if (type== EDIT){

         viewModel.getOneNoteById(noteId)

            viewModel.getNoteById.observe(viewLifecycleOwner){


                it?.let {

                    binding?.edtTitle?.setText(it.title)
                    binding?.edtDesc?.setText(it.desc)

                    binding?.categorySpinner?.setSelection(getIndex(categoryList,it.category))

                    binding?.prioritySpinner?.setSelection(getIndex(priorityList,it.priority))



                }




            }


        }







    }







    private fun spinnerCategory(data: MutableList<String>) {


        categoryList = data

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categoryList)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        binding?.categorySpinner?.adapter = adapter
        binding?.categorySpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                    category = categoryList[p2]


                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }


            }


    }


    private fun prioritySpinner(data: MutableList<String>) {

        priorityList = data

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, priorityList)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.prioritySpinner?.adapter = adapter
        binding?.prioritySpinner?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    priority = priorityList[p2]
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }


            }


    }



    private fun getIndex(list: MutableList<String>,item:String):Int{



        var index=0
        for (i in list.indices){

            if (list[i]==item){

             index=i

                break
            }



        }

        return index


    }


    override fun onStop() {
        super.onStop()

        mainBinding = null
    }


}