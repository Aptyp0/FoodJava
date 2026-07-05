package Model;

import Exception.DocumentoInvalidoException;
import Util.ValidadorDocumento;

public class Restaurante {
    private String nomeFantasia;
    private String cnpj;
    private String endereco;
    private String telefone;
    private String categoriaCulinaria;
    public Restaurante(String nomeFantasia, String cnpj, String endereco,
                        String telefone, String categoriaCulinaria)
            throws DocumentoInvalidoException {
        ValidadorDocumento.validarCNPJ(cnpj);
        this.nomeFantasia = nomeFantasia;
        this.cnpj = cnpj;
        this.endereco = endereco;
        this.telefone = telefone;
        this.categoriaCulinaria = categoriaCulinaria;
    }
    public String getNomeFantasia() {
        return nomeFantasia;
    }
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) throws DocumentoInvalidoException {
        ValidadorDocumento.validarCNPJ(cnpj);
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCategoriaCulinaria() {
        return categoriaCulinaria;
    }
    public void setCategoriaCulinaria(String categoriaCulinaria) {
        this.categoriaCulinaria = categoriaCulinaria;
    }

    @Override
    public String toString() {
        return String.format("%s | CNPJ: %s | Categoria: %s | Telefone: %s | Endereço: %s",
                nomeFantasia, cnpj, categoriaCulinaria, telefone, endereco);
    }
}
