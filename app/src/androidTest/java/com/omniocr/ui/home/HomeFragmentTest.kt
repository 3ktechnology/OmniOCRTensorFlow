package com.omniocr.ui.home

import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.omniocr.MainActivity
import com.omniocr.R
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    var activityTestRule :ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)
    private val ITEM_BELOW_THE_FOLD = 10
    private val ITEM_MULTIPLE = 1
    private val ITEM_MULTIPLE2 = 2
    private val TOTALITEMS =3




    @Test
    fun test_textViews(){
        val homeFragment: Fragment = HomeFragment()
        activityTestRule.activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment)

        onView(withId(R.id.homeTitle)).check(matches(withText(R.string.title_capture_access))).check(matches(isDisplayed()))
        onView(withId(R.id.text_desc)).check(matches(withText(R.string.title_home_details))).check(matches(isDisplayed()))
        onView(withId(R.id.text_application)).check(matches(withText(R.string.title_application))).check(matches(isDisplayed()))
        onView(withId(R.id.text2)).check(matches(withText(R.string.title_your_application))).check(matches(isDisplayed()))
        onView(withId(R.id.text3)).check(matches(withText(R.string.title_application_details))).check(matches(isDisplayed()))
        onView(allOf( isAssignableFrom(TextView::class.java), withText(R.string.title_view_application))).check(matches(isDisplayed()))
        onView(allOf( isAssignableFrom(TextView::class.java), withText("12"))).check(matches(isDisplayed()))

    }



    @Test
    fun test_assesmentButtonClick()
    {
        val homeFragment: Fragment = HomeFragment()
        activityTestRule.activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment)
        onView(allOf( isAssignableFrom(Button::class.java), withText(R.string.title_start_new_accessment))).perform(click())
    }


    @Test
    fun test_gallery()
    {
        val homeFragment: Fragment = HomeFragment()
        activityTestRule.activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment)
        onView(allOf( isAssignableFrom(Button::class.java), withText(R.string.title_start_new_accessment))).perform(click())
        onView(withId(R.id.button_standard)).perform(click())
        onView(withId(R.id.text_ar)).check(matches(isDisplayed()))
        onView(withId(R.id.clickme_gallary)).perform(click())
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_BELOW_THE_FOLD, click()))
        onView(withId(R.id.text_access)).perform(click())


    }

    @Test
    fun test_textView_AR()
    {
        val homeFragment: Fragment = HomeFragment()
        activityTestRule.activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment)
        onView(allOf( isAssignableFrom(Button::class.java), withText(R.string.title_start_new_accessment))).perform(click())
        onView(withId(R.id.text_ar)).perform(click())
        Thread.sleep(10000)
    }


    @Test
    fun testLongPress_galleryItems()
    {
        val homeFragment: Fragment = HomeFragment()
        activityTestRule.activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment)
        onView(allOf( isAssignableFrom(Button::class.java), withText(R.string.title_start_new_accessment))).perform(click())
        onView(withId(R.id.button_standard)).perform(click())
        onView(withId(R.id.clickme_gallary)).perform(click())
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_BELOW_THE_FOLD, longClick()))
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_MULTIPLE, longClick()))
        onView(withId(R.id.selection_ok) ).perform(click())
        onView(withId(R.id.text_access)).perform(click())

    }

    @Test
    fun testCapturedImageList()
    {
        val homeFragment: Fragment = HomeFragment()
        activityTestRule.activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment)
        onView(allOf( isAssignableFrom(Button::class.java), withText(R.string.title_start_new_accessment))).perform(click())
        onView(withId(R.id.button_standard)).perform(click())
        onView(withId(R.id.clickme_gallary)).perform(click())
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_MULTIPLE, longClick()))
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_MULTIPLE2, longClick()))
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_BELOW_THE_FOLD, longClick()))
        onView(withId(R.id.selection_ok) ).perform(click())
        onView(withId(R.id.text_access)).perform(click())
        onView(withId(R.id.select)).perform(click())
        onView(withId(R.id.image_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(TOTALITEMS-1, click()))
        onView(withId(R.id.image_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(TOTALITEMS-2, click()))
        onView(withId(R.id.image_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(TOTALITEMS-3, click()))


    }

    @Test
    fun test_capturedImageList_LongPress()
    {
        val homeFragment: Fragment = HomeFragment()
        activityTestRule.activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment)
        onView(allOf( isAssignableFrom(Button::class.java), withText(R.string.title_start_new_accessment))).perform(click())
        onView(withId(R.id.button_standard)).perform(click())
        onView(withId(R.id.clickme_gallary)).perform(click())
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_MULTIPLE, longClick()))
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_MULTIPLE2, longClick()))
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_BELOW_THE_FOLD, longClick()))
        onView(withId(R.id.selection_ok) ).perform(click())
        onView(withId(R.id.text_access)).perform(click())
        onView(withId(R.id.image_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(TOTALITEMS-3, click()))
        onView(withId(R.id.image_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(TOTALITEMS-2, click()))
        onView(withId(R.id.image_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(TOTALITEMS-1, click()))

    }

    @Test
    fun test_capturedImageList_SelectAll()
    {
        val homeFragment: Fragment = HomeFragment()
        activityTestRule.activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment)
        onView(allOf( isAssignableFrom(Button::class.java), withText(R.string.title_start_new_accessment))).perform(click())
        onView(withId(R.id.button_standard)).perform(click())
        onView(withId(R.id.clickme_gallary)).perform(click())
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_MULTIPLE, longClick()))
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_MULTIPLE2, longClick()))
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_BELOW_THE_FOLD, longClick()))
        onView(withId(R.id.selection_ok) ).perform(click())
        onView(withId(R.id.text_access)).perform(click())
        onView(withId(R.id.image_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(TOTALITEMS-3, click()))
        onView(allOf( isAssignableFrom(TextView::class.java), withText(R.string.title_select_all))).perform(click())

    }

    @Test
    fun test_capturedImageList_SelectAllDeleteAll()
    {
        val homeFragment: Fragment = HomeFragment()
        activityTestRule.activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment)
        onView(allOf( isAssignableFrom(Button::class.java), withText(R.string.title_start_new_accessment))).perform(click())
        onView(withId(R.id.button_standard)).perform(click())
        onView(withId(R.id.clickme_gallary)).perform(click())
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_MULTIPLE, longClick()))
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_MULTIPLE2, longClick()))
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_BELOW_THE_FOLD, longClick()))
        onView(withId(R.id.selection_ok) ).perform(click())
        onView(withId(R.id.text_access)).perform(click())
        onView(withId(R.id.image_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(TOTALITEMS-3, click()))
        onView(allOf( isAssignableFrom(TextView::class.java), withText(R.string.title_select_all))).perform(click())
        onView(withId(R.id.delete)).perform(click())

    }


    @Test
    fun test_capturedImageList_Recapture()
    {
        val homeFragment: Fragment = HomeFragment()
        activityTestRule.activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment)
        onView(allOf( isAssignableFrom(Button::class.java), withText(R.string.title_start_new_accessment))).perform(click())
        onView(withId(R.id.button_standard)).perform(click())
        onView(withId(R.id.clickme_gallary)).perform(click())
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_MULTIPLE, longClick()))
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_MULTIPLE2, longClick()))
        onView(withId(R.id.rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEM_BELOW_THE_FOLD, longClick()))
        onView(withId(R.id.selection_ok) ).perform(click())
        onView(withId(R.id.text_access)).perform(click())
        onView(withId(R.id.image_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(TOTALITEMS-3, click()))
        onView(withId(R.id.re_capture)).perform(click())
        onView(withId(R.id.clickme)).perform(click())
        Thread.sleep(2500)
        onView(withId(R.id.text_access)).perform(click())
        Thread.sleep(2000)


    }


    @Test
    fun test_capturedImage()
    {
        val homeFragment: Fragment = HomeFragment()
        activityTestRule.activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFragment)
        onView(allOf( isAssignableFrom(Button::class.java), withText(R.string.title_start_new_accessment))).perform(click())
        onView(withId(R.id.button_standard)).perform(click())
        onView(withId(R.id.clickme)).perform(click())
        Thread.sleep(1500)
        onView(withId(R.id.text_access)).perform(click())
        Thread.sleep(1500)


    }

}