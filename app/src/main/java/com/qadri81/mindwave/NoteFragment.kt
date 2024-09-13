package com.qadri81.mindwave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.qadri81.mindwave.databinding.FragmentNoteBinding
import com.qadri81.mindwave.models.NoteRequest
import com.qadri81.mindwave.models.NoteResponse
import com.qadri81.mindwave.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private var note: NoteResponse? = null

    private val noteViewModel by viewModels<NoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
        bindHandlers()
        bindObservers()
    }

    private fun bindObservers() {
        noteViewModel.statusLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {

                }

                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun bindHandlers() {
        binding.btnDelete.setOnClickListener {
            note?.let {
                noteViewModel.deleteNote(it._id)
            }
        }

        binding.btnSubmit.setOnClickListener {
            val title = binding.txtTitle.text.toString()
            val description = binding.txtDescription.text.toString()

            val noteRequest = NoteRequest(description, title)

            if (note == null) {
                noteViewModel.createNote(noteRequest)
            } else {
                noteViewModel.updateNote(noteId = note!!._id, noteRequest)
            }
        }
    }

    private fun setInitialData() {
        val jsonNote = arguments?.getString("note")
        if (jsonNote != null) {
            note = Gson().fromJson(jsonNote, NoteResponse::class.java)
            note?.let {
                binding.txtTitle.setText(it.title)
                binding.txtDescription.setText(it.description)
            }
        } else {
            binding.addEditText.text = "Add note"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}