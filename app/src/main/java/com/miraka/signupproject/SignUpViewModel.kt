package com.miraka.signupproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miraka.signupproject.util.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var inputValidator: InputValidator

    val firstName = MutableLiveData("")
    val password = MutableLiveData("")
    val website = MutableLiveData("")
    val emailAddress = MutableLiveData("")

    private val _waitingForResult = MutableLiveData(false)
    val isWaitingForResults: LiveData<Boolean> = _waitingForResult

    private val _passwordInvalid = MutableLiveData(false)
    val passwordInvalid: LiveData<Boolean> = _passwordInvalid

    private val _emailInvalid = MutableLiveData(false)
    val emailInvalid: LiveData<Boolean> = _emailInvalid


    fun submitForm() {

        _waitingForResult.value = true

        emailAddress.value?.let {
            _emailInvalid.postValue(!inputValidator.isValidEmail(it))
        }

        password.value?.let {
            _passwordInvalid.postValue(!inputValidator.isValidPassword(it))
        }

        _waitingForResult.postValue(false)
    }
}