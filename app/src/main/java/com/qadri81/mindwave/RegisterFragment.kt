package com.qadri81.mindwave

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.qadri81.mindwave.databinding.FragmentRegisterBinding
import com.qadri81.mindwave.models.UserRequest
import com.qadri81.mindwave.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        binding.btnLogin?.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragmant)
        }
        binding.btnSignup?.setOnClickListener {
            authViewModel.registerUser(UserRequest("test@gmail.com", "123456", "Tester"))

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel.userResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar?.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    //Add Token later
                    findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
                }

                is NetworkResult.Error -> {
                    binding.txtError?.text = it.message
                }

                is NetworkResult.Loading -> {
                    binding.progressBar?.isVisible = true
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}