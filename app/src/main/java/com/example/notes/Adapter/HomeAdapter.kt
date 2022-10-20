package com.example.notes.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.Model.Note
import com.example.notes.R
import com.example.notes.ViewModel.AppViewModel
import com.example.notes.databinding.HomeItemsBinding
import java.text.SimpleDateFormat
import java.util.*

class HomeAdapter(private val noteslist:List<Note>,private val context: Context)
    :RecyclerView.Adapter<HomeAdapter.ViewHolder>(){

    class ViewHolder(val binding:HomeItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=HomeItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note=noteslist[position]

        val date=longToDate(note.date)
        val category=note.category
        holder.binding.noteData.text=note.note
        holder.binding.category.text=category
        holder.binding.date.text=date
        when (category) {
            "Study" -> {
                holder.binding.category.setTextColor(ContextCompat.getColor(context, R.color.blue))
            }
            "Family Affair" -> {
                holder.binding.category.setTextColor(ContextCompat.getColor(context, R.color.red))
            }
            "Personal" -> {
                holder.binding.category.setTextColor(ContextCompat.getColor(context, R.color.yellow))
            }
            "Work" -> {
                holder.binding.category.setTextColor(ContextCompat.getColor(context, R.color.purple))
            }
            else -> {
                holder.binding.category.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
        }
        holder.itemView.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("note",note.note)
            bundle.putString("category",note.category)
            bundle.putLong("date",note.date)
            bundle.putInt("id",note.id)
            holder.itemView.findNavController().navigate(R.id.action_homeFragment_to_updateFragment,bundle)
        }
    }
    override fun getItemCount(): Int {
        return noteslist.size
    }
    @SuppressLint("SimpleDateFormat")
    private fun longToDate(time:Long):String{
        val date=Date(time)
        val format=SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }
//    private fun selectItem(holder: ViewHolder, note: Note) {
//        if (selectedItems.contains(note)){
//            holder.itemView.setBackgroundColor(Color.WHITE)
//            selectedItems.remove(note)
//            holder.binding.checked.visibility=View.GONE
//            val item=Note(note.id,note.note,note.category,note.date,false)
//            viewModel.updateNote(item)
//        }else{
//            holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.grey))
//            holder.binding.checked.visibility=View.VISIBLE
//            selectedItems.add(note)
//            val item=Note(note.id,note.note,note.category,note.date,true)
//            viewModel.updateNote(item)
//        }
//        if (selectedItems.isEmpty()){
//            multiselect=false
//            showmenu(false)
//            holder.binding.checked.visibility=View.GONE
//        }
//    }
}