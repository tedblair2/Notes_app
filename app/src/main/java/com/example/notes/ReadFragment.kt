package com.example.notes

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.notes.Model.Note
import com.example.notes.ViewModel.AppViewModel
import com.example.notes.databinding.FragmentReadBinding
import com.example.notes.databinding.FragmentUpdateBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ReadFragment : Fragment() {
    private var _binding:FragmentReadBinding?=null
    private val binding get() = _binding!!

    private val viewModel:AppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentReadBinding.inflate(inflater,container,false)
        val view=binding.root

        val menuHost:MenuHost=requireActivity()

        val note=arguments?.getString("note")
        val category=arguments?.getString("category")
        val dateLong=arguments?.getLong("date")
        val id=arguments?.getInt("id")
        val date=longToDate(dateLong!!)

        binding.click.setOnClickListener {
            val bundle=Bundle()
            bundle.putString("note",note)
            bundle.putString("category",category)
            bundle.putLong("date",dateLong)
            bundle.putInt("id", id!!)
            findNavController().navigate(R.id.action_readFragment_to_updateFragment,bundle)
        }

        binding.categoryTxt.text=category
        binding.date.text=date
        binding.notes.text=note
        when (category) {
            "Study" -> {
                binding.categoryTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
            }
            "Family Affair" -> {
                binding.categoryTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
            }
            "Personal" -> {
                binding.categoryTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.yellow))
            }
            "Work" -> {
                binding.categoryTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.purple))
            }
            else -> {
                binding.categoryTxt.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
            }
        }
        return view
    }

    @SuppressLint("SimpleDateFormat")
    private fun longToDate(time:Long):String{
        val date=Date(time)
        val format=SimpleDateFormat("yyyy.MM.dd")
        return format.format(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }

}