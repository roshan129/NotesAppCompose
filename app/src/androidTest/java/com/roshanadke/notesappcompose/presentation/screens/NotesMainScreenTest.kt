package com.roshanadke.notesappcompose.presentation.screens

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.roshanadke.notesappcompose.presentation.MainActivity
import com.roshanadke.notesappcompose.presentation.theme.NotesAppComposeTheme
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


class NotesMainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    //val composeRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Test
    fun sampleTest() {
        //composeRule.onNodeWithText("Sample").assertExists()
    }

    @Test
    fun notesScreen_VerifyDestination() {
        composeTestRule.onNodeWithText("Notes").assertExists()
    }

}