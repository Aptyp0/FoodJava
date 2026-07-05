package Model;

import Exception.SenhaInvalidaException;

public class Gerente extends Usuario {
    public Gerente(String nome, String email, String senha) throws SenhaInvalidaException {
        super(nome, email, senha);
    }
    @Override
    public String getPerfil() {
        return "Gerente";
    }
}
