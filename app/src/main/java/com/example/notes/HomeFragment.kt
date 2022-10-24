package com.example.notes

import android.app.AlertDialog
import android.app.Notification
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.Adapter.HomeAdapter
import com.example.notes.Model.LongPress
import com.example.notes.Model.Note
import com.example.notes.ViewModel.AppViewModel
import com.example.notes.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),LongPress {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!
    lateinit var adapter: HomeAdapter
    private var actionMode:ActionMode?=null
    lateinit var itemlist:ArrayList<Note>
    lateinit var selectedlist:ArrayList<Note>

    private val viewModel:AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentHomeBinding.inflate(inflater,container,false)
        val view=binding.root

        itemlist= arrayListOf()
        selectedlist= arrayListOf()

        binding.recyclerNotes.setHasFixedSize(true)
        binding.recyclerNotes.layoutManager=LinearLayoutManager(requireContext())
        viewModel.arrangebyId.observe(viewLifecycleOwner, Observer { notes->
            itemlist.addAll(notes)
            adapter= HomeAdapter(notes,requireContext(),this)
            binding.recyclerNotes.adapter=adapter
            adapter.notifyDataSetChanged()
        })
        val menuHost:MenuHost=requireActivity()
        menuHost.addMenuProvider(object:MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu,menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when(menuItem.itemId){
                    R.id.sortbyid->{
                        viewModel.arrangebyId.observe(viewLifecycleOwner, Observer { notes->
                            adapter= HomeAdapter(notes,requireContext(),this@HomeFragment)
                            binding.recyclerNotes.adapter=adapter
                            adapter.notifyDataSetChanged()
                        })
                        true
                    }
                    R.id.sort->{
                        viewModel.notes.observe(viewLifecycleOwner, Observer { notes->
                            adapter= HomeAdapter(notes,requireContext(),this@HomeFragment)
                            binding.recyclerNotes.adapter=adapter
                            adapter.notifyDataSetChanged()
                        })
                        true
                    }
                    R.id.delete->{
                        deleteAll()
                        true
                    }
                    else->false
                }
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)

        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
        return view
    }

    private fun deleteAll() {
        val alert=AlertDialog.Builder(requireContext())
        alert.setTitle("Delete Notes")
        alert.setMessage("Are you sure you want to delete all notes?")
        alert.setPositiveButton("Yes"){_,_->
            viewModel.deleteAllNotes()
            Toast.makeText(requireContext(),"All notes deleted",Toast.LENGTH_LONG).show()
        }
        alert.setNegativeButton("No"){_,_->
        }
        alert.create().show()
    }
    private val actionCallback=object :ActionMode.Callback{
        override fun onCreateActionMode(p0: ActionMode, p1: Menu): Boolean {
            p0.menuInflater.inflate(R.menu.delete_item,p1)
            return true
        }

        override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
            return false
        }

        override fun onActionItemClicked(p0: ActionMode, p1: MenuItem): Boolean {
            return when(p1.itemId){
                R.id.deleteSpecific->{
                    deleteItem()
                    true
                }
                else->false
            }
        }

        override fun onDestroyActionMode(p0: ActionMode?) {
            selectedlist.clear()
            adapter.selectedItems.clear()
            adapter.notifyDataSetChanged()
            actionMode=null
        }

    }

    private fun deleteItem() {
        val alert=AlertDialog.Builder(requireContext())
        alert.setTitle("Delete")
        alert.setMessage("Do you want to delete these items?")
        alert.setPositiveButton("Yes"){_,_->
            for (note in selectedlist){
                viewModel.deleteNote(note)
                adapter.notifyDataSetChanged()
            }
            val total=selectedlist.size.toString()
            Snackbar.make(binding.recyclerNotes,"Deleted $total items",Snackbar.LENGTH_LONG).show()
            selectedlist.clear()
            adapter.selectedItems.clear()
            actionMode?.finish()
        }
        alert.setNegativeButton("No"){_,_->
            selectedlist.clear()
            adapter.selectedItems.clear()
            adapter.notifyDataSetChanged()
            actionMode=null
        }
        alert.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

    override fun longPress(position: Int) {
        if (actionMode==null){
            actionMode=activity?.startActionMode(actionCallback)
        }
        val note=itemlist[position]
        if (selectedlist.contains(note)){
            selectedlist.remove(note)
        }else{
            selectedlist.add(note)
        }
        val total=selectedlist.size.toString()
        actionMode?.title=total
    }
}