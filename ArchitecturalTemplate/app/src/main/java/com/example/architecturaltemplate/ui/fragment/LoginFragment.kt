package com.example.architecturaltemplate.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.architecturaltemplate.R
import com.example.architecturaltemplate.databinding.FragmentLoginBinding
import com.example.architecturaltemplate.network.AuthApi
import com.example.architecturaltemplate.network.Resource
import com.example.architecturaltemplate.repository.LoginRepository
import com.example.architecturaltemplate.util.handleApiError
import com.example.architecturaltemplate.util.snackbar
import com.example.architecturaltemplate.util.visible
import com.example.architecturaltemplate.viewmodel.LoginViewModel
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            viewModel.loginOnClickListener()
        }

        initializeViewModel()

    }

    private fun initializeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
                    Log.i(
                        TAG,
                        "...........................:::::::::::: LoginFragment view model scope"
                    )
                    binding.loading.visible(it is Resource.Loading)
                    when (it) {
                        is Resource.Success -> {
                            lifecycleScope.launch {
                                binding.xmlLoginResponse =
                                    it.value// save responses to [LoginResponse]
                                viewModel.savePreference(it.value)// save responses to preference
                                view?.snackbar("Login successfully")

                                TODO("Start new fragment")
                            }
                        }

                        is Resource.Failure -> handleApiError(it) { /*viewModel.loginOnClickListener()*/ }
                        else -> {
                            view?.snackbar("something went wrong, please try again")
                        }
                    }
                })
            }
        }
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
    }

    override fun getFragmentRepository(): LoginRepository {
        return LoginRepository(remoteDataSource.buildApi(AuthApi::class.java))
    }

    override fun getViewModel() = LoginViewModel::class.java

    companion object {
        const val TAG = "LoginFragment"
    }
}