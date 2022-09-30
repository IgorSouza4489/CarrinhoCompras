package com.example.tp3thiago

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3thiago.ui.api.ApiClient
import com.example.tp3thiago.ui.api.FrutasService
import com.example.tp3thiago.ui.model.FrutaResponse
import com.example.tp3thiago.ui.model.FrutasDto
import com.example.tp3thiago.ui.model.frutas
import kotlinx.coroutines.launch

class FrutasListViewModel : ViewModel() {
    val frutas = MutableLiveData<List<frutas>?>()



    init {
        viewModelScope.launch {
            val fi = ApiClient
            val fs = fi.frutasService()
            val fResult = fs.getFrutas()
            frutas.postValue(fResult)
        }
        }
    }
