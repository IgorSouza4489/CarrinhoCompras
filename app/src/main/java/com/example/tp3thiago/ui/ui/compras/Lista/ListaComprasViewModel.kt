package com.example.tp3thiago.ui.ui.compras.Lista

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tp3thiago.ui.Dao.authDao
import com.example.tp3thiago.ui.Dao.comprasDao
import com.example.tp3thiago.ui.model.Compras

class ListaComprasViewModel : ViewModel() {

    var compras = MutableLiveData<List<Compras>>()

    val status = MutableLiveData<Boolean>()
    val msg = MutableLiveData<String>()

    init {
        comprasDao.listar(authDao.getCurrentUser()!!.uid)
            .addSnapshotListener{snapshot, error ->
                if (error != null){
                    msg.value = error.message
                }

                if (snapshot != null){
                    compras.value = snapshot.toObjects(Compras::class.java)
                }
            }


    }

//    fun excluir() {
//        val task = comprasDao.excluir()
//        task.addOnSuccessListener {
//            status.value = true
//        }.addOnFailureListener {
//            msg.value = it.message
//        }
//    }

    fun signOut(){
        authDao.signOut()
    }

}