package Service;

import Exception.DocumentoInvalidoException;
import Exception.SenhaInvalidaException;
import Exception.UsuarioDuplicadoException;
import Model.Cliente;
import Util.ValidadorDocumento;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioService {
    private final List<Cliente> clientes = new ArrayList<>();
    private static String apenasDigitos(String texto) {
        return texto == null ? "" : texto.replaceAll("[^0-9]", "");
    }
    private boolean emailJaCadastrado(String email) {
        return clientes.stream().anyMatch(c -> c.getEmail().equalsIgnoreCase(email));
    }
    private boolean cpfJaCadastrado(String cpf) {
        String cpfDigitos = apenasDigitos(cpf);
        return clientes.stream().anyMatch(c -> apenasDigitos(c.getCpf()).equals(cpfDigitos));
    }
    public Cliente cadastrarCliente(String nome, String email, String senha, String cpf,
                                     String telefone, String endereco)
            throws UsuarioDuplicadoException, SenhaInvalidaException, DocumentoInvalidoException {
        if (emailJaCadastrado(email)) {
            throw new UsuarioDuplicadoException("Já existe um cliente cadastrado com o e-mail: " + email);
        }
        ValidadorDocumento.validarCPF(cpf);
        if (cpfJaCadastrado(cpf)) {
            throw new UsuarioDuplicadoException("Já existe um cliente cadastrado com o CPF: " + cpf);
        }
        Cliente cliente = new Cliente(nome, email, senha, cpf, telefone, endereco);
        clientes.add(cliente);
        return cliente;
    }
    public Optional<Cliente> autenticar(String email, String senha) {
        return clientes.stream()
                .filter(c -> c.autenticar(email, senha))
                .findFirst();
    }
}
