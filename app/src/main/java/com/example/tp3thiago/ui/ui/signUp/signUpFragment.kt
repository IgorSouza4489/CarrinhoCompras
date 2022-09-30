package com.example.tp3thiago.ui.ui.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tp3thiago.R
import com.example.tp3thiago.databinding.FragmentSignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class signUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        viewModel.status.observe(viewLifecycleOwner) {
            if (it)
                //findNavController().popBackStack()
                findNavController().navigate(R.id.listaComprasFragment)
        }
        viewModel.msg.observe(viewLifecycleOwner){
            if (it.isNotBlank())
                Snackbar.make(
                    view, it, Snackbar.LENGTH_LONG
                ).show()
        }
        setup()

        return view
    }

    fun setup(){
        setupClickListener()
    }


    fun setupClickListener(){
        binding.btnRegistrar.setOnClickListener{
            register()
        }

        binding.imageBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }



    fun register(){
        val nome = binding.inputNome.text.toString()
        val email = binding.inputEmail1.text.toString()
        val senha = binding.inputSenha1.text.toString()
        val confirm = binding.inputConfirmar1.text.toString()
        if (email.isNotEmpty() && senha.isNotEmpty() && confirm.isNotEmpty()){
            if (confirm == senha) {
                viewModel.salvarUsuario(email, senha, nome)
                //findNavController().navigate(R.id.listaComprasFragment)
            }
            else{
                Toast.makeText(requireContext(), "As senhas devem coincidir", Toast.LENGTH_LONG).show()

            }

        }
        else{
            Toast.makeText(requireContext(), "Insira os campos", Toast.LENGTH_LONG).show()
        }

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        // TODO: Use the ViewModel
    }

}