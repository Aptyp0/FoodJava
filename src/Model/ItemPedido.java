package Model;

public class ItemPedido {
    private final ItemCardapio item;
    private int quantidade;

    public ItemPedido(ItemCardapio item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }
    public ItemCardapio getItem() {
        return item;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public void adicionarQuantidade(int qtd) {
        this.quantidade += qtd;
    }
    public double getSubtotal() {
        return item.getPreco() * quantidade;
    }
    @Override
    public String toString() {
        return String.format("%dx %s - R$ %.2f", quantidade, item.getNome(), getSubtotal());
    }
}
