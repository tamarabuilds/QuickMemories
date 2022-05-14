package com.example.quickmemories

import android.content.ClipData
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quickmemories.data.Child
import com.example.quickmemories.databinding.FragmentAddEditChildBinding
import com.example.quickmemories.model.QuickViewModel
import com.example.quickmemories.model.QuickViewModelFactory

/**
 * A simple [Fragment] subclass to add and edit a child in the Child database.
 */

class AddEditChildFragment : Fragment() {

    // Use the 'by activityViewModels()' Kotlin property delegate from the fragment-ktx artifact
    // to share the ViewModel across fragments.
    private val viewModel: QuickViewModel by activityViewModels{
        QuickViewModelFactory(
            (activity?.application as QuickMemoriesApplication).database
                .childMemoryDao()
        )
    }

    private val navigationArgs: AddEditChildFragmentArgs by navArgs()

    lateinit var child: Child

    private var _binding: FragmentAddEditChildBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddEditChildBinding.inflate(inflater, container, false)

        return binding.root
    }

    /**
     * Returns true if the EditTexts are not empty
     */
    private fun isEntryValidChild(): Boolean {
        return viewModel.isEntryValidChild(
            binding.childNameAdd.text.toString(),
            binding.childDobAdd.text.toString(),
        )
    }


    /**
     * Binds views with the passed in [child] information.
     */
    private fun bind(child: Child) {
        binding.apply {
            childNameAdd.setText(child.childName, TextView.BufferType.SPANNABLE)
            childDobAdd.setText(child.childDob, TextView.BufferType.SPANNABLE)
            buttonSaveChild.setOnClickListener { updateChild() }
        }
    }





    /**
     * Inserts the new Child into database and navigates up to list fragment.
     */
    private fun addNewChild() {
        if (isEntryValidChild()) {
            viewModel.addNewChild(
                binding.childNameAdd.text.toString(),
                binding.childDobAdd.text.toString(),
            )
            val action = AddEditChildFragmentDirections.actionAddEditChildFragmentToChildListFragment()
            findNavController().navigate(action)
        }
    }

    /**
     * Updates an existing Item in the database and navigates up to list fragment.
     */
    private fun updateChild() {
        if (isEntryValidChild()) {
            viewModel.updateChild(
                this.navigationArgs.childId,
                this.binding.childNameAdd.text.toString(),
                this.binding.childDobAdd.text.toString()
            )
            val action = AddEditChildFragmentDirections.actionAddEditChildFragmentToChildListFragment()
            findNavController().navigate(action)
        }
    }




    /**
     * Called when the view is created.
     * The childId Navigation argument determines the edit item or add new item.
     * If the childId is positive, this method retrieves the information from the database and
     * allows the user to update it.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.childId
        if (id > 0) {
            viewModel.retrieveChild(id).observe(this.viewLifecycleOwner) { selectedId ->
                child = selectedId
                bind(child)
            }
        } else {
            binding.buttonSaveChild.setOnClickListener {
                addNewChild()
            }
        }
    }



    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }


}
