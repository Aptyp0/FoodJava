package Model;

import Exception.DocumentoInvalidoException;
import Exception.SenhaInvalidaException;
import Util.ValidadorDocumento;

public class Cliente extends Usuario {
    private static int contadorId = 1;
    private final int id;
    private String cpf;
    private String telefone;
    private String endereco;
    public Cliente(String nome, String email, String senha, String cpf,
                    String telefone, String endereco)
            throws SenhaInvalidaException, DocumentoInvalidoException {
        super(nome, email, senha);
        ValidadorDocumento.validarCPF(cpf);
        this.id = contadorId++;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
    }
    @Override
    public String getPerfil() {
        return "Cliente";
    }
    public int getId() {
        return id;
    }
    public String getCpf() {
        return cpf;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
