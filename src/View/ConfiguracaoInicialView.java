package View;

import Service.RestauranteService;
import Exception.DocumentoInvalidoException;
import Exception.SenhaInvalidaException;

public class ConfiguracaoInicialView {

    private final RestauranteService restauranteService;

    public ConfiguracaoInicialView(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    public void executar() {
        System.out.println("\n=== Configuração inicial do restaurante ===");
        boolean sucesso = false;
        while (!sucesso) {
            String nomeFantasia = Terminal.lerTexto("Digite o nome do restaurante: ");
            String cnpj = Terminal.lerTexto("Digite seu CNPJ: ");
            String endereco = Terminal.lerTexto("Digite o endereço do restaurante: ");
            String telefone = Terminal.lerTexto("Digite o telefone do restaurante: ");
            String categoriaCulinaria = Terminal.lerTexto("Categoria culinária do restaurante: ): ");

            System.out.println("\n=== Cadastro do gerente ===");
            String nomeGerente = Terminal.lerTexto("Digite o nome do gerente: ");
            String email = Terminal.lerTexto("Digite o e-mail do gerente: ");
            String senha = Terminal.lerTexto("Digite uma senha (mínimo de 8 caracteres, com ao menos 1 dígito): ");

            try {
                restauranteService.configurar(nomeFantasia, cnpj, endereco, telefone,
                        categoriaCulinaria, nomeGerente, email, senha);
                System.out.println("\nConfiguração inicial concluída com sucesso");
                sucesso = true;
            } catch (DocumentoInvalidoException | SenhaInvalidaException e) {
                System.out.println("\nErro: " + e.getMessage());
                System.out.println("Tente novamente\n");
            }
        }
    }
}
