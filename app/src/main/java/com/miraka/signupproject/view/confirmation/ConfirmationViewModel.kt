package com.miraka.signupproject.view.confirmation

import androidx.lifecycle.ViewModel
import com.miraka.signupproject.repos.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ConfirmationViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel()  {

    val user = userRepository.user

    fun signIn(){
        Timber.i("Signing in")
    }
}