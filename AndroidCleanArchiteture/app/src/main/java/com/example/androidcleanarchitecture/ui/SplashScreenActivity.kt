package com.example.androidcleanarchitecture.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.androidcleanarchitecture.R
import com.example.androidcleanarchitecture.viewmodel.SplashActivityViewModel
import com.example.androidcleanarchitecture.viewmodel.factory.ViewModelFactory
import com.example.androidcleanarchitecture.webservices.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

   private lateinit var viewModel: SplashActivityViewModel
  //@Inject lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        viewModel = ViewModelProvider(this)[SplashActivityViewModel::class.java]

        val textView = findViewById<TextView>(R.id.splash)
        lifecycleScope.launch {
            delay(500)
            viewModel.getSampleResponse()
                .collect {
                    when(it){
                        is State.DataState ->textView.text = "success ${it.data}"
                         is State.ErrorState -> textView.text = "error ${it.exception}"
                        is State.LoadingState -> textView.text = "loading.."
                    }
                }
        }
    }
}