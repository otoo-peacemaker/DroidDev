package com.example.daggerinaction.ui.login

import androidx.lifecycle.ViewModel
import com.example.daggerinaction.repository.UserRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    val userRepository: UserRepository
) : ViewModel() {
    // TODO: Implement the ViewModel
}