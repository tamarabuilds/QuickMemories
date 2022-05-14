package com.example.quickmemories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quickmemories.adapter.ChildListAdapter
import com.example.quickmemories.databinding.FragmentChildListBinding
import com.example.quickmemories.model.QuickViewModel
import com.example.quickmemories.model.QuickViewModelFactory

/**
 * A simple [Fragment] displaying all the children
 */
class ChildListFragment : Fragment() {
    private val viewModel: QuickViewModel by activityViewModels{
        QuickViewModelFactory(
            (this.activity?.application as QuickMemoriesApplication).database.childMemoryDao()
        )
    }

    private var _binding: FragmentChildListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChildListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ChildListAdapter {
            val action =
                ChildListFragmentDirections.actionChildListFragmentToChildDetailFragment(it.childId)
            this.findNavController().navigate(action)
        }
        binding.recyclerViewChildList.layoutManager = LinearLayoutManager(this.context)
        binding.recyclerViewChildList.adapter = adapter
        // Attach an observer on the child list to update the UI automatically when the data
        // changes.
        viewModel.allChildren.observe(this.viewLifecycleOwner) { children ->
            children.let {
                adapter.submitList(it)
            }
        }


        binding.buttonAddChild.setOnClickListener {
            val action = ChildListFragmentDirections.actionChildListFragmentToAddEditChildFragment(
                getString(R.string.add_child), -1
            )
            this.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

