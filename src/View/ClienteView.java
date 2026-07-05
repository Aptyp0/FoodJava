package View;

import Service.CardapioService;
import Service.PedidoService;
import Exception.CancelamentoNaoPermitidoException;
import Exception.CarrinhoVazioException;
import Model.Cliente;
import Model.ItemCardapio;
import Model.ItemPedido;
import Model.Pedido;
import java.util.ArrayList;
import java.util.List;

public class ClienteView {
    private final CardapioService cardapioService;
    private final PedidoService pedidoService;
    public ClienteView(CardapioService cardapioService, PedidoService pedidoService) {
        this.cardapioService = cardapioService;
        this.pedidoService = pedidoService;
    }
    public void executar(Cliente cliente) {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n=== Menu do cliente (" + cliente.getNome() + ") ===");
            System.out.println("1. Ver meus pedidos");
            System.out.println("2. Ver cardápio");
            System.out.println("3. Sair e voltar para a tela de login");
            int opcao = Terminal.lerOpcao(1, 3);
            switch (opcao) {
                case 1: verPedidosCliente(cliente); break;
                case 2: verCardapioCliente(cliente); break;
                case 3: sair = true; break;
            }
        }
    }
    private void verPedidosCliente(Cliente cliente) {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n=== Meus pedidos ===");
            List<Pedido> pedidos = pedidoService.getPedidosDoCliente(cliente);
            if (pedidos.isEmpty()) {
                System.out.println("Você ainda não fez nenhum pedido");
                return;
            }
            Terminal.listarPedidos(pedidos);
            System.out.println("\n1. Cancelar um pedido (só é possível se o status for AGUARDANDO_CONFIRMACAO)");
            System.out.println("2. Voltar");
            int opcao = Terminal.lerOpcao(1, 2);
            if (opcao == 1) {
                int id = Terminal.lerInteiro("Digite o número do pedido que quer cancelar: ");
                try {
                    pedidoService.cancelarPedidoComoCliente(id, cliente);
                    System.out.println("Pedido cancelado com sucesso");
                } catch (CancelamentoNaoPermitidoException | IllegalArgumentException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            } else {
                voltar = true;
            }
        }
    }
    private void verCardapioCliente(Cliente cliente) {
        List<ItemPedido> carrinho = new ArrayList<>();
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n=== Cardápio ===");
            List<ItemCardapio> disponiveis = cardapioService.getItensDisponiveis();
            Terminal.listarCardapio(disponiveis);

            System.out.println("\n=== Carrinho atual ===");
            Terminal.mostrarCarrinho(carrinho);

            System.out.println("\n1. Adicionar item ao carrinho");
            System.out.println("2. Remover item do carrinho");
            System.out.println("3. Confirmar pedido");
            System.out.println("4. Voltar e cancelar alterações");
            int opcao = Terminal.lerOpcao(1, 4);

            switch (opcao) {
                case 1: adicionarAoCarrinho(carrinho, disponiveis); break;
                case 2: removerDoCarrinho(carrinho); break;
                case 3:
                    if (confirmarPedido(cliente, carrinho)) {
                        voltar = true;
                    }
                    break;
                case 4: voltar = true; break;
            }
        }
    }
    private void adicionarAoCarrinho(List<ItemPedido> carrinho, List<ItemCardapio> disponiveis) {
        if (disponiveis.isEmpty()) {
            System.out.println("Não tem itens disponíveis no cardápio");
            return;
        }
        int id = Terminal.lerInteiro("Digite o ID do item: ");
        ItemCardapio item = disponiveis.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
        if (item == null) {
            System.out.println("Item não encontrado ou indisponível");
            return;
        }
        int qtd = Terminal.lerInteiro("Quantidade: ");
        if (qtd <= 0) {
            System.out.println("Quantidade inválida");
            return;
        }
        for (ItemPedido ip : carrinho) {
            if (ip.getItem().getId() == item.getId()) {
                ip.adicionarQuantidade(qtd);
                System.out.println("Quantidade atualizada no carrinho");
                return;
            }
        }
        carrinho.add(new ItemPedido(item, qtd));
        System.out.println("Item adicionado ao carrinho");
    }
    private void removerDoCarrinho(List<ItemPedido> carrinho) {
        if (carrinho.isEmpty()) {
            System.out.println("O carrinho está vazio");
            return;
        }
        int id = Terminal.lerInteiro("Digite o ID do item a remover do carrinho: ");
        boolean removido = carrinho.removeIf(ip -> ip.getItem().getId() == id);
        System.out.println(removido ? "Item removido do carrinho" : "Item não encontrado no carrinho");
    }
    private boolean confirmarPedido(Cliente cliente, List<ItemPedido> carrinho) {
        try {
            Pedido pedido = pedidoService.criarPedido(cliente, carrinho);
            System.out.println("\nPedido confirmado com sucesso");
            System.out.println(pedido);
            carrinho.clear();
            return true;
        } catch (CarrinhoVazioException e) {
            System.out.println("Erro ao confirmar pedido: " + e.getMessage());
            return false;
        }
    }
}
