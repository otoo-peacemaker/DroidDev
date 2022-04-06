package com.example.architecturaltemplate.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.architecturaltemplate.MainActivity
import com.example.architecturaltemplate.R
import com.example.architecturaltemplate.dao.LoginResponse
import com.example.architecturaltemplate.network.AuthApi
import com.example.architecturaltemplate.repository.LoginRepository
import com.example.architecturaltemplate.util.Constants
import com.example.architecturaltemplate.util.PreferenceConfiguration
import com.example.architecturaltemplate.viewmodel.LoginViewModel
import com.triad.mvvmlearning.databinding.FragmentLoginBinding
import com.triad.mvvmlearning.network.Resource
import com.triad.mvvmlearning.utility.UtilityMethods
import com.triad.mvvmlearning.view.BaseFragment


class LoginFragment : BaseFragment<LoginViewModel, FragmentLoginBinding, LoginRepository>() {
//    @Inject
//    lateinit var remoteDataSource2 : RemoteDataSource

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        (activity?.application as App).component
//            .inject(this)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {

            when (it) {
                is Resource.Success -> {
                    binding.viewmodel = it.value
                    SavePreference(it.value)
                    Toast.makeText(requireContext(), "Logged In", Toast.LENGTH_SHORT).show()
                }

                is Resource.Failure -> {

                    val loginIntent = Intent(
                        activity,
                        MainActivity::class.java
                    )
                    loginIntent.putExtra("from", "login")
                    startActivity(loginIntent)
                    val activity = activity as Activity
                    activity.finish()

                    Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }

        })

        binding.login.setOnClickListener {
            val paramObject = HashMap<String, String>()
            paramObject["username"] = binding.username.text.toString()
            paramObject["password"] = binding.password.text.toString()
            paramObject["login_type"] = "login"
            paramObject["role"] = "am"
            paramObject["device"] = "Device"
            viewModel.login(paramObject)

        }

       /* binding.signup.setOnClickListener {

//            val amount = "text"
//            val action =  LoginFragmentDirections.actionLoginFragmentToForgotPassword(amount)
//            it.findNavController().navigate(action)

            // Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_forgotPassword)

            val bundle = bundleOf("amount" to "data")
            it.findNavController().navigate(R.id.action_loginFragment_to_signUpFragment, bundle)
        }*/
//        DaggerAppComponent.builder().application(githubApp)
//            .build().inject(githubApp)
//        component = DaggerAppComponent.builder().build();
//        component.inject(this);

        //  val callServer = backendService!!.callServer()

        // remoteDa.testmetod(App.applicationContext())
    }


    private fun SavePreference(value: LoginResponse) {

        val username: String = value.data.username
        val fname: String = value.data.name

        UtilityMethods.setTokenValue(value.data.token)

        PreferenceConfiguration.setPreference(
            Constants.PreferenceConstants.USER_ID,
            username
        )
        PreferenceConfiguration.setPreference(
            Constants.PreferenceConstants.USER_PASSWORD,
            binding.password.text.toString()
        )
        PreferenceConfiguration.setPreference(Constants.PreferenceConstants.USER_NAME, username)
        PreferenceConfiguration.setPreference(
            Constants.PreferenceConstants.STATUS,
            value.data.status
        )
        PreferenceConfiguration.setPreference(Constants.PreferenceConstants.FIRST_NAME, fname)

        val loginIntent = Intent(
            activity,
            MainActivity::class.java
        )
        loginIntent.putExtra("from", "login")
        startActivity(loginIntent)
        val activity = activity as Activity
        activity.finish()
    }


    override fun getViewModel() = LoginViewModel::class.java

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewDataBinding {

        return DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
    }


    override fun getFragmentRepository(): LoginRepository {
        return LoginRepository(remoteDataSource.buildApi(AuthApi::class.java))
    }
}