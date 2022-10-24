package com.example.notes

import android.annotation.SuppressLint
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
import com.example.notes.databinding.FragmentUpdateBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class UpdateFragment : Fragment() {
    private var _binding:FragmentUpdateBinding?=null
    private val binding get() = _binding!!

    private val viewModel:AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentUpdateBinding.inflate(inflater,container,false)
        val view=binding.root

        val note=arguments?.getString("note")
        val id=arguments?.getInt("id")
        val category1=arguments?.getString("category")
        var category=category1
        val dateLong=arguments?.getLong("date")

        binding.note.editText?.setText(note)
        binding.category.setOnClickListener {
            category="UnCategorized"
            val toast= Toast.makeText(requireContext(),category, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
        binding.family.setOnClickListener {
            category="Family Affair"
            val toast= Toast.makeText(requireContext(),category, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
        binding.personal.setOnClickListener {
            category="Personal"
            val toast= Toast.makeText(requireContext(),category, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
        binding.work.setOnClickListener {
            category="Work"
            val toast= Toast.makeText(requireContext(),category, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
        binding.study.setOnClickListener {
            category="Study"
            val toast= Toast.makeText(requireContext(),category, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
        }
        binding.btnUpdate.setOnClickListener {
            val noteTxt=binding.note.editText?.text.toString()
            if (TextUtils.isEmpty(noteTxt)){
                val toast= Toast.makeText(requireContext(),"Note cannot be empty", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER,0,0)
                toast.show()
            }else{
                updateNote(noteTxt,category,id)
            }
        }
        return view
    }
    private fun dateToLong():Long{
        return System.currentTimeMillis()
    }
    private fun updateNote(noteTxt: String, category: String?, id: Int?) {
        val current=dateToLong()
        val note=Note(id!!,noteTxt,category!!,current,false)
        viewModel.updateNote(note)
        val toast= Toast.makeText(requireContext(),"Note updated successfully", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
        findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
    }
}