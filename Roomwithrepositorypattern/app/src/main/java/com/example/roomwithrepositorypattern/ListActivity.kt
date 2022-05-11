package com.example.roomwithrepositorypattern

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class ListActivity : AppCompatActivity() {
    private lateinit var editWordView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_acitivity)

        editWordView = findViewById(R.id.edit_word)
        val btn = findViewById<Button>(R.id.button_save)

        btn.setOnClickListener {
            val  replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) setResult(Activity.RESULT_CANCELED, replyIntent)
            else{
                val word = editWordView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, word)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }



    }
    companion object {
        const val EXTRA_REPLY = "REPLY"
    }
}