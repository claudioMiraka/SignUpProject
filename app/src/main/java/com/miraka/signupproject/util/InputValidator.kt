package com.miraka.signupproject.util

import javax.inject.Inject

class InputValidator @Inject constructor() {

    /**
     * Validates a passwords.
     * To keep it simple, a password is valid if it's at least 6 chars long.
     *
     * @param password: String
     * @return bool
     */
    fun isValidPassword(password:String) : Boolean{
        return password.length >= 6
    }


    /**
     * Validates an email address.
     *
     * @param email: String
     * @return bool
     */
    fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }
}