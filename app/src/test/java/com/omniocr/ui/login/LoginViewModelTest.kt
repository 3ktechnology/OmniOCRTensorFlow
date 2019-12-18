package com.omniocr.ui.login

import android.app.Application
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Before
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var application:Application

    @InjectMocks
    lateinit var loginViewModel : LoginViewModel

    @Mock
    lateinit var view : View


    @Before
    fun setUp(){
        application = Mockito.mock(Application::class.java)
        view = Mockito.mock(View::class.java)
        loginViewModel = LoginViewModel(application)
    }


    @Test
    fun emailIdLength_String_Void(){
        loginViewModel.setEmailID("abc@gmail.com")
        assertEquals(13,loginViewModel.emailLength.value)
    }

    @Test
    fun ValidEmailId_String_Void(){
        loginViewModel.checkEmailValidation("abc@gmail.com")
        assertEquals(true,loginViewModel.isEmailValid.value)
    }

    /**
     * make relative changes in java class: add  "validatePassword" method in login view model
     */
    @Test
    fun validatePassword_passwordString_Void(){
        loginViewModel.validatePassword("12345")
        assertEquals(5,loginViewModel.passwordLength.value)
        assertEquals(true,loginViewModel.isPasswordValid.value)

    }


    @Test
    fun onNextClicked_View_Void(){
        loginViewModel.onNextClicked(view)
        assertEquals(true,loginViewModel.completeEmailFlow.value)

    }
    @Test
    fun onLoginClicked_View_Void(){
        loginViewModel.onLoginClicked(view)
        assertEquals(true,loginViewModel.completePasswordFlow.value)

    }

    @Test
    fun onBackClicked_View_Void(){
        loginViewModel.onBackClicked(view)
        assertEquals(true,loginViewModel.backPressed.value)

    }
}