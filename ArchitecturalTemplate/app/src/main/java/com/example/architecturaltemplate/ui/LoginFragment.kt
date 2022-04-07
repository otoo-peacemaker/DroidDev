package com.example.architecturaltemplate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.architecturaltemplate.dao.LoginResponse
import com.example.architecturaltemplate.databinding.FragmentLoginBinding
import com.example.architecturaltemplate.network.AuthApi
import com.example.architecturaltemplate.repository.LoginRepository
import com.example.architecturaltemplate.ui.BaseFragment
import com.example.architecturaltemplate.viewmodel.LoginViewModel
import com.example.architecturaltemplate.network.Resource
import com.example.architecturaltemplate.util.Constants
import com.example.architecturaltemplate.util.PreferenceConfiguration
import com.triad.mvvmlearning.utility.UtilityMethods


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loginResponse.observe(viewLifecycleOwner, Observer{
            when(it){
                is Resource.Success ->{
                    binding.xmlViewModel = it.value
                    savePreference(it.value)
                    Toast.makeText(requireContext(),"Logged in",Toast.LENGTH_LONG).show()
                }
                is Resource.Failure ->{
                    Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        })

         binding.login.setOnClickListener {
            val paramObject = HashMap<String, String>()
            paramObject["username"] = binding.username.text.toString()
            paramObject["password"] = binding.password.text.toString()
            paramObject["login_type"] = "login"
            viewModel.login(paramObject)

        }
    }


    private fun savePreference(value: LoginResponse){
        UtilityMethods.setTokenValue(value.data.token)
        PreferenceConfiguration.setPreference(
            Constants.PreferenceConstants.USER_ID, value.data.email
        )

        PreferenceConfiguration.setPreference(
            Constants.PreferenceConstants.USER_PASSWORD,
            binding.password.text.toString()
        )
    }

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?): ViewDataBinding {
        return DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
    }

    override fun getFragmentRepository(): LoginRepository {
        return LoginRepository(remoteDataSource.buildApi(AuthApi::class.java))
    }

    override fun getViewModel() = LoginViewModel::class.java
}