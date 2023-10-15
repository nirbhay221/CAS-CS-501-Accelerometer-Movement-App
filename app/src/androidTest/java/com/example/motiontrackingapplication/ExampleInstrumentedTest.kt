package com.example.motiontrackingapplication

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation

import androidx.test.uiautomator.UiDevice
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private lateinit var scenario: ActivityScenario<MainActivity>
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.motiontrackingapplication", appContext.packageName)
    }

    @Test
    fun testSensitivitySeekBar(){
        scenario = launch(MainActivity::class.java)
        Espresso.onView(withId(R.id.seekBar)).perform(ViewActions.click())
        Espresso.onView(withId(R.id.textView))
            .check(matches(withText("50.0")))
        Thread.sleep(3000)
        scenario.close()
    }

    @Test
    fun testMotion(){
        scenario = launch(MainActivity::class.java)

        val device = UiDevice.getInstance(getInstrumentation())
        device.setOrientationNatural()
        device.setOrientationRight()
        device.setOrientationLeft()
        device.setOrientationNatural()

        scenario.close()
    }

}