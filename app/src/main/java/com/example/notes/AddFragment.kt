package com.example.notes

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notes.Model.Note
import com.example.notes.ViewModel.AppViewModel
import com.example.notes.databinding.FragmentAddBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddFragment : Fragment() {
    private var _binding:FragmentAddBinding?=null
    private val binding get() = _binding!!

    private val viewModel:AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentAddBinding.inflate(inflater,container,false)
        val view=binding.root

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarAdd)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        var category="UnCategorized"

        binding.category.setOnClickListener {
            category="UnCategorized"
            val toast=Toast.makeText(requireContext(),category,Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
        binding.family.setOnClickListener {
            category="Family Affair"
            val toast=Toast.makeText(requireContext(),category,Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
        binding.personal.setOnClickListener {
            category="Personal"
            val toast=Toast.makeText(requireContext(),category,Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
        binding.work.setOnClickListener {
            category="Work"
            val toast=Toast.makeText(requireContext(),category,Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
        binding.study.setOnClickListener {
            category="Study"
            val toast=Toast.makeText(requireContext(),category,Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }

        binding.btnSave.setOnClickListener {
            val note=binding.note.editText?.text.toString().trim()

            if (TextUtils.isEmpty(note)){
                val toast=Toast.makeText(requireContext(),"Note cannot be empty",Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
            }else{
                addNote(note,category)
            }
        }


        return view
    }
    private fun timeToLong():Long{
        return System.currentTimeMillis()
    }

    private fun addNote(note: String, category: String) {
        val currentdate=timeToLong()
        val note=Note(0,note,category,currentdate,false)
        viewModel.addNote(note)
        Toast.makeText(requireContext(),"Note saved",Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_addFragment_to_homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}