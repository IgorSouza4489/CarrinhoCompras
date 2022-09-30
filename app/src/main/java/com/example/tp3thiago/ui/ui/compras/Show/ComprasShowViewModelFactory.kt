package com.example.tp3thiago.ui.ui.compras.Show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ComprasShowViewModelFactory(val documentId: String)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ComprasShowViewModel(documentId) as T
    }
}