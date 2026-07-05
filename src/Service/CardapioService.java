package Service;

import Exception.ItemVinculadoException;
import Exception.PrecoInvalidoException;
import Model.CategoriaItem;
import Model.ItemCardapio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardapioService {

    private final List<ItemCardapio> itens = new ArrayList<>();
    private int proximoId = 1;
    private final PedidoService pedidoController;

    public CardapioService(PedidoService pedidoController) {
        this.pedidoController = pedidoController;
    }

    public ItemCardapio cadastrarItem(String nome, String descricao, CategoriaItem categoria,
                                       double preco, boolean disponivel) throws PrecoInvalidoException {
        ItemCardapio item = new ItemCardapio(proximoId++, nome, descricao, categoria, preco, disponivel);
        itens.add(item);
        return item;
    }

    public List<ItemCardapio> getTodosItens() {
        return itens;
    }

    public List<ItemCardapio> getItensDisponiveis() {
        List<ItemCardapio> disponiveis = new ArrayList<>();
        for (ItemCardapio item : itens) {
            if (item.isDisponivel()) disponiveis.add(item);
        }
        return disponiveis;
    }

    public Optional<ItemCardapio> buscarItem(int id) {
        return itens.stream().filter(i -> i.getId() == id).findFirst();
    }

    public void editarItem(int id, String nome, String descricao, CategoriaItem categoria,
                            double preco, boolean disponivel) throws ItemVinculadoException, PrecoInvalidoException {
        ItemCardapio item = buscarItem(id)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado: #" + id));
        if (pedidoController.itemVinculadoAPedidoAberto(id)) {
            throw new ItemVinculadoException(
                    "Não é possível editar o item #" + id + ", ele está vinculado a um pedido em aberto");
        }
        item.setNome(nome);
        item.setDescricao(descricao);
        item.setCategoria(categoria);
        item.setPreco(preco);
        item.setDisponivel(disponivel);
    }

    public void excluirItem(int id) throws ItemVinculadoException {
        ItemCardapio item = buscarItem(id)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado: #" + id));
        if (pedidoController.itemVinculadoAPedidoAberto(id)) {
            throw new ItemVinculadoException(
                    "Não é possível excluir o item #" + id + ": está vinculado a um pedido em aberto.");
        }
        itens.remove(item);
    }
}
