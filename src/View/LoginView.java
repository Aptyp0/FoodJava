package View;

import Service.RestauranteService;
import Service.UsuarioService;
import Exception.DocumentoInvalidoException;
import Exception.SenhaInvalidaException;
import Exception.UsuarioDuplicadoException;

public class LoginView {

    private final RestauranteService restauranteService;
    private final UsuarioService usuarioService;
    private final GerenteView gerenteView;
    private final ClienteView clienteView;

    public LoginView(RestauranteService restauranteService,
                      UsuarioService usuarioService,
                      GerenteView gerenteView,
                      ClienteView clienteView) {
        this.restauranteService = restauranteService;
        this.usuarioService = usuarioService;
        this.gerenteView = gerenteView;
        this.clienteView = clienteView;
    }

    public void executar() {
        boolean sair = false;
        while (!sair) {
            System.out.println("\n======================");
            System.out.println("Restaurante: " + restauranteService.getRestaurante());
            System.out.println("======================");
            System.out.println("1. Login como gerente");
            System.out.println("2. Login como cliente");
            System.out.println("3. Cadastro como cliente");
            System.out.println("4. Sair");
            int opcao = Terminal.lerOpcao(1, 4);

            switch (opcao) {
                case 1: fazerLoginGerente(); break;
                case 2: fazerLoginCliente(); break;
                case 3: cadastrarCliente(); break;
                case 4: sair = true; break;
            }
        }
    }

    private void fazerLoginGerente() {
        System.out.println("\n=== Login do gerente ===");
        String email = Terminal.lerTexto("E-mail: ");
        String senha = Terminal.lerTexto("Senha: ");

        if (restauranteService.autenticarGerente(email, senha)) {
            System.out.println("Login efetuado com sucesso");
            gerenteView.executar();
        } else {
            System.out.println("E-mail ou senha inválidos");
        }
    }

    private void fazerLoginCliente() {
        System.out.println("\n=== Login do cliente ===");
        String email = Terminal.lerTexto("E-mail: ");
        String senha = Terminal.lerTexto("Senha: ");

        usuarioService.autenticar(email, senha).ifPresentOrElse(
                cliente -> {
                    System.out.println("Login efetuado com sucesso, bem-vindo(a), " + cliente.getNome());
                    clienteView.executar(cliente);
                },
                () -> System.out.println("E-mail ou senha inválidos")
        );
    }

    private void cadastrarCliente() {
        System.out.println("\n=== Cadastro de cliente ===");
        String nome = Terminal.lerTexto("Digite seu nome: ");
        String email = Terminal.lerTexto("Digite seu e-mail: ");
        String senha = Terminal.lerTexto("Crie uma senha (mínimo de 8 caracteres, com ao menos 1 dígito): ");
        String cpf = Terminal.lerTexto("Digite seu CPF: ");
        String telefone = Terminal.lerTexto("Digite seu telefone: ");
        String endereco = Terminal.lerTexto("Digite seu endereço: ");

        try {
            usuarioService.cadastrarCliente(nome, email, senha, cpf, telefone, endereco);
            System.out.println("Cliente cadastrado com sucesso, faça login para continuar");
        } catch (UsuarioDuplicadoException | SenhaInvalidaException | DocumentoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
