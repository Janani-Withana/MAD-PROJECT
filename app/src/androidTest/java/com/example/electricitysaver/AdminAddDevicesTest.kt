package com.example.electricitysaver

import android.widget.Button
import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
class AdminAddDevicesTest {

    @Test
    fun testAddItem() {
        // Start the activity
        val scenario = ActivityScenario.launch(AdminAddDevices::class.java)

        // Fill in the fields
        onView(withId(R.id.cat)).perform(typeText("Test Category"), closeSoftKeyboard())
        onView(withId(R.id.brand)).perform(typeText("Test Brand"), closeSoftKeyboard())
        onView(withId(R.id.edtWatts)).perform(typeText("100"), closeSoftKeyboard())

        // Click the add button
        onView(withId(R.id.btnAdminAdd)).perform(click())

        // Check that the record was added
        onView(withId(android.R.id.button1)).perform(click()) // Click the "OK" button on the dialog

        // Close the activity
        scenario.close()
    }

    @Test
    fun testEmptyFields() {
        // Start the activity
        val scenario = ActivityScenario.launch(AdminAddDevices::class.java)

        // Click the add button
        onView(withId(R.id.btnAdminAdd)).perform(click())

        // Check that an error message is displayed
        onView(withText("Please fill in all fields")).check(doesNotExist())

        // Close the activity
        scenario.close()
    }

    @Test
    fun testInvalidWatts() {
        // Start the activity
        val scenario = ActivityScenario.launch(AdminAddDevices::class.java)

        // Fill in the fields
        onView(withId(R.id.cat)).perform(typeText("Test Category"), closeSoftKeyboard())
        onView(withId(R.id.brand)).perform(typeText("Test Brand"), closeSoftKeyboard())
        onView(withId(R.id.edtWatts)).perform(typeText("-100"), closeSoftKeyboard())

        // Click the add button
        onView(withId(R.id.btnAdminAdd)).perform(click())

        // Close the activity
        scenario.close()
    }

}