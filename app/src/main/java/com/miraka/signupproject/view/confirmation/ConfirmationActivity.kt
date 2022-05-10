package com.miraka.signupproject.view.confirmation

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.miraka.signupproject.R
import com.miraka.signupproject.databinding.ActivityConfirmationBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import kotlin.math.sign

@AndroidEntryPoint
class ConfirmationActivity : AppCompatActivity() {

    private val confirmationViewModel: ConfirmationViewModel by viewModels()
    private lateinit var binding: ActivityConfirmationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirmation)

        binding.lifecycleOwner = this
        binding.signUpViewModel = confirmationViewModel


        confirmationViewModel.user?.let { signedUser ->

            binding.userEmailAddress.text = signedUser.emailAddress

            if (signedUser.firstName != null && signedUser.firstName.isNotBlank()) {
                binding.userFirstName.text = signedUser.firstName
                binding.confirmHeader.text = String.format(getString(R.string.confirmation_header), signedUser.firstName)
            } else {
                binding.confirmHeader.text = String.format(getString(R.string.confirmation_header), R.string.there)
                binding.userFirstName.visibility = View.GONE
            }

            if (signedUser.website != null && signedUser.website.isNotBlank()) {
                binding.userWebsite.text = signedUser.website
                binding.userWebsite.setOnClickListener {
                    try {
                        var url = signedUser.website
                        if (!url.startsWith("https://") && !url.startsWith("http://")){
                            url = "https://$url"
                        }
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(intent)
                    } catch (ex: Exception) {
                        Toast.makeText(this, R.string.url_lunch_error, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                binding.userWebsite.visibility = View.GONE
            }
        }
    }
}