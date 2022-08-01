package com.abi21shek.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "NoteListFragment"

class NoteListFragment: Fragment() {

    private lateinit var noteRecyclerView: RecyclerView
    private var adapter: NoteAdapter? = null

    private val noteListViewModel: NoteListViewModel by lazy {
        ViewModelProviders.of(this).get(NoteListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total Notes: ${noteListViewModel.notes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        noteRecyclerView = view.findViewById(R.id.note_recycler_view) as RecyclerView
        noteRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    private fun updateUI(){
        val notes = noteListViewModel.notes
        adapter = NoteAdapter(notes)
        noteRecyclerView.adapter = adapter
    }

    private inner class NoteHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var note: Note

        private val noteTitle: TextView = itemView.findViewById(R.id.note_item_title)
        private val noteDetails: TextView = itemView.findViewById(R.id.note_item_details)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(note: Note){
            this.note = note
            noteTitle.text = note.title
            noteDetails.text = note.notes
        }

        override fun onClick(v: View){
            Toast.makeText(context, "${note.title} pressed!", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class NoteAdapter(var notes: List<Note>): RecyclerView.Adapter<NoteHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
            val view = layoutInflater.inflate(R.layout.list_item_note, parent, false)
            return NoteHolder(view)
        }

        override fun onBindViewHolder(holder: NoteHolder, position: Int) {
            val note = notes[position]
            holder.bind(note)
        }

        override fun getItemCount() = notes.size

    }


    companion object{
        fun newInstance(): NoteListFragment{
            return NoteListFragment()
        }
    }

}