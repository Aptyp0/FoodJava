package Service;

import Exception.DocumentoInvalidoException;
import Exception.SenhaInvalidaException;
import Model.Gerente;
import Model.Restaurante;

public class RestauranteService {
    private Restaurante restaurante;
    private Gerente gerente;
    private boolean configurado = false;
    public boolean isConfigurado() {
        return configurado;
    }
    public Restaurante getRestaurante() {
        return restaurante;
    }
    public Gerente getGerente() {
        return gerente;
    }
    public void configurar(String nomeFantasia, String cnpj, String endereco, String telefone,
                            String categoriaCulinaria, String nomeGerente, String email, String senha)
            throws DocumentoInvalidoException, SenhaInvalidaException {
        Restaurante novoRestaurante = new Restaurante(nomeFantasia, cnpj, endereco, telefone, categoriaCulinaria);
        Gerente novoGerente = new Gerente(nomeGerente, email, senha);
        this.restaurante = novoRestaurante;
        this.gerente = novoGerente;
        this.configurado = true;
    }
    public boolean autenticarGerente(String email, String senha) {
        return gerente != null && gerente.autenticar(email, senha);
    }
}
