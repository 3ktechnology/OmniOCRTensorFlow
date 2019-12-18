package com.omniocr.ui.login

import android.app.Application
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.omniocr.data.model.LoginModel
import com.omniocr.util.Utility

class LoginViewModel(val app: Application) : AndroidViewModel(app) {
    var isEmailValid = MutableLiveData<Boolean>()
    var emailLength = MutableLiveData<Int>()
    var isPasswordValid = MutableLiveData<Boolean>()
    var passwordLength = MutableLiveData<Int>()
    var model = LoginModel()
    var completeEmailFlow = MutableLiveData<Boolean>()
    var completePasswordFlow = MutableLiveData<Boolean>()
    var backPressed = MutableLiveData<Boolean>()


    fun getEmailId(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
               setEmailID(editable.toString())
                //isEmailValid.value = (Utility.isValidEmail1(editable.toString()))
                checkEmailValidation(editable.toString())
                model.email = editable.toString()

            }
        }
    }

     fun setEmailID(email: String) {
        emailLength.value = email.length
    }

    fun checkEmailValidation(email: String){
        isEmailValid.value = (Utility.isValidEmail1(email))
    }

    fun getPassword(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                /****
                 * move the commented lines to a separate method validatePassword
                 * and call that method here
                 */
                validatePassword(editable.toString())
//                isPasswordValid.value = editable.toString().isNotEmpty()
//                passwordLength.value = editable.toString().length
                model.password = editable.toString()

            }
        }
    }

    fun validatePassword(password: String) {

        isPasswordValid.value = password.isNotEmpty()
        passwordLength.value = password.toString().length
    }

    fun onNextClicked(view: View) {
        completeEmailFlow.value = true

    }

    fun onLoginClicked(view: View) {
        completePasswordFlow.value = true

    }

    fun onBackClicked(view: View) {
        backPressed.value = true
    }


}