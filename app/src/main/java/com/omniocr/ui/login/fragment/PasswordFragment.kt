package com.omniocr.ui.login.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.omniocr.MainActivity
import com.omniocr.R
import com.omniocr.databinding.FragmentPasswordBinding
import com.omniocr.ui.login.LoginViewModel


class PasswordFragment : Fragment() {
    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding: FragmentPasswordBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_password, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.backPressed.observe(this, Observer {
            if (fragmentManager!!.backStackEntryCount > 0) {
                fragmentManager!!.popBackStack()
            }
        })

        viewModel.completePasswordFlow.observe(this, Observer {
            startActivity(Intent(activity, MainActivity::class.java))
        })
    }
}