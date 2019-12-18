package com.omniocr.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.omniocr.R
import com.omniocr.ui.login.fragment.EmailFragment

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /* window.setFlags(
             WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN
         )*/
        setContentView(R.layout.activity_login)
        goToLoginFragment()
    }

    private fun goToLoginFragment() {
        val fragment = EmailFragment()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
    }
}