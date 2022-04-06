package com.example.app.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.app.network.NetworkStatus
import com.example.app.ui.LoginFragment
import com.example.app.ui.RegistrationFragment
import com.google.android.material.snackbar.Snackbar
import okhttp3.ResponseBody
import java.util.regex.Pattern

fun isEmailValid(email: String): Boolean {
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(email)
    return matcher.matches()
}

fun isPasswordValid(password: StringBuffer, confirmPassword: StringBuffer): Boolean {
    val expression = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
    val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
    val matcher = pattern.matcher(password)
    matcher.appendTail(confirmPassword)
    return matcher.matches()
}


//Snack bar for showing error cases
fun View.snackBar(message: String, action: (() -> Unit)? = null) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackBar.setAction("Retry") {
            it()
        }
    }
    snackBar.show()
}

//making toast
fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}



fun Fragment.handleApiErr(
    failure: NetworkStatus.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackBar(
            "Please check your internet connection", retry
        )

        failure.errorCode == StatusCodes.unauthorized -> {
            when (this) {
                is LoginFragment -> {
                    requireView().snackBar("You've entered incorrect email or password")
                }
                is RegistrationFragment -> {
                    requireView().snackBar("You've entered incorrect email or password")
                }

                else -> {
                    val error = failure.errorBody?.string().toString()
                    requireView().snackBar(error)
                }
            }

        }
    }
}