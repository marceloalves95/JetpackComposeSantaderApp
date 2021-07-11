package br.com.jetpackcomposesantaderapp.data

import br.com.jetpackcomposesantaderapp.domain.Cartao
import br.com.jetpackcomposesantaderapp.domain.Cliente
import br.com.jetpackcomposesantaderapp.domain.Conta

class FakeData {

    val cliente = Cliente("Marcelo")
    val cartao = Cartao("41111111")

    fun getLocalData(): Conta = Conta("445655-4", "6552-4", "2450,80", "R$ 4.120,00", cliente, cartao)
}