package com.sowmya.contactmanagementapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sowmya.contactmanagementapp.R
import com.sowmya.contactmanagementapp.data.model.Contact
import com.sowmya.contactmanagementapp.databinding.ItemContactBinding

class ContactAdapter(private val onContactClick: (Contact) -> Unit) :
    ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)
        holder.bind(contact)
    }

    inner class ContactViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            binding.tvContactName.text = contact.name
            binding.tvContactPhone.text = contact.phone
            
            // Handle profile image
            Glide.with(binding.ivContactProfile.context)
                .load(contact.imagePath)
                .placeholder(android.R.drawable.ic_menu_myplaces)
                .error(android.R.drawable.ic_menu_myplaces)
                .into(binding.ivContactProfile)

            // Handle favorite icon
            if (contact.isFavorite) {
                binding.ivFavorite.setImageResource(android.R.drawable.star_big_on)
            } else {
                binding.ivFavorite.setImageResource(android.R.drawable.star_big_off)
            }

            binding.root.setOnClickListener { onContactClick(contact) }
        }
    }

    class ContactDiffCallback : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean =
            oldItem == newItem
    }
}
