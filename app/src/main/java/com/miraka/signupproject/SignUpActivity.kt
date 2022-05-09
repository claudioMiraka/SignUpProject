package com.miraka.signupproject

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.miraka.signupproject.databinding.ActivitySignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private val signUpViewModel: SignUpViewModel by viewModels()
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        binding.lifecycleOwner = this
        binding.signUpViewModel = signUpViewModel


        signUpViewModel.isWaitingForResults.observe(this) { waiting ->
            binding.firstNameEditField.isEnabled = !waiting
            binding.emailEditField.isEnabled = !waiting
            binding.websiteEditField.isEnabled = !waiting
            binding.passwordEditField.isEnabled = !waiting
            binding.submitButton.visibility = if (waiting) View.GONE else View.VISIBLE
            binding.progressBar.visibility = if (waiting) View.VISIBLE else View.GONE
        }

        signUpViewModel.passwordInvalid.observe(this) { isValid ->
            binding.passwordEditField.error =
                if (isValid) getString(R.string.invalid_password) else null
        }

        signUpViewModel.emailInvalid.observe(this) { isValid ->
            binding.emailEditField.error = if (isValid) getString(R.string.invalid_email) else null
        }
    }
}