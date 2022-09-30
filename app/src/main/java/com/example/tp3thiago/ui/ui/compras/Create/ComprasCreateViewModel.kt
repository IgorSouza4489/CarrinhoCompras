package com.example.tp3thiago.ui.ui.compras.Create

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tp3thiago.ui.Dao.authDao
import com.example.tp3thiago.ui.Dao.comprasDao
import com.example.tp3thiago.ui.model.Compras

class ComprasCreateViewModel : ViewModel() {

    var status = MutableLiveData<Boolean>()
    var msg = MutableLiveData<String>()




    fun inserir(item: String, quantidade: String, preco: String, categoria: String) {
        val userUid = authDao.getCurrentUser()!!.uid
        comprasDao.test().addOnSuccessListener { documento ->
            if (documento.exists()){
                val nome = documento.getString("nome")
                Log.d("TAG", "Documento existe")
                val compras = Compras(item, quantidade, preco, categoria, userUid, nome)
                val task = comprasDao.inserir(compras)

                task.addOnSuccessListener {
                    msg.value = "Item criada com sucesso."
                    status.value = true
                }.addOnFailureListener {
                    msg.value = it.message
                }
            }
            else{
                Log.d("TAG", "Documento NAO existe")
            }
        }.addOnFailureListener{

        }


        //val nome =

    }

}