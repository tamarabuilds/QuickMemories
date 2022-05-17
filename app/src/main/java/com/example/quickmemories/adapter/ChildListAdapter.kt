package com.example.quickmemories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quickmemories.data.Child
import com.example.quickmemories.databinding.ChildListChildBinding


// List Adapter for the recyclerview

class ChildListAdapter(private val onChildClicked: (Child) -> Unit) :
    ListAdapter<Child, ChildListAdapter.ChildViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        return ChildViewHolder(
            ChildListChildBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onChildClicked(current)
        }
        holder.bind(current)
    }

    class ChildViewHolder( var binding: ChildListChildBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(child: Child) {
                binding.childName.text = child.childName
                binding.childDob.text = child.childDob.toString()
            }
        }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Child>() {
            override fun areItemsTheSame(oldItem: Child, newItem: Child): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Child, newItem: Child): Boolean {
                return oldItem.childName == newItem.childName
            }
        }
    }
}