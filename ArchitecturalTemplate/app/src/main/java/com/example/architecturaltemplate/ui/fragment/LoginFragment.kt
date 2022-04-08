package com.example.architecturaltemplate.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.architecturaltemplate.R
import com.example.architecturaltemplate.dao.LoginResponse
import com.example.architecturaltemplate.databinding.FragmentLoginBinding
import com.example.architecturaltemplate.network.AuthApi
import com.example.architecturaltemplate.network.Resource
import com.example.architecturaltemplate.repository.LoginRepository
import com.example.architecturaltemplate.util.*
import com.example.architecturaltemplate.viewmodel.LoginViewModel
import com.triad.mvvmlearning.utility.UtilityMethods
import kotlinx.coroutines.launch


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.loading.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        binding.xmlViewModel = it.value// save responses to [LoginResponse]
                        savePreference(it.value)// save responses to preference
                        view.snackbar("Login successfully")

                        TODO("Start new fragment")
                    }
                }

                is Resource.Failure -> handleApiError(it){
                }
                else -> {
                    view.snackbar("something went wrong, please try again")
                }
            }
        })

        binding.login.setOnClickListener {
            login()
        }
    }

  private  fun login(){
        val paramObject = HashMap<String, String>()
            paramObject["username"] = binding.username.text.toString()
            paramObject["password"] = binding.password.text.toString()
            viewModel.login(paramObject)
    }


    private fun savePreference(value: LoginResponse) {
        UtilityMethods.setTokenValue(value.authData.token)
        PreferenceConfiguration.setPreference(
            Constants.PreferenceConstants.USER_ID, value.authData.email
        )

        PreferenceConfiguration.setPreference(
            Constants.PreferenceConstants.USER_PASSWORD,
            binding.password.text.toString()
        )
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
        val TAG = LoginFragment
    }
}