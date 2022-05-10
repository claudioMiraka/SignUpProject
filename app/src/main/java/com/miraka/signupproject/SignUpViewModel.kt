package com.miraka.signupproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraka.signupproject.model.SignedUser
import com.miraka.signupproject.repos.UserRepository
import com.miraka.signupproject.util.InputValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

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

    private val _navigate = MutableLiveData<Boolean?>()
    val navigate: LiveData<Boolean?> = _navigate

    fun submitForm() {

        Timber.d(
            "Form submitted. " +
                    "Email: ${emailAddress.value}, " +
                    "FirstName: ${firstName.value}, " +
                    "Website: ${website.value}," +
                    "Password: ${password.value}"
        )

        _waitingForResult.value = true

        val newUser = validateCredentials()
        viewModelScope.launch {

            newUser?.let {
                userRepository.updateUserInfo(it)
                navigateAway()
            }

            _waitingForResult.postValue(false)
        }
    }

    private fun validateCredentials(): SignedUser? {
        if (emailAddress.value != null && password.value != null) {
            val emailAddressSafe = emailAddress.value!!
            val isEmailInvalid = !inputValidator.isValidEmail(emailAddressSafe)
            _emailInvalid.postValue(isEmailInvalid)

            val passwordSafe = password.value!!
            val isPasswordInvalid = !inputValidator.isValidPassword(passwordSafe)
            _passwordInvalid.postValue(isPasswordInvalid)

            if (!isEmailInvalid && !isPasswordInvalid) {
                return SignedUser(
                    firstName = firstName.value,
                    emailAddress = emailAddressSafe,
                    website = website.value
                )
            }
        }
        return null
    }

    private fun navigateAway() {
        _navigate.postValue(true)
    }

    fun navigateAwayDone() {
        _navigate.value = null
    }
}