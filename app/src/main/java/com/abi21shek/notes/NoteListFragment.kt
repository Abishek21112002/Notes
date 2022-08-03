package com.abi21shek.notes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import javax.security.auth.callback.Callback

private const val TAG = "Notes Got"

class NoteListFragment: Fragment() {

    interface Callbacks {
        fun onNoteSelected(noteId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var noteRecyclerView: RecyclerView
    private var adapter: NoteAdapter? = NoteAdapter(emptyList())

    private val noteListViewModel: NoteListViewModel by lazy {
        ViewModelProviders.of(this).get(NoteListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_note_list, container, false)

        noteRecyclerView = view.findViewById(R.id.note_recycler_view) as RecyclerView
        noteRecyclerView.layoutManager = LinearLayoutManager(context)
        noteRecyclerView.adapter = adapter


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteListViewModel.noteListLiveData.observe(viewLifecycleOwner, Observer {
            notes -> notes?.let {
                Log.i(TAG, "Got Notes ${notes.size}")
            updateUI(notes)
        }
        })
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_note_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.new_note -> {
                val note = Note()
                noteListViewModel.addNote(note)
                callbacks?.onNoteSelected(note.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private inner class NoteHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{

        private lateinit var note: Note

        private val noteTitle: TextView = itemView.findViewById(R.id.note_item_title)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(note: Note){
            this.note = note
            noteTitle.text = note.title
        }

        override fun onClick(v: View){
            callbacks?.onNoteSelected(note.id)
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

    private fun updateUI(notes: List<Note>){
        adapter = NoteAdapter(notes)
        noteRecyclerView.adapter = adapter
    }

    companion object{
        fun newInstance(): NoteListFragment{
            return NoteListFragment()
        }
    }

}