package com.example.roomwithrepositorypattern

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomwithrepositorypattern.adapter.ListItemAdapter
import com.example.roomwithrepositorypattern.factory.ViewModelFactory
import com.example.roomwithrepositorypattern.model.ListItem
import com.example.roomwithrepositorypattern.viewmodel.ListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private val newWordActivityRequestCode = 1

    private val dataViewModel: ListViewModel by viewModels {
        ViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = ListItemAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        dataViewModel.allList.observe(this, Observer {
            // Update the cached copy of the words in the adapter.
            it?.let {
                adapter.submitList(it)
            }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, ListActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {

            data?.getStringExtra(ListActivity.EXTRA_REPLY)?.let { reply->
                val word = ListItem(word = reply)
                dataViewModel.insert(word)
            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG
            ).show()
        }
    }


}