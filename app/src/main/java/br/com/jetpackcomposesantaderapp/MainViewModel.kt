package br.com.jetpackcomposesantaderapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.jetpackcomposesantaderapp.data.FakeData
import br.com.jetpackcomposesantaderapp.domain.Conta

class MainViewModel:ViewModel() {

    private val mutableLiveData:MutableLiveData<Conta> = MutableLiveData()

    fun buscarContaCliente():LiveData<Conta>{

        mutableLiveData.value = FakeData().getLocalData()

        return mutableLiveData

    }
}