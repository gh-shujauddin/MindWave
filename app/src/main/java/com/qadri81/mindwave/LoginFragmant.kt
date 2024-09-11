package com.qadri81.mindwave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.qadri81.mindwave.databinding.FragmentLoginBinding
import com.qadri81.mindwave.models.UserRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragmant : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener {
            authViewModel.loginUser(UserRequest("test@gmail.com", "123456", "Tester"))

//            findNavController().navigate(R.id.action_loginFragmant_to_mainFragment)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}