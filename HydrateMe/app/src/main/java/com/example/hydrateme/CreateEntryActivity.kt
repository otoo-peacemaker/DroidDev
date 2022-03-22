package com.example.hydrateme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class CreateEntryActivity : AppCompatActivity() {
    private var variable = 300
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_entry)
        setTitle("Create Entry")

        val addButton: View = findViewById(R.id.button)
        val entry: EditText = findViewById(R.id.editTextTextPersonName)

       Log.d(TAG, variable.toString())
        addButton.setOnClickListener { view->
            Snackbar.make(view,"Entry saved", Snackbar.LENGTH_LONG).show()
            val intakeInMl = entry.text.toString().toInt()
            Log.d(TAG, intakeInMl.toString()+"ml")

            val data = Intent()
            data.putExtra("intake", intakeInMl)
            setResult(RESULT_OK,data)
            finish()

        }
    }

    companion object{
        private const val TAG = "CreateEntryActivity"
    }
}