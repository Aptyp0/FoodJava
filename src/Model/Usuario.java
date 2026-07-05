package Model;

import Exception.SenhaInvalidaException;
import Util.ValidadorSenha;

public abstract class Usuario implements Autenticavel {
    private String nome;
    private String email;
    private String senha;
    protected Usuario(String nome, String email, String senha) throws SenhaInvalidaException {
        ValidadorSenha.validar(senha);
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    @Override
    public boolean autenticar(String email, String senha) {
        return this.email.equalsIgnoreCase(email) && this.senha.equals(senha);
    }
    public abstract String getPerfil();
    @Override
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) throws SenhaInvalidaException {
        ValidadorSenha.validar(senha);
        this.senha = senha;
    }
}
