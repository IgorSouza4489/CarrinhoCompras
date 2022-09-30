package com.example.tp3thiago.ui.ui.signUp

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tp3thiago.ui.Dao.authDao

class SignUpViewModel : ViewModel() {
    val status = MutableLiveData<Boolean>()
    val msg = MutableLiveData<String>()

    init {
        status.value = false
    }

    fun salvarUsuario(
        email: String, senha: String, nome: String
    ){

        val task = authDao.cadastrarUsuario(email, senha)

        task.addOnSuccessListener {
           if (task.isSuccessful){
               authDao.profilecreateFirestore(email, nome)
               status.value = true
               Log.d("user", "Usuário cadastrado")
           }
        }.addOnFailureListener {
            msg.value = it.message
        }

    }



}