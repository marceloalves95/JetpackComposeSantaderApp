package br.com.jetpackcomposesantaderapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import br.com.jetpackcomposesantaderapp.domain.Conta

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cliente:String
    private lateinit var agencia:String
    private lateinit var contaCorrente:String
    private lateinit var saldo:String
    private lateinit var limiteCartao:String
    private lateinit var cartao:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
        setupViewModel()
    }
    private fun setupViewModel(){
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.buscarContaCliente().observe(this) { conta -> bind(conta) }
    }
    private fun bind(conta:Conta){
        with(conta){
            this@MainActivity.cartao = cartao.numeroConta
            this@MainActivity.cliente = cliente.nome
            this@MainActivity.agencia = "Ag $agencia"
            contaCorrente = "CC $numero"
            this@MainActivity.saldo = "R$ $saldo"
            this@MainActivity.limiteCartao = "Saldo - Limite: $limite"
        }

    }

    @Preview
    @Composable
    private fun MainContent(){
        Scaffold(topBar = {MainTopBar()}){
            Field()
        }
    }
    @Composable
    private fun MainTopBar(){
        TopAppBar(
            elevation = 0.dp,
            title = { Logo()},
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.Menu, contentDescription = null,tint = colorResource(id = R.color.white))
                }
            },
            actions = {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_notifications),
                        contentDescription = null,
                        tint = colorResource(id = R.color.white))
                }
            },
            backgroundColor = colorResource(id = R.color.redSecondary),
            contentColor = Color.White)

    }
    @Composable
    private fun Logo(){
        Image(
            modifier = Modifier
                .height(56.dp)
                .fillMaxSize()
                .wrapContentSize(),
            painter = painterResource(id = R.drawable.santander_logo),
            contentDescription = null)
    }
    @Composable
    private fun ExpansableCard(){

        var expandedState by remember { mutableStateOf(false) }
        val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp, start = 16.dp, end = 16.dp)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            elevation = 12.dp
        ) {
            Column{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically){
                    Image(
                        modifier = Modifier.padding(16.dp),
                        painter = painterResource(id = R.drawable.ic_update),
                        contentDescription = null)
                    Text(
                        text = "Saldo Disponível",
                        fontSize=18.sp,
                        modifier = Modifier
                            .weight(6f)
                            .padding(end = 8.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis)
                    IconButton(
                        onClick = { expandedState = !expandedState },
                        modifier = Modifier
                            .alpha(ContentAlpha.medium)
                            .weight(1f)
                            .rotate(rotationState)) {
                        Icon(painter = painterResource(id = R.drawable.ic_expand_more), contentDescription = null)

                    }


                }
                if (expandedState) {

                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                        text = saldo,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp),
                        text = limiteCartao,
                        overflow = TextOverflow.Ellipsis
                    )
                    Divider(
                        modifier = Modifier.padding(16.dp),
                        color = Color.Gray, thickness = 1.dp
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 16.dp),
                            text = "Ver Extrato",
                            color = colorResource(id = R.color.redSecondary),
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

            }

        }
    }
    @Composable
    private fun Field(){
        Column(
            modifier = Modifier
                .height(110.dp)
                .background(colorResource(id = R.color.redSecondary))
                .fillMaxSize()){
            Text(
                text = "Olá, $cliente",
                color = colorResource(id = R.color.white),
                modifier = Modifier.padding(start = 16.dp, top = 16.dp))

            Row{
                Text(
                    text = agencia,
                    color = colorResource(id = R.color.white),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp))
                Text(
                    text = contaCorrente,
                    color = colorResource(id = R.color.white),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp))
            }

        }
        Column{
            ExpansableCard()
            Cards()
            CartaoFinal()

        }
        
    }
    @Composable
    private fun Cards() {
        Row(modifier = Modifier.padding(16.dp)) {
            Card(
                modifier = Modifier
                    .weight(2f)
                    .padding(end = 8.dp)
                    .clickable { },
                elevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_credit_card),
                        contentDescription = null,
                        modifier = Modifier.padding(top = 16.dp),
                        alignment = Alignment.Center
                    )

                    Text(
                        text = "Pagar",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }

            }
            Card(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp, end = 8.dp)
                    .clickable { },
                elevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_payments),
                        contentDescription = null,
                        modifier = Modifier.padding(top = 16.dp),
                        alignment = Alignment.Center
                    )
                    Text(
                        text = "Tranferir",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }

            }
            Card(
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 8.dp)
                    .clickable { },
                elevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.wrapContentSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(id = R.drawable.ic_stay_current_portrait),
                        contentDescription = null,
                        modifier = Modifier.padding(top = 16.dp),
                        alignment = Alignment.Center
                    )
                    Text(
                        text = "Recarregar",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }

            }


        }
    }
    @Composable
    private fun CartaoFinal(){
        Row(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .background(colorResource(id = R.color.redPrimary))
        ){
            Text(
                modifier= Modifier
                    .weight(5f)
                    .padding(16.dp),
                text = "Cartão Final: $cartao",
                color = colorResource(id = R.color.white))
            IconButton(
                modifier=Modifier.weight(1f),
                onClick = {  }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_expand_more), contentDescription = null,
                    tint = colorResource(id = R.color.white))

            }
        }
    }

}

