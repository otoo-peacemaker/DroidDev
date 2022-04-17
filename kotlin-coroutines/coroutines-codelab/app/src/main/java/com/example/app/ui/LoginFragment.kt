package com.example.app.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.app.*
import com.example.app.databinding.FragmentLoginBinding
import com.example.app.network.NetworkStatus
import com.example.app.util.handleApiErr
import com.example.app.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding

     private val viewModel: UserViewModel by lazy {
        ViewModelProvider(this).get(UserViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

         // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
       // binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
//        binding.viewModel = viewModel

        viewModel.userResponse.observe(viewLifecycleOwner, Observer {
            //load the progress
           //  binding.progressbar.visible(it is NetworkStatus.Loading)
            when(it){
                is  NetworkStatus.Success->{
                    lifecycleScope.launch {
                        //save access token
                        viewModel.saveAccessTokens(
                            it.value.access!!,
                            it.value.refresh!!
                        )

                        //navigation
                    }
                }

                is NetworkStatus.Failure-> handleApiErr(it){
                    login()
                }else ->{
                    print("nothing")
                }
            }
        })

        //set on click listener
    }

    private fun login(){
        viewModel.login(UserBody("email","password"))
    }


}