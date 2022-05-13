package com.example.quickmemories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.quickmemories.data.Child
import com.example.quickmemories.model.QuickViewModel
import com.example.quickmemories.model.QuickViewModelFactory
import androidx.navigation.fragment.findNavController
import com.example.quickmemories.databinding.FragmentChildDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * A simple [Fragment] subclass.
 * Use the [ChildDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChildDetailFragment : Fragment() {
    private val navigationArgs: ChildDetailFragmentArgs by navArgs()
    lateinit var child: Child

    private val viewModel: QuickViewModel by activityViewModels{
        QuickViewModelFactory(
            (activity?.application as QuickMemoriesApplication).database.childMemoryDao()
        )
    }

    private var _binding: FragmentChildDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentChildDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * Binds views with the passed in item data.
     */
    private fun bind(child: Child) {
        binding.apply {
            childNameDetail.text = child.childName
            childDobDetail.text = child.childDob
            buttonDeleteChild.setOnClickListener { showConfirmationDialog() }
            floatingActionButtonEditChild.setOnClickListener { editChild() }
        }
    }

    /**
     * Navigate to the Edit Child screen.
     */
    private fun editChild() {
        val action = ChildDetailFragmentDirections.actionChildDetailFragmentToAddChildFragment(
           // child.childName
        )
        this.findNavController().navigate(action)
    }

    /**
     * Displays an alert dialog to get the user's confirmation before deleting the child.
     */
    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteChild()
            }
            .show()
    }

    /**
     * Deletes the current child and navigates to the child list fragment.
     */
    private fun deleteChild() {
        viewModel.deleteChild(child)
        findNavController().navigateUp()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val childId = navigationArgs.childId
        // Retrieve the child details using the childId.
        // Attach an observer on the data (instead of polling for changes) and only update the
        // the UI when the data actually changes.
        viewModel.retrieveChild(childId = childId).observe(this.viewLifecycleOwner) { selectedItem ->
            child = selectedItem
            bind(child)
        }
    }

    /**
     * Called when fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





}

/**
 *     override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
arguments?.let {
param1 = it.getString(ARG_PARAM1)
param2 = it.getString(ARG_PARAM2)
}
}
 */