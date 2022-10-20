package com.example.notes

import android.app.AlertDialog
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
import com.example.notes.Model.Note
import com.example.notes.ViewModel.AppViewModel
import com.example.notes.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!
    lateinit var adapter: HomeAdapter

    private val viewModel:AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentHomeBinding.inflate(inflater,container,false)
        val view=binding.root

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.recyclerNotes.setHasFixedSize(true)
        binding.recyclerNotes.layoutManager=LinearLayoutManager(requireContext())
        viewModel.arrangebyId.observe(viewLifecycleOwner, Observer { notes->
            adapter= HomeAdapter(notes,requireContext())
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
                            adapter= HomeAdapter(notes,requireContext())
                            binding.recyclerNotes.adapter=adapter
                            adapter.notifyDataSetChanged()
                        })
                        true
                    }
                    R.id.sort->{
                        viewModel.notes.observe(viewLifecycleOwner, Observer { notes->
                            adapter= HomeAdapter(notes,requireContext())
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}