package com.khaledamin.prayerapplication.navigation

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.khaledamin.prayerapplication.R
import com.khaledamin.prayerapplication.presentation.MainActivity
import com.khaledamin.prayerapplication.presentation.screens.home.HomeFragment
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testNavigationFromHomeFragmentToMapsFragment(){
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val titleScenario = launchFragmentInContainer<HomeFragment>()
        titleScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.navigation)
            Navigation.setViewNavController(fragment.requireView(),navController)
        }

        onView(ViewMatchers.withId(R.id.show_qibla_btn)).perform(click())
        assertEquals(R.id.mapsFragment,navController.currentDestination?.id)
    }
}