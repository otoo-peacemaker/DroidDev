package com.example.daggerinaction.ui.register

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.daggerinaction.R
import com.example.daggerinaction.app.App
import javax.inject.Inject

class RegisterFragment : Fragment() {

    @Inject lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        // TODO: Use the ViewModel
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
      (context as App).appComponentGraph.injectRegistration(this)

    }

}