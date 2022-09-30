package com.example.tp3thiago.ui.Dao

import com.example.tp3thiago.ui.Dao.authDao.Companion.getCurrentUser
import com.example.tp3thiago.ui.model.Compras
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class comprasDao {

    companion object {
        private val collection = Firebase
            .firestore.collection("compras")

        //fun listar() = collection.get()

        fun excluir(documentId: String) = collection.document(documentId).delete()

        fun exibir(documentId: String) = collection.document(documentId)

        fun test() = Firebase.firestore.collection("profile").document(getCurrentUser()!!.uid).get()

        fun listar() = collection
        fun listar(userUid: String) = collection.whereEqualTo("userUid", userUid)

        fun inserir(compras: Compras) = collection.add(compras)
        fun atualizar(documentId: String, compras: Compras) = collection.document(documentId).set(compras)

        //fun excluir(documentId: String) = collection.document(documentId).delete()




        //fun exibir(documentId: String) = collection.document(documentId).get()
        //fun exibir(documentId: String) = collection.document(documentId)
        //fun excluir(documentId: String) = collection.document(documentId).delete()

        //fun atualizar(documentId: String, carro: Carro) =
        //collection.document(documentId).set(carro)

    }


}