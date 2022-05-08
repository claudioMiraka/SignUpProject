package com.miraka.signupproject

import com.miraka.signupproject.util.InputValidator
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class InputValidatorTest {

    private val inputValidator = InputValidator()

    @Test
    fun `test valid and invalid password`() {
        assertTrue(inputValidator.isValidPassword("aubifbfah"))

        assertFalse(inputValidator.isValidPassword(""))
        assertFalse(inputValidator.isValidPassword("12345"))
    }

    @Test
    fun `test valid emails`() {
        assertTrue(inputValidator.isValidEmail("name@domain.com"))
        assertTrue(inputValidator.isValidEmail("n@s.eu"))
        assertTrue(inputValidator.isValidEmail("first.last@domain.com"))
        assertTrue(inputValidator.isValidEmail("name_middle.last@domain.com"))
    }

    @Test
    fun `test invalid emails`() {

        assertFalse(inputValidator.isValidEmail(""))
        assertFalse(inputValidator.isValidEmail("name_domain.com"))

        assertFalse(inputValidator.isValidEmail("notValid"))
        assertFalse(inputValidator.isValidEmail(".notValid"))
        assertFalse(inputValidator.isValidEmail("otValid."))

        assertFalse(inputValidator.isValidEmail("@otValid."))
        assertFalse(inputValidator.isValidEmail(".@otValid."))
        assertFalse(inputValidator.isValidEmail("@otValid"))
        assertFalse(inputValidator.isValidEmail(".otValid@"))
        assertFalse(inputValidator.isValidEmail("otValid@"))
        assertFalse(inputValidator.isValidEmail("@domain.com"))
    }
}