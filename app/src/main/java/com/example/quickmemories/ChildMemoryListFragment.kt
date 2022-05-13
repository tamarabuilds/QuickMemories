package com.example.quickmemories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quickmemories.databinding.FragmentChildMemoryListBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ChildMemoryListFragment : Fragment() {

    private var _binding: FragmentChildMemoryListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentChildMemoryListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //  binding.floatingActionButton.setOnClickListener {
      //      findNavController().navigate(R.id.addMemoryFragment)
      //  }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}