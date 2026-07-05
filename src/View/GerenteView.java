package View;

import Service.CardapioService;
import Service.PedidoService;
import Service.RestauranteService;
import Exception.CancelamentoNaoPermitidoException;
import Exception.DocumentoInvalidoException;
import Exception.ItemVinculadoException;
import Exception.PrecoInvalidoException;
import Exception.StatusInvalidoException;
import Model.CategoriaItem;
import Model.ItemCardapio;
import Model.Restaurante;
import Model.StatusPedido;

public class GerenteView {

    private final RestauranteService restauranteService;
    private final CardapioService cardapioService;
    private final PedidoService pedidoService;

    public GerenteView(RestauranteService restauranteService,
                        CardapioService cardapioService,
                        PedidoService pedidoService) {
        this.restauranteService = restauranteService;
        this.cardapioService = cardapioService;
        this.pedidoService = pedidoService;
    }

    public void executar() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n=== Menu do gerente ===");
            System.out.println("1. Cadastrar item do cardápio");
            System.out.println("2. Ver, editar ou excluir cardápio");
            System.out.println("3. Painel de pedidos");
            System.out.println("4. Editar dados do restaurante");
            System.out.println("5. Sair e voltar para a tela de login)");
            int opcao = Terminal.lerOpcao(1, 5);

