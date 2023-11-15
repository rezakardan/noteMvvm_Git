package com.example.noteappbymvvm.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteappbymvvm.R
import com.example.noteappbymvvm.data.model.NoteEntity
import com.example.noteappbymvvm.databinding.ActivityMainBinding
import com.example.noteappbymvvm.ui.main.note.NoteAdapter
import com.example.noteappbymvvm.ui.main.note.NoteFragment
import com.example.noteappbymvvm.util.*
import com.example.noteappbymvvm.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var mainBinding: ActivityMainBinding? = null

    private val binding get() = mainBinding

    private val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var adapter: NoteAdapter

    @Inject
    lateinit var entity: NoteEntity

    var selectedItem=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

setSupportActionBar(binding?.toolbar)

        binding?.btnaddNote?.setOnClickListener {

            NoteFragment().show(supportFragmentManager, NoteFragment().tag)


        }


        viewModel.getAllNotes()

        viewModel.allNotes.observe(this@MainActivity) {

            adapter.setData(it)

            binding?.recyclerMain?.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            binding?.recyclerMain?.adapter = adapter


        }

        viewModel.emptyList.observe(this@MainActivity) {

            if (it) {

                binding?.recyclerMain?.visibility = View.GONE
                binding?.emptyLayout?.visibility = View.VISIBLE
            } else {

                binding?.recyclerMain?.visibility = View.VISIBLE
                binding?.emptyLayout?.visibility = View.GONE


            }


        }


binding?.toolbar?.setOnMenuItemClickListener { menuItem->

    when(menuItem.itemId){

        R.id.actionFilter->{

filterByPriority()
            return@setOnMenuItemClickListener true

        }else->{

            return@setOnMenuItemClickListener false

        }


    }






}




        adapter.setOnItemClickListener { noteEntity, state ->


            when(state){

                DELETE->{

            entity.id=noteEntity.id
            entity.priority=noteEntity.priority
            entity.category=noteEntity.category
            entity.desc=noteEntity.desc
            entity.title=noteEntity.title

                  viewModel.deleteNote(entity,false)



                }

                EDIT->{
val noteFragment=NoteFragment()

                    val bundle=Bundle()
                    bundle.putInt(BUNDLE_ID,noteEntity.id)

                    noteFragment.arguments=bundle

                    noteFragment.show(supportFragmentManager,NoteFragment().tag)


                }





            }





        }




    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_toolbar, menu)

        val search = menu.findItem(R.id.actionSearch)

        val searchView = search.actionView as SearchView

        searchView.queryHint = getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                viewModel.searchNotes(newText)

                viewModel.searchList.observe(this@MainActivity) {




                        adapter.setData(it)

                        binding?.recyclerMain?.layoutManager = LinearLayoutManager(
                            this@MainActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        binding?.recyclerMain?.adapter = adapter







                }




                return true
            }

        })




        return super.onCreateOptionsMenu(menu)
    }







    private fun filterByPriority(){

        val builder=AlertDialog.Builder(this)
        val priorityList= arrayOf(ALL, HIGH, NORMAL, LOW)
        builder.setSingleChoiceItems(priorityList,selectedItem){dialog,item->


            when(item){

                0->{
                    viewModel.getAllNotes()
                }

                in 1..3->{


                  viewModel.filterByPriority(priorityList[item])

                    viewModel.filterPriority.observe(this@MainActivity){

                        adapter.setData(it)
                        binding?.recyclerMain?.layoutManager=LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
                        binding?.recyclerMain?.adapter=adapter








                    }


                }



            }

             selectedItem=item
            dialog.dismiss()






        }

        val dialog:AlertDialog=builder.create()

        dialog.show()





    }

    override fun onDestroy() {
        super.onDestroy()

        mainBinding = null

    }
}