package com.example.daggerinaction.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.daggerinaction.R
import com.example.daggerinaction.app.App
import javax.inject.Inject

class LoginFragment : Fragment() {
    // You want Dagger to provide an instance of LoginViewModel from the graph
    @Inject
    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        //Field Injection
        (context as App).appComponentGraph.injectLogin(this)// Now loginViewModel is available
        super.onAttach(context)
    }

}