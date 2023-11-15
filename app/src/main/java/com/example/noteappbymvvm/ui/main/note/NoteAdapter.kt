package com.example.noteappbymvvm.ui.main.note

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteappbymvvm.R
import com.example.noteappbymvvm.data.model.NoteEntity
import com.example.noteappbymvvm.databinding.ItemNotesBinding
import com.example.noteappbymvvm.util.*
import javax.inject.Inject

class NoteAdapter @Inject constructor() : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    lateinit var binding: ItemNotesBinding

    var noteList = emptyList<NoteEntity>()

    lateinit var context: Context

    inner class NoteViewHolder(item: View) : RecyclerView.ViewHolder(item) {


        fun onBind(oneItem: NoteEntity) {

            binding.txtTitle.text = oneItem.title
            binding.txtDesc.text = oneItem.desc



            when (oneItem.category) {

                WORK -> {

                    binding.categoryImg.setImageResource(R.drawable.work)


                }

                HOME -> {
                    binding.categoryImg.setImageResource(R.drawable.home)
                }

                HEALTH -> {
                    binding.categoryImg.setImageResource(R.drawable.healthcare)
                }

                EDUCATION -> {
                    binding.categoryImg.setImageResource(R.drawable.education)
                }


            }






            when (oneItem.priority) {


                HIGH -> {
                    binding.priorityState.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.red
                        )
                    )
                }
                NORMAL -> {
                    binding.priorityState.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.yellow
                        )
                    )

                }

                LOW -> {
                    binding.priorityState.setBackgroundColor(
                        ContextCompat.getColor(
                            context,
                            R.color.aqua
                        )
                    )
                }

            }




            binding.menuImg.setOnClickListener {

                val popUpMenu = PopupMenu(context, it)

                popUpMenu.menuInflater.inflate(R.menu.menu_items, popUpMenu.menu)

                popUpMenu.show()


                popUpMenu.setOnMenuItemClickListener {

                    when (it.itemId) {

                        R.id.itemDelete -> {

                            onItemClick?.let {

                                it(oneItem, DELETE)


                            }


                        }

                        R.id.itemEdit -> {

                            onItemClick?.let {

                                it(oneItem, EDIT)
                            }
                        }

                    }






                    return@setOnMenuItemClickListener true
                }


            }


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        context = parent.context
        binding = ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder((binding.root))
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(noteList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = noteList.size


    private var onItemClick: ((NoteEntity, String) -> Unit)? = null

    fun setOnItemClickListener(listener: (NoteEntity, String) -> Unit) {


        onItemClick = listener


    }


    fun setData(data: List<NoteEntity>) {

        val noteDiffUtils = NoteDiffUtils(noteList, data)

        val diff = DiffUtil.calculateDiff(noteDiffUtils)

        noteList = data

        diff.dispatchUpdatesTo(this)


    }

    class NoteDiffUtils(val oldItem: List<NoteEntity>, val newItem: List<NoteEntity>) :
        DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }


    }


}