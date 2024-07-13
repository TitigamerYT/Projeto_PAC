import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantefastio2.db.RestauranteFastioBase
import com.example.restaurantefastio2.db.conta
import com.example.restaurantefastio2.db.mesas
import com.example.restaurantefastio2.db.pedidos_mesa
import com.example.restaurantefastio2.db.produto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ViewModel(application: Application) : AndroidViewModel(application) {
    private val mesasDao = RestauranteFastioBase.getDatabase(application).mesasDao()
    private val produtoDao = RestauranteFastioBase.getDatabase(application).produtosDao()
    private val pedidosmesasDao = RestauranteFastioBase.getDatabase(application).pedidosmesasDao()
    private val contaDao = RestauranteFastioBase.getDatabase(application).contaDao()

    private val _mesas = MutableStateFlow<List<mesas>>(emptyList())
    val mesas: StateFlow<List<mesas>> = _mesas

    private val _produto = MutableStateFlow<List<produto>>(emptyList())
    val produto: StateFlow<List<produto>> = _produto

    private val _pedidos_mesas = MutableStateFlow<List<pedidos_mesa>>(emptyList())
    val pedidos_mesas: StateFlow<List<pedidos_mesa>> = _pedidos_mesas

    private val _conta = MutableStateFlow<List<conta>>(emptyList())
    val conta: StateFlow<List<conta>> = _conta

    init {
        viewModelScope.launch {
            _mesas.value = mesasDao.getAllMesas()
            _produto.value = produtoDao.getAllProdutos()
            _pedidos_mesas.value = pedidosmesasDao.getAllPedidos()
            inicializarProdutos()
            loadPedidosMesas()
        }
    }

    private suspend fun inicializarProdutos() {
        val produtosIniciais = listOf(
            produto(produto = "Pão", preco = 1.50),
            produto(produto = "Azeitonas", preco = 1.20),
            produto(produto = "Manteigas e Patês", preco = 1.80),
            produto(produto = "Tábua de Enchidos", preco = 3.50),
            produto(produto = "Tábua de Queijos", preco = 3.00),
            produto(produto = "Saladas Frias", preco = 2.00),
            produto(produto = "Camarão", preco = 4.50),
            produto(produto = "Vinho Monte Velho", preco = 9.50),
            produto(produto = "Vinho Casal Garcia", preco = 10.00),
            produto(produto = "Vinho Vidigueira", preco = 11.00),
            produto(produto = "Vinho Reguengos", preco = 10.00),
            produto(produto = "Vinho Vinha da Foz", preco = 12.00),
            produto(produto = "Vinho Piano Reserva", preco = 14.00),
            produto(produto = "Vinho Porta da Ravessa", preco = 9.00),
            produto(produto = "Vinho Bafarela Reserva", preco = 14.00),
            produto(produto = "Vinho Grandes Quintas Reserva", preco = 34.00),
            produto(produto = "Vinho Quinta da Foz Reserva", preco = 20.00),
            produto(produto = "Vinho Quinta da Sequeira Grande Reserva Branco", preco = 33.50),
            produto(produto = "Cerveja", preco = 1.50),
            produto(produto = "Refrigerantes", preco = 1.50),
            produto(produto = "Sangria", preco = 6.00),
            produto(produto = "Água", preco = 1.50),
            produto(produto = "Abanicos de porco preto", preco = 22.00),
            produto(produto = "Lagartos de porco preto", preco = 20.00),
            produto(produto = "Plumas de porco preto", preco = 20.00),
            produto(produto = "Secretos de porco preto", preco = 20.00),
            produto(produto = "Bochechas de porco preto", preco = 18.00),
            produto(produto = "Costeletas de borrego com migas", preco = 24.00),
            produto(produto = "Costeletas de vitela", preco = 25.00),
            produto(produto = "Bifinhos de peru/frango", preco = 15.00),
            produto(produto = "Bitoque de porco", preco = 11.00),
            produto(produto = "Carne de porco à alentejana", preco = 24.00),
            produto(produto = "Febras de porco preto", preco = 13.00),
            produto(produto = "Picanha na grelha", preco = 28.00),
            produto(produto = "Bife da Vazia Grelhado", preco = 15.00),
            produto(produto = "Bacalhau à brás", preco = 15.00),
            produto(produto = "Bacalhau com natas", preco = 15.00),
            produto(produto = "Polvo à lagareiro", preco = 30.00),
            produto(produto = "Lulas grelhadas", preco = 26.00),
            produto(produto = "Robalo grelhado", preco = 12.00),
            produto(produto = "Dourada grelhada", preco = 13.00),
            produto(produto = "Bacalhau assado", preco = 25.00),
            produto(produto = "Arroz de tanboril", preco = 26.00),
            produto(produto = "Espeto de gambas", preco = 29.00),
            produto(produto = "Chocos fritos", preco = 16.00),
            produto(produto = "Medalhões de pescada", preco = 24.00),
            produto(produto = "Açorda de camarão", preco = 25.00),
            produto(produto = "Feijoada de camarão", preco = 25.00),
            produto(produto = "Doce da casa", preco = 3.50),
            produto(produto = "Mousse de chocolate", preco = 3.50),
            produto(produto = "Baba de camelo", preco = 3.50),
            produto(produto = "Pudim caseiro", preco = 3.50),
            produto(produto = "Bolo de bolacha", preco = 3.50),
            produto(produto = "Mousse de café", preco = 3.50),
            produto(produto = "Tiramisù", preco = 4.50),
            produto(produto = "Doce de amendoa", preco = 3.80),
            produto(produto = "Semi-frio de limão", preco = 3.80),
            produto(produto = "Salada de frutas", preco = 3.50),
            produto(produto = "Semi-frio de frutos vermelhos", preco = 3.80),
            produto(produto = "Café", preco = 1.00),
            produto(produto = "Amarguinha", preco = 2.50),
            produto(produto = "Bagaço", preco = 1.50),
            produto(produto = "Licor Beirão", preco = 2.50),
            produto(produto = "Esparguete à bolonhesa", preco = 9.50),
            produto(produto = "Tagliatelle de quatro queijos", preco = 12.00),
            produto(produto = "Carbonara", preco = 9.50),
            produto(produto = "Lasanha", preco = 12.00),
            produto(produto = "Raviolli com recheio de carne", preco = 14.00),
            produto(produto = "Pizza margueritta", preco = 10.00),
            produto(produto = "Pizza de quatro estações", preco = 12.00),
            produto(produto = "Pizza de quatro queijos", preco = 12.00),
            produto(produto = "Pizza frutos do mar", preco = 12.00),
            produto(produto = "Calzone", preco = 15.00),
            produto(produto = "Pizza vegetariana", preco = 10.00),
            produto(produto = "Pizza de pepperoni", preco = 12.00),
            produto(produto = "Pizza burrata e pesto", preco = 13.00)
        )

        produtoDao.deleteAll()
        produtoDao.insertAll(produtosIniciais)
    }


    fun adicionarProduto(nome: String, preco: Double) {
        viewModelScope.launch {
            val novoProduto = produto(produto = nome, preco = preco)
            produtoDao.insert(novoProduto)
        }
    }

    fun addPedidoMesa(id_mesa: Int, produto: String, categoria: String, quant: Int, notas: String, soma_pedido: Double) {
        viewModelScope.launch {
            pedidosmesasDao.insert(pedidos_mesa(0, id_mesa, produto, categoria, quant, notas, soma_pedido))
            _pedidos_mesas.value = pedidosmesasDao.getAllPedidos()  // Atualiza o StateFlow com todos os pedidos
        }
    }

    fun carregarPedidosMesa(idMesa: Int) {
        viewModelScope.launch {
            _pedidos_mesas.value = pedidosmesasDao.getPedidosByMesa(idMesa)
        }
    }

    suspend fun loadPedidosMesas() {
        _pedidos_mesas.value = pedidosmesasDao.getAllPedidos()
    }

    fun deletePedidosMesaByIdMesa(id_mesa: Int) {
        viewModelScope.launch {
            pedidosmesasDao.deletePedidosByMesa(id_mesa)
            _pedidos_mesas.value = pedidosmesasDao.getAllPedidos()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addConta(id_mesa: Int, conta: Double, data_pagamento: LocalDateTime) {
        viewModelScope.launch {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formattedDataPagamento = data_pagamento.format(formatter)
            contaDao.insert(conta(id_mesa = id_mesa, conta = conta, data_pagamento = formattedDataPagamento))
            _conta.value = contaDao.getAllConta()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun mostrarConta() {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dataAtual = LocalDateTime.now().format(formatter)
        _conta.value = contaDao.getContasByData(dataAtual)
    }
}



