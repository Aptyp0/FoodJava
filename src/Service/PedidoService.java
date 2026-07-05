package Service;

import Exception.CancelamentoNaoPermitidoException;
import Exception.CarrinhoVazioException;
import Exception.StatusInvalidoException;
import Model.Cliente;
import Model.ItemPedido;
import Model.Pedido;
import Model.StatusPedido;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PedidoService {
    public static class ResumoDia {
        public final int totalPedidos;
        public final double faturamento;
        public ResumoDia(int totalPedidos, double faturamento) {
            this.totalPedidos = totalPedidos;
            this.faturamento = faturamento;
        }
    }
    private final List<Pedido> pedidos = new ArrayList<>();
    private int proximoId = 1;
    public Pedido criarPedido(Cliente cliente, List<ItemPedido> carrinho) throws CarrinhoVazioException {
        if (carrinho == null || carrinho.isEmpty()) {
            throw new CarrinhoVazioException("O carrinho está vazio");
        }
        Pedido pedido = new Pedido(proximoId++, cliente, carrinho);
        pedidos.add(pedido);
        return pedido;
    }
    public List<Pedido> getPedidosDoCliente(Cliente cliente) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.getCliente().getId() == cliente.getId()) resultado.add(p);
        }
        return resultado;
    }
    public List<Pedido> getTodosPedidos() {
        return pedidos;
    }
    public List<Pedido> filtrarPorStatus(StatusPedido status) {
        List<Pedido> resultado = new ArrayList<>();
        for (Pedido p : pedidos) {
            if (p.getStatus() == status) resultado.add(p);
        }
        return resultado;
    }
    public Optional<Pedido> buscarPedido(int id) {
        return pedidos.stream().filter(p -> p.getId() == id).findFirst();
    }
    public boolean itemVinculadoAPedidoAberto(int itemId) {
        for (Pedido p : pedidos) {
            if (!p.getStatus().isFinal() && p.contemItem(itemId)) {
                return true;
            }
        }
        return false;
    }
    public void avancarStatus(int idPedido) throws StatusInvalidoException {
        Pedido pedido = buscarPedido(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: #" + idPedido));
        pedido.avancarStatus();
    }
    public void cancelarPedidoComoCliente(int idPedido, Cliente cliente) throws CancelamentoNaoPermitidoException {
        Pedido pedido = buscarPedido(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: #" + idPedido));
        if (pedido.getCliente().getId() != cliente.getId()) {
            throw new IllegalArgumentException("Este pedido não pertence a este cliente");
        }
        pedido.cancelar();
    }
    public void cancelarPedidoComoGerente(int idPedido) throws CancelamentoNaoPermitidoException {
        Pedido pedido = buscarPedido(idPedido)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado: #" + idPedido));
        pedido.cancelar();
    }
    public ResumoDia resumoDoDia() {
        int total = 0;
        double faturamento = 0;
        for (Pedido p : pedidos) {
            if (p.isDeHoje()) {
                total++;
                if (p.getStatus() == StatusPedido.ENTREGUE) {
                    faturamento += p.getValorTotal();
                }
            }
        }
        return new ResumoDia(total, faturamento);
    }
}
