package Model;

import Exception.CancelamentoNaoPermitidoException;
import Exception.StatusInvalidoException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private final int id;
    private final Cliente cliente;
    private final LocalDateTime dataHora;
    private final List<ItemPedido> itens;
    private StatusPedido status;
    public Pedido(int id, Cliente cliente, List<ItemPedido> itens) {
        this.id = id;
        this.cliente = cliente;
        this.dataHora = LocalDateTime.now();
        this.itens = new ArrayList<>(itens);
        this.status = StatusPedido.AGUARDANDO_CONFIRMACAO;
    }
    public int getId() {
        return id;
    }
    public Cliente getCliente() {
        return cliente;
    }
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public List<ItemPedido> getItens() {
        return itens;
    }
    public StatusPedido getStatus() {
        return status;
    }
    public double getValorTotal() {
        double total = 0;
        for (ItemPedido ip : itens) {
            total += ip.getSubtotal();
        }
        return total;
    }
    public boolean contemItem(int itemId) {
        return itens.stream().anyMatch(ip -> ip.getItem().getId() == itemId);
    }

    public void avancarStatus() throws StatusInvalidoException {
        StatusPedido proximo = status.proximo();
        if (proximo == null) {
            throw new StatusInvalidoException(
                    "O pedido #" + id + " já está em status final (" + status + ").");
        }
        status = proximo;
    }
    public void cancelar() throws CancelamentoNaoPermitidoException {
        if (status != StatusPedido.AGUARDANDO_CONFIRMACAO) {
            throw new CancelamentoNaoPermitidoException(
                    "Só é possível cancelar pedidos com status AGUARDANDO_CONFIRMACAO. Status atual: " + status);
        }
        status = StatusPedido.CANCELADO;
    }
    public boolean isDeHoje() {
        LocalDateTime agora = LocalDateTime.now();
        return dataHora.toLocalDate().isEqual(agora.toLocalDate());
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Pedido #%d | Cliente: %s | Data: %s | Status: %s%n",
                id, cliente.getNome(), dataHora.format(FORMATO_DATA), status));
        for (ItemPedido ip : itens) {
            sb.append("   - ").append(ip).append("\n");
        }
        sb.append(String.format("   Valor total: R$ %.2f", getValorTotal()));
        return sb.toString();
    }
}
