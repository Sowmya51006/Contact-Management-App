package com.sowmya.contactmanagementapp.ui.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.sowmya.contactmanagementapp.data.model.Contact
import com.sowmya.contactmanagementapp.databinding.FragmentAddContactBinding
import com.sowmya.contactmanagementapp.viewmodel.ContactViewModel
import kotlinx.coroutines.launch

class EditContactFragment : Fragment() {
    private var _binding: FragmentAddContactBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContactViewModel by viewModels()
    private var imageUri: String? = null

    private val pickImage = registerForActivityResult(androidx.activity.result.contract.ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            imageUri = it.toString()
            binding.ivProfile.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contactId = arguments?.getInt(ARG_CONTACT_ID) ?: -1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHeader.text = "Edit Contact"
        loadContact()

        binding.ivProfile.setOnClickListener {
            pickImage.launch("image/*")
        }

        binding.btnSave.setOnClickListener { updateContact() }
    }

    private fun loadContact() {
        lifecycleScope.launch {
            currentContact = viewModel.getContactById(contactId)
            currentContact?.let {
                binding.etName.setText(it.name)
                binding.etPhone.setText(it.phone)
                binding.etEmail.setText(it.email)
                imageUri = it.imagePath
                
                Glide.with(this@EditContactFragment)
                    .load(it.imagePath)
                    .placeholder(android.R.drawable.ic_menu_camera)
                    .into(binding.ivProfile)
            }
        }
    }

    private fun updateContact() {
        val name = binding.etName.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(context, "Please fill required fields", Toast.LENGTH_SHORT).show()
            return
        }

        currentContact?.let {
            val updated = it.copy(name = name, phone = phone, email = email, imagePath = imageUri)
            viewModel.update(updated)
            Toast.makeText(context, "Contact Updated", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
