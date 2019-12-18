package com.omniocr.ui.login.fragment

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.omniocr.R
import com.omniocr.ui.login.LoginActivity
import com.omniocr.ui.login.fragment.ImageViewHasDrawableMatcher.hasDrawable
import org.hamcrest.CoreMatchers.not
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailFragmentTest {


    @get:Rule
    var activityActivityTestRule = ActivityTestRule(LoginActivity::class.java)


    @Test
    fun email_Fragment_Test_complete() {

        val emailFragment: Fragment = EmailFragment()
        activityActivityTestRule.activity.getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame_layout, emailFragment)

        onView(Matchers.allOf(isAssignableFrom(Button::class.java), withText(R.string.title_next))).check(matches(not(isClickable())))
        onView(withId(R.id.edit_text_email))
            .perform(typeText(EMAIL_VALID), closeSoftKeyboard())
        onView(Matchers.allOf(isAssignableFrom(Button::class.java), withText(R.string.title_next))).perform(
            click())

        onView(withId(R.id.loginButton)).check(matches(not(isClickable())))

        onView(withId(R.id.edit_text_email)).perform(
            typeText(PASSWORD_VALID),
            closeSoftKeyboard()
        )
        onView(withId(R.id.loginButton)).perform(click())

    }


    @Test
    fun email_Fragment_Test_textView() {

        val emailFragment: Fragment = EmailFragment()
        activityActivityTestRule.activity.getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame_layout, emailFragment)
        onView(withId(R.id.email_instruction)).check(matches(withText(R.string.title_email_desc)))

        onView(withId(R.id.email)).check(matches(withText(R.string.ques_email)))

    }

    @Test
    fun password_Fragment_backPress() {

        val emailFragment: Fragment = EmailFragment()
        activityActivityTestRule.activity.getSupportFragmentManager().beginTransaction()
            .replace(R.id.frame_layout, emailFragment)

        onView(withId(R.id.edit_text_email))
            .perform(typeText(EMAIL_VALID), closeSoftKeyboard())
        onView(
            Matchers.allOf(
                isAssignableFrom(Button::class.java),
                withText(R.string.title_next)
            )
        ).perform(click())
        onView(withId(R.id.back_button)).check(matches(not(hasDrawable())))
            .perform(click())

    }


    companion object {
        val EMAIL_VALID = "abc@gmail.com"
        val PASSWORD_VALID = "12345"
        val LOG_IN = "Log in"
    }
}