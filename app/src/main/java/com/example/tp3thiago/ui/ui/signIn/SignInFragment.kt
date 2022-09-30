package com.example.tp3thiago.ui.ui.signIn

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tp3thiago.R
import com.example.tp3thiago.databinding.FragmentSignInBinding
import com.example.tp3thiago.ui.Dao.authDao
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {


    private var callbackManager: CallbackManager? = null
    private var _binding: FragmentSignInBinding? = null
    private lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        callbackManager = CallbackManager.Factory.create()
        auth = Firebase.auth


        binding.loginButton.setReadPermissions("email", "public_profile")
        binding.loginButton.registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                //Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)


            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
                Log.d(TAG, "facebook:onError", error)
            }
        })

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        viewModel.status.observe(viewLifecycleOwner){
            if (it)
                findNavController()
                    .navigate(R.id.listaComprasFragment)
        }

        viewModel.msg.observe(viewLifecycleOwner){
            if (it.isNotBlank())
                Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }




        setup()
        return view
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        //Log.d(TAG, "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Log.d(TAG, "signInWithCredential:success");
                    val user = auth.currentUser

                    if (user != null) {
                        //Toast.makeText(requireContext(), user.email, Toast.LENGTH_LONG).show()
                        Log.i(TAG, "email" + user.getEmail());
                        //--------------Here is the UID
                        //Toast.makeText(requireContext(), user.uid, Toast.LENGTH_LONG).show()
                        Log.i(TAG, "uid" + user.getUid());
                        val userid = user.uid.toString()
                        authDao.profilecreateFacebook(userid)
                    }



                   // val userUid: String = token.userId
                    findNavController().navigate(R.id.listaComprasFragment)

                    //checkiflogged()
                    //Toast.makeText(requireContext(), user!!.email, Toast.LENGTH_LONG).show()
                } else {

                    Toast.makeText(requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }


    fun setup(){
        checkiflogged()
        setupClickListeners()
    }

    fun setupClickListeners(){
        binding.btnRegistrar.setOnClickListener{
            login()
        }

        binding.tvRegister.setOnClickListener{
            findNavController().navigate(R.id.signUpFragment)
        }

        binding.imageRegister.setOnClickListener{
            findNavController().navigate(R.id.signUpFragment)
        }

        binding.tvEsquecerSenha.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordFragment)
        }
    }

    fun checkiflogged(){
        FirebaseAuth.AuthStateListener { firebaseAuth ->
        val current_user = firebaseAuth.currentUser
        if (current_user != null){
            findNavController().navigate(R.id.listaComprasFragment)
        }
        }

    }


    fun login(){
        val email = binding.inputEmail1.text.toString()
        val senha = binding.inputSenha1.text.toString()

        if (email.isNotEmpty() && senha.isNotEmpty()){
            viewModel.autenticar(email, senha)
        }
        else{
            Toast.makeText(requireContext(), "Insira os campos", Toast.LENGTH_LONG).show()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }


}