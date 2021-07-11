package br.com.jetpackcomposesantaderapp.domain

/**
 * Created by RubioAlves on 27/05/2021
 */
data class Conta(
    val numero:String,
    val agencia:String,
    val saldo:String,
    val limite:String,
    val cliente: Cliente,
    val cartao: Cartao
)
