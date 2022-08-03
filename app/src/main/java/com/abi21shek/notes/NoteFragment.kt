package com.abi21shek.notes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import java.util.*

private const val ARG_NOTE_ID = "note_id"

class NoteFragment: Fragment() {

    private lateinit var note: Note

    private val noteDetailViewModel: NoteDetailViewModel by lazy {
        ViewModelProviders.of(this).get(NoteDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        note = Note()
        val noteId: UUID = arguments?.getSerializable(ARG_NOTE_ID) as UUID
        noteDetailViewModel.loadNote(noteId)
    }

    private lateinit var title: EditText
    private lateinit var details: EditText
    private lateinit var dateButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)

        title = view.findViewById(R.id.note_title)
        details = view.findViewById(R.id.note_details)
        dateButton = view.findViewById(R.id.note_date)

        dateButton.apply {
            text = note.date.toString()
            isEnabled = false
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteDetailViewModel.noteLiveData.observe(
            viewLifecycleOwner,
            Observer { note ->
                note?.let {
                    this.note = note
                    updateUI()
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        note.title = title.text.toString()
        note.notes = details.text.toString()
        noteDetailViewModel.saveNote(note)
    }

    private fun updateUI(){
        title.setText(note.title)
        details.setText(note.notes)
        dateButton.text = note.date.toString()
    }

    companion object {
        fun newInstance(noteId: UUID): NoteFragment {
            val args = Bundle().apply {
                putSerializable(ARG_NOTE_ID, noteId)
            }
            return NoteFragment().apply {
                arguments = args
            }
        }
    }
}