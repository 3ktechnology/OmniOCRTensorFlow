package com.omniocr.ui.login.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.omniocr.R
import com.omniocr.databinding.FragmentEmailBinding
import com.omniocr.ui.login.LoginViewModel

class EmailFragment : Fragment() {
    lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding: FragmentEmailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_email, container, false)
        binding.lifecycleOwner = this
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.completeEmailFlow.observe(this, Observer {
            if (it) {
                viewModel.completeEmailFlow.value = false
                val fragment = PasswordFragment()
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
    }
}