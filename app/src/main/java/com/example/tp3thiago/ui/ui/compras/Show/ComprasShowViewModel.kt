package com.example.tp3thiago.ui.ui.compras.Show

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tp3thiago.ui.Dao.authDao
import com.example.tp3thiago.ui.Dao.comprasDao
import com.example.tp3thiago.ui.model.Compras

class ComprasShowViewModel(val documentId: String) : ViewModel() {

    val compras = MutableLiveData<Compras>()
    val status = MutableLiveData<Boolean>()
    val msg = MutableLiveData<String>()

    init {
        comprasDao.exibir(documentId)
            .addSnapshotListener { snapshot, error ->
                if (error != null)
                    msg.value = error.message
                if (snapshot != null)
                    compras.value = snapshot.toObject(Compras::class.java)
            }
    }


    fun atualizar(item: String, quantidade: String, preco: String, categoria: String){


        val userUid = authDao.getCurrentUser()!!.uid
        comprasDao.test().addOnSuccessListener { documento ->
            if (documento.exists()){
                val nome = documento.getString("nome")
                Log.d("TAG", "Documento existe")
                val compras = Compras(item, quantidade, preco, categoria, userUid, nome)
                val task = comprasDao.atualizar(documentId!!, compras)

                task.addOnSuccessListener {
                    msg.value = "Item atualizado com sucesso."
                    status.value = true
                }.addOnFailureListener {
                    msg.value = "Erro: Item não foi atualizado"
                }
            }
            else{
                Log.d("TAG", "Documento NAO existe")
            }
        }.addOnFailureListener{

        }

        }






    fun excluir() {
        val task = comprasDao.excluir(documentId)
        task.addOnSuccessListener {
            status.value = true
        }.addOnFailureListener {
            msg.value = it.message
        }
    }
}