            switch (opcao) {
                case 1: cadastrarItemCardapio(); break;
                case 2: gerenciarCardapio(); break;
                case 3: painelDePedidos(); break;
                case 4: editarRestaurante(); break;
                case 5: sair = true; break;
            }
        }
    }

    private void cadastrarItemCardapio() {
        System.out.println("\n=== Cadastrar item do cardápio ===");
        String nome = Terminal.lerTexto("Nome: ");
        String descricao = Terminal.lerTexto("Descrição: ");
        CategoriaItem categoria = Terminal.lerCategoriaItem();
        double preco = Terminal.lerDouble("Preço: ");
        boolean disponivel = Terminal.lerSimNao("Disponível para pedidos? (s/n): ");

        try {
            ItemCardapio item = cardapioService.cadastrarItem(nome, descricao, categoria, preco, disponivel);
            System.out.println("Item cadastrado: " + item);
        } catch (PrecoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void gerenciarCardapio() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n--- Cardápio ---");
            Terminal.listarCardapio(cardapioService.getTodosItens());
            System.out.println("\n1. Editar item");
            System.out.println("2. Excluir item");
            System.out.println("3. Voltar");
            int opcao = Terminal.lerOpcao(1, 3);

            switch (opcao) {
                case 1: editarItemCardapio(); break;
                case 2: excluirItemCardapio(); break;
                case 3: voltar = true; break;
            }
        }
    }

    private void editarItemCardapio() {
        int id = Terminal.lerInteiro("Digite o ID do item a editar: ");
        var itemOpt = cardapioService.buscarItem(id);
        if (itemOpt.isEmpty()) {
            System.out.println("Item não encontrado");
            return;
        }
        ItemCardapio atual = itemOpt.get();
        System.out.println("Editando: " + atual);

        String nome = Terminal.lerTexto("Novo nome (Enter para manter \"" + atual.getNome() + "\"): ");
        if (nome.isBlank()) nome = atual.getNome();

        String descricao = Terminal.lerTexto("Nova descrição (Enter para manter): ");
        if (descricao.isBlank()) descricao = atual.getDescricao();

        System.out.println("Nova categoria (Enter para manter " + atual.getCategoria() + "):");
        CategoriaItem categoria = Terminal.lerCategoriaItemOpcional(atual.getCategoria());

        String precoStr = Terminal.lerTexto("Novo preço (Enter para manter " + atual.getPreco() + "): ");
        double preco = precoStr.isBlank() ? atual.getPreco() : Double.parseDouble(precoStr.replace(",", "."));

        boolean disponivel = Terminal.lerSimNao("Disponível? (s/n): ");

        try {
            cardapioService.editarItem(id, nome, descricao, categoria, preco, disponivel);
            System.out.println("Item atualizado com sucesso");
        } catch (ItemVinculadoException | PrecoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    private void excluirItemCardapio() {
        int id = Terminal.lerInteiro("Digite o ID do item que quer excluir: ");
        try {
            cardapioService.excluirItem(id);
            System.out.println("Item excluído com sucesso");
        } catch (ItemVinculadoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    private void painelDePedidos() {
        boolean voltar = false;
        while (!voltar) {
            System.out.println("\n=== Painel de pedidos ===");
            System.out.println("1. Ver todos os pedidos");
            System.out.println("2. Filtrar pedidos por status");
            System.out.println("3. Avançar status de um pedido");
            System.out.println("4. Cancelar um pedido");
            System.out.println("5. Ver resumo do dia");
            System.out.println("6. Voltar");
            int opcao = Terminal.lerOpcao(1, 6);

            switch (opcao) {
                case 1: Terminal.listarPedidos(pedidoService.getTodosPedidos()); break;
                case 2: filtrarPedidosPorStatus(); break;
                case 3: avancarStatusPedido(); break;
                case 4: cancelarPedidoGerente(); break;
                case 5: verResumoDoDia(); break;
                case 6: voltar = true; break;
            }
        }
    }

    private void filtrarPedidosPorStatus() {
        StatusPedido status = Terminal.lerStatusPedido();
        Terminal.listarPedidos(pedidoService.filtrarPorStatus(status));
    }

    private void avancarStatusPedido() {
        int id = Terminal.lerInteiro("Digite o número do pedido: ");
        try {
            pedidoService.avancarStatus(id);
            System.out.println("Status avançado com sucesso");
        } catch (StatusInvalidoException | IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void cancelarPedidoGerente() {
        int id = Terminal.lerInteiro("Digite o número do pedido a cancelar: ");
        try {
            pedidoService.cancelarPedidoComoGerente(id);
            System.out.println("Pedido cancelado com sucesso");
        } catch (CancelamentoNaoPermitidoException | IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void verResumoDoDia() {
        PedidoService.ResumoDia resumo = pedidoService.resumoDoDia();
        System.out.println("\n=== Resumo do dia ===");
        System.out.println("Total de pedidos hoje: " + resumo.totalPedidos);
        System.out.printf("Faturamento (pedidos entregues hoje): R$ %.2f%n", resumo.faturamento);
    }

    private void editarRestaurante() {
        Restaurante r = restauranteService.getRestaurante();
        System.out.println("\n=== Editar dados do restaurante ===");

        String nome = Terminal.lerTexto("Nome (Enter para manter \"" + r.getNomeFantasia() + "\"): ");
        if (!nome.isBlank()) r.setNomeFantasia(nome);

        String categoria = Terminal.lerTexto("Categoria culinária (Enter para manter \"" + r.getCategoriaCulinaria() + "\"): ");
        if (!categoria.isBlank()) r.setCategoriaCulinaria(categoria);

        String telefone = Terminal.lerTexto("Telefone (Enter para manter \"" + r.getTelefone() + "\"): ");
        if (!telefone.isBlank()) r.setTelefone(telefone);

        String endereco = Terminal.lerTexto("Endereço (Enter para manter \"" + r.getEndereco() + "\"): ");
        if (!endereco.isBlank()) r.setEndereco(endereco);

        String cnpj = Terminal.lerTexto("CNPJ (Enter para manter \"" + r.getCnpj() + "\"): ");
        if (!cnpj.isBlank()) {
            try {
                r.setCnpj(cnpj);
            } catch (DocumentoInvalidoException e) {
                System.out.println("Erro: " + e.getMessage() + " (CNPJ não foi alterado)");
            }
        }

        System.out.println("Dados do restaurante atualizados");
    }
}
