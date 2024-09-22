package com.example.noteapplication


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapplication.room.Note
import com.example.noteapplication.utils.Keys

class AddEditActivity : AppCompatActivity() {
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var numberID: NumberPicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)

        titleEditText = findViewById(R.id.titleEditText)
        descriptionEditText = findViewById(R.id.descriptionEditText)
        numberID = findViewById(R.id.numberID)

        numberID.minValue = 0
        numberID.maxValue = 1000


        if (intent.hasExtra(Keys.ID_KEY.toString())) {
            title = "Edit Note"
            titleEditText.setText(intent.getStringExtra(Keys.TITLE_KEY))
            descriptionEditText.setText(intent.getStringExtra(Keys.DESC_KEY))
            numberID.value = intent.getIntExtra(Keys.ID_KEY.toString(), -1)
        } else {
            title = "Add Note"
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_Menu -> saveNote()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun saveNote() {
        val title = titleEditText.text.toString()
        val description = descriptionEditText.text.toString()
        val id = numberID.value

        if (title.trim().isEmpty()) {
            Toast.makeText(this@AddEditActivity, "Please Enter Title", Toast.LENGTH_SHORT).show()
        }
        val newNote = Note(
            id = id,
            title = title,
            descriptions = description
        )

        val ID = intent.getIntExtra(Keys.ID_KEY, -1)
        if (ID != -1) {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(Keys.ID_KEY.toString(), newNote.id)
                putExtra(Keys.TITLE_KEY, newNote.title)
                putExtra(Keys.DESC_KEY, newNote.descriptions)
            })
        } else {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(Keys.ID_KEY.toString(), newNote.id)
                putExtra(Keys.TITLE_KEY, newNote.title)
                putExtra(Keys.DESC_KEY, newNote.descriptions)

            })
        }
        finish()

    }


}

