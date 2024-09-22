package com.example.noteapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat.Style
import androidx.core.content.withStyledAttributes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapplication.adaptor.NoteAdapter
import com.example.noteapplication.room.Note
import com.example.noteapplication.room.NoteDatabase
import com.example.noteapplication.room.NoteViewModel
import com.example.noteapplication.room.NoteViewModelFactory
import com.example.noteapplication.utils.Keys
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteAdapter.OnClickListener {
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var addNoteLauncher: ActivityResultLauncher<Intent>
    private lateinit var addButton: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton = findViewById(R.id.addButton)
        recyclerView = findViewById(R.id.recyclerView)

        val noteDao = NoteDatabase.getNoteDatabase(application).getNoteDao()

        val factory = NoteViewModelFactory(application, noteDao)

        noteViewModel = ViewModelProvider(this, factory).get(NoteViewModel::class.java)
        ////////////////////////////////////////////////////////////////////////////////////////////////

        val adapter = NoteAdapter(this)
        recyclerView.layoutManager =LinearLayoutManager(this)
        recyclerView.adapter = adapter

        noteViewModel.allNotes.observe(this, Observer {note ->
            note?.let { adapter.setNotes(it) }
        })

        addButton.setOnClickListener {
            val intent = Intent(this, AddEditActivity::class.java)
            addNoteLauncher.launch(intent)
        }
        addNoteLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val title = result.data!!.getStringExtra(Keys.TITLE_KEY)
                    val descriptions = result.data!!.getStringExtra(Keys.DESC_KEY)
                    val id = result.data!!.getIntExtra(Keys.ID_KEY,-1)
                    val note = Note(id, title,descriptions)
                    noteViewModel.insert(note)
                }else if (result.resultCode == RESULT_OK){

                    val title = result.data!!.getStringExtra(Keys.TITLE_KEY)
                    val descriptions = result.data!!.getStringExtra(Keys.DESC_KEY)
                    val id = result.data!!.getIntExtra(Keys.ID_KEY,-1)
                    val note = Note(id, title,descriptions)
                    note.id = id!!
                    noteViewModel.update(note)
                }
            }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setIcon(R.drawable.baseline_delete_24)
                dialog.setTitle("Delete")
                dialog.setMessage("Do You want to Delete This Note?")

                dialog.setNegativeButton("delete"){_,_ ->
                    noteViewModel.delete(adapter.getNoteAt(viewHolder.adapterPosition))

                }
                dialog.setPositiveButton("undo"){_,_->
                    noteViewModel.insert(adapter.getNoteAt(viewHolder.adapterPosition))
                }
                dialog.create().show()


            }

        }).attachToRecyclerView(recyclerView)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_all_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteAllMenu -> noteViewModel.deleteAllNote()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClickItem(note: Note) {
        val title = note.title
        val descriptions = note.descriptions
        val id = note.id

        val intent = Intent(this@MainActivity,AddEditActivity::class.java)
        intent.putExtra(Keys.TITLE_KEY,title)
        intent.putExtra(Keys.DESC_KEY,descriptions)
        intent.putExtra(Keys.ID_KEY,id)
        addNoteLauncher.launch(intent)
    }
}