package com.miraka.signupproject

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.miraka.signupproject.view.confirmation.ConfirmationActivity
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class SignUpActivityTest {

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

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
        onView(withId(R.id.website_edit_field)).perform(
            typeText("my.domain.com"),
            closeSoftKeyboard()
        )

        onView(withId(R.id.submit_button)).perform(click())
        onView(withId(R.id.email_edit_field)).check(matches(hasErrorText(getResourceString(R.string.invalid_email))))
        onView(withId(R.id.password_edit_field)).check(matches(not(hasErrorText(getResourceString(R.string.invalid_password)))))

        onView(withId(R.id.password_edit_field)).perform(typeText("1236"))
        onView(withId(R.id.email_edit_field)).perform(
            typeText("namelast@domail.com"),
            closeSoftKeyboard()
        )
        onView(withId(R.id.submit_button)).perform(click())

        onView(withId(R.id.password_edit_field)).check(matches(hasErrorText(getResourceString(R.string.invalid_password))))
        onView(withId(R.id.email_edit_field)).check(matches(not(hasErrorText(getResourceString(R.string.invalid_email)))))
    }

    @Test
    fun submit_form_successfully() {
        val emailAddress = "name.last@domail.com"
        val password = "123456"

        onView(withId(R.id.email_edit_field)).check(matches(not(hasErrorText(getResourceString(R.string.invalid_email)))))
        onView(withId(R.id.password_edit_field)).check(matches(not(hasErrorText(getResourceString(R.string.invalid_password)))))
        onView(withId(R.id.email_edit_field)).perform(typeText(emailAddress))
        onView(withId(R.id.password_edit_field)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.submit_button)).perform(click())
        intended(hasComponent(ConfirmationActivity::class.java.name))

        onView(withId(R.id.user_first_name)).check(matches(not(isDisplayed())))
        onView(withId(R.id.user_website)).check(matches(not(isDisplayed())))
        onView(withId(R.id.user_email_address)).check(matches(withText(emailAddress)))
    }

    @Test
    fun submit_all_field_form() {
        val firstName = "myFirstName"
        val emailAddress = "name.last@domail.com"
        val password = "123456"
        val website = "microsoft.com"

        onView(withId(R.id.email_edit_field)).perform(typeText(emailAddress))
        onView(withId(R.id.first_name_edit_field)).perform(typeText(firstName))
        onView(withId(R.id.website_edit_field)).perform(typeText(website))
        onView(withId(R.id.password_edit_field)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.submit_button)).perform(click())

        intended(hasComponent(ConfirmationActivity::class.java.name))

        onView(withId(R.id.user_first_name)).check(matches(withText(firstName)))
        onView(withId(R.id.user_email_address)).check(matches(withText(emailAddress)))
        onView(withId(R.id.user_website)).check(matches(withText(website)))
    }


    private fun getResourceString(id: Int): String {
        val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
        return targetContext.resources.getString(id)
    }
}