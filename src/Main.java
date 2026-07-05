import Service.CardapioService;
import Service.PedidoService;
import Service.RestauranteService;
import Service.UsuarioService;
import View.ClienteView;
import View.ConfiguracaoInicialView;
import View.Terminal;
import View.GerenteView;
import View.LoginView;

public class Main {

    public static void main(String[] args) {
        System.out.println("======================");
        System.out.println("Bem-vindo ao FoodJava");
        System.out.println("======================");

        RestauranteService restauranteService = new RestauranteService();
        UsuarioService usuarioService = new UsuarioService();
        PedidoService pedidoService = new PedidoService();
        CardapioService cardapioService = new CardapioService(pedidoService);

        ConfiguracaoInicialView configuracaoInicialView = new ConfiguracaoInicialView(restauranteService);
        GerenteView gerenteView = new GerenteView(restauranteService, cardapioService, pedidoService);
        ClienteView clienteView = new ClienteView(cardapioService, pedidoService);
        LoginView loginView = new LoginView(restauranteService, usuarioService, gerenteView, clienteView);

        if (!restauranteService.isConfigurado()) {
            configuracaoInicialView.executar();
        }

        loginView.executar();

        System.out.println("\nObrigado por utilizar o FoodJava");
        System.out.print("============================");
        Terminal.fechar();
    }
}
