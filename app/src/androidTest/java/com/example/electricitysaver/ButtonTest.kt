package com.example.electricitysaver

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ButtonTest {

    @get:Rule
    val activityRule = ActivityTestRule(userAddCustomItem::class.java)

    @Test
    fun clickButton() {
        onView(withId(R.id.btnadd)).perform(click())
    }
}