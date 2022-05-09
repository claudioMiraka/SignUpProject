package com.miraka.signupproject

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SignUpActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(SignUpActivity::class.java)

    @Test
    fun click_on_submit_with_empty_form() {
        onView(withId(R.id.email_edit_field)).check(matches(not(hasErrorText(getResourceString(R.string.invalid_email)))))
        onView(withId(R.id.password_edit_field)).check(matches(not(hasErrorText(getResourceString(R.string.invalid_password)))))
        onView(withId(R.id.submit_button)).perform(click())
        onView(withId(R.id.email_edit_field)).check(matches(hasErrorText(getResourceString(R.string.invalid_email))))
        onView(withId(R.id.password_edit_field)).check(matches(hasErrorText(getResourceString(R.string.invalid_password))))
    }

    @Test
    fun click_on_submit_with_invalid_form() {
        onView(withId(R.id.first_name_edit_field)).perform(typeText("first name"))
        onView(withId(R.id.email_edit_field)).perform(typeText("namelastdomail.com"))
        onView(withId(R.id.password_edit_field)).perform(typeText("123456"))
        onView(withId(R.id.website_edit_field)).perform(typeText("my.domain.com"), closeSoftKeyboard())

        onView(withId(R.id.submit_button)).perform(click())
        onView(withId(R.id.email_edit_field)).check(matches(hasErrorText(getResourceString(R.string.invalid_email))))
        onView(withId(R.id.password_edit_field)).check(matches(not(hasErrorText(getResourceString(R.string.invalid_password)))))

        onView(withId(R.id.password_edit_field)).perform(typeText("1236"))
        onView(withId(R.id.email_edit_field)).perform(typeText("namelast@domail.com"), closeSoftKeyboard())
        onView(withId(R.id.submit_button)).perform(click())

        onView(withId(R.id.password_edit_field)).check(matches(hasErrorText(getResourceString(R.string.invalid_password))))
        onView(withId(R.id.email_edit_field)).check(matches(not(hasErrorText(getResourceString(R.string.invalid_email)))))
    }

    @Test
    fun submit_form_successfully() {

        onView(withId(R.id.email_edit_field)).check(matches(not(hasErrorText(getResourceString(R.string.invalid_email)))))
        onView(withId(R.id.password_edit_field)).check(matches(not(hasErrorText(getResourceString(R.string.invalid_password)))))
        onView(withId(R.id.email_edit_field)).perform(typeText("name.last@domail.com"))
        onView(withId(R.id.password_edit_field)).perform(typeText("123456"), closeSoftKeyboard())
        onView(withId(R.id.submit_button)).perform(click())

        onView(withId(R.id.email_edit_field)).check(matches(not(hasErrorText(getResourceString(R.string.invalid_email)))))
        onView(withId(R.id.password_edit_field)).check(matches(not(hasErrorText(getResourceString(R.string.invalid_password)))))
        /// continue check here when what comes next is available

    }


    private fun getResourceString(id: Int): String {
        val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
        return targetContext.resources.getString(id)
    }
}