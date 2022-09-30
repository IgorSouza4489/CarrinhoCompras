package com.example.tp3thiago.ui.ui.forgotPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tp3thiago.R
import com.example.tp3thiago.databinding.FragmentForgotPasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class forgotPasswordFragment : Fragment() {

    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        viewModel.status.observe(viewLifecycleOwner){
            if (it)
                findNavController()
                    .navigate(R.id.signInFragment)

        }

        viewModel.msg.observe(viewLifecycleOwner){
            if (it.isNotBlank())
                Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }
        setup()

        return view
    }

    fun setup(){
        setupClickListeners()
    }

    fun setupClickListeners(){
        val email = binding.tvEmailRecuperar.text.toString().trim{it <= ' '}
        binding.btnEmailRecuperar.setOnClickListener {
            recuperarSenha(email)

        }

        binding.tvBack.setOnClickListener {

            findNavController().popBackStack()
        }
    }

    fun recuperarSenha(email: String){

        val email = binding.tvEmailRecuperar.text.toString().trim{it <= ' '}

        if (email.isNotEmpty()){
           viewModel.recuperarSenha(email)

        }
        else{
            Toast.makeText(requireContext(), "Insira o Email", Toast.LENGTH_LONG).show()
        }


        //findNavController().popBackStack()

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)
        // TODO: Use the ViewModel
    }

}