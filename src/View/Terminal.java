package View;

import Model.CategoriaItem;
import Model.ItemCardapio;
import Model.ItemPedido;
import Model.Pedido;
import Model.StatusPedido;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public final class Terminal {
    private static final Scanner sc = new Scanner(System.in);
    private Terminal() {
    }
    public static void fechar() {
        sc.close();
    }
    public static String lerTexto(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }
    public static int lerOpcao(int min, int max) {
        while (true) {
            System.out.print("Escolha uma opção: ");
            String linha = sc.nextLine().trim();
            try {
                int valor = Integer.parseInt(linha);
                if (valor >= min && valor <= max) return valor;
            } catch (NumberFormatException ignored) { }
            System.out.println("Opção inválida, digite um número entre " + min + " e " + max);
        }
    }
    public static int lerInteiro(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = sc.nextLine().trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido, digite um número inteiro");
            }
        }
    }
    public static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = sc.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(linha);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido");
            }
        }
    }
    public static boolean lerSimNao(String prompt) {
        while (true) {
            System.out.print(prompt);
            String linha = sc.nextLine().trim().toLowerCase(Locale.ROOT);
            if (linha.equals("s") || linha.equals("sim")) return true;
            if (linha.equals("n") || linha.equals("nao") || linha.equals("não")) return false;
            System.out.println("Responda com 's' ou 'n'");
        }
    }
    public static CategoriaItem lerCategoriaItem() {
        while (true) {
            System.out.println("Categoria do item:");
            System.out.println("1. Entrada");
            System.out.println("2. Prato principal");
            System.out.println("3. Sobremesa");
            System.out.println("4. Bebidas");
            int opcao = lerOpcao(1, 4);
            switch (opcao) {
                case 1: return CategoriaItem.ENTRADA;
                case 2: return CategoriaItem.PRATO_PRINCIPAL;
                case 3: return CategoriaItem.SOBREMESA;
                case 4: return CategoriaItem.BEBIDAS;
            }
        }
    }
    public static CategoriaItem lerCategoriaItemOpcional(CategoriaItem atual) {
        System.out.println("1. Entrada  2. Prato principal  3. Sobremesa  4. Bebidas  (Enter para manter atual)");
        System.out.print("Escolha uma opção: ");
        String linha = sc.nextLine().trim();
        if (linha.isBlank()) return atual;
        switch (linha) {
            case "1": return CategoriaItem.ENTRADA;
            case "2": return CategoriaItem.PRATO_PRINCIPAL;
            case "3": return CategoriaItem.SOBREMESA;
            case "4": return CategoriaItem.BEBIDAS;
            default:
                System.out.println("Opção inválida, a categoria atual será mantida");
                return atual;
        }
    }
    public static StatusPedido lerStatusPedido() {
        StatusPedido[] valores = StatusPedido.values();
        System.out.println("Escolha o status:");
        for (int i = 0; i < valores.length; i++) {
            System.out.println((i + 1) + ". " + valores[i]);
        }
        int opcao = lerOpcao(1, valores.length);
        return valores[opcao - 1];
    }
    public static void listarCardapio(List<ItemCardapio> itens) {
        if (itens.isEmpty()) {
            System.out.println("(nenhum item cadastrado)");
            return;
        }
        for (ItemCardapio item : itens) {
            System.out.println(item);
        }
    }
    public static void listarPedidos(List<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido encontrado");
            return;
        }
        for (Pedido p : pedidos) {
            System.out.println("============");
            System.out.println(p);
        }
    }

    public static void mostrarCarrinho(List<ItemPedido> carrinho) {
        if (carrinho.isEmpty()) {
            System.out.println("(vazio)");
            return;
        }
        double total = 0;
        for (ItemPedido ip : carrinho) {
            System.out.println("- " + ip);
            total += ip.getSubtotal();
        }
        System.out.printf("Total do carrinho: R$ %.2f%n", total);
    }
}
