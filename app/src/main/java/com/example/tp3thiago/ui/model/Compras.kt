package com.example.tp3thiago.ui.model

import com.google.firebase.firestore.DocumentId

class Compras(


    val item: String? = null,
    val quantidade: String? = null,
    val preco: String? = null,
    val categoria: String? = null,
    val userUid: String? = null,
    var precototal: String? = null,

    @DocumentId
    val documentId: String? = null


) {

    override fun toString() = "${item}"
}