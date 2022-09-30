package com.example.tp3thiago.ui.ui.forgotPassword

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tp3thiago.ui.Dao.authDao

class ForgotPasswordViewModel : ViewModel() {
    val status = MutableLiveData<Boolean>()
    val msg = MutableLiveData<String>()

    init {
        status.value = false
    }


    fun recuperarSenha(email: String){
        val task = authDao.recuperarSenha(email)
        task.addOnSuccessListener {
            status.value = true

        }.addOnFailureListener{
            msg.value = it.message
        }
    }
}