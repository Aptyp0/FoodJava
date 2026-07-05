package Model;

import Exception.PrecoInvalidoException;

public class ItemCardapio {
    private final int id;
    private String nome;
    private String descricao;
    private CategoriaItem categoria;
    private double preco;
    private boolean disponivel;
    public ItemCardapio(int id, String nome, String descricao, CategoriaItem categoria,
                         double preco, boolean disponivel) throws PrecoInvalidoException {
        validarPreco(preco);
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.disponivel = disponivel;
    }
    private static void validarPreco(double preco) throws PrecoInvalidoException {
        if (preco <= 0) {
            throw new PrecoInvalidoException("O preço do item deve ser maior que zero");
        }
    }
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public CategoriaItem getCategoria() {
        return categoria;
    }
    public void setCategoria(CategoriaItem categoria) {
        this.categoria = categoria;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) throws PrecoInvalidoException {
        validarPreco(preco);
        this.preco = preco;
    }
    public boolean isDisponivel() {
        return disponivel;
    }
    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
    @Override
    public String toString() {
        return String.format("[%d] %s (%s) - R$ %.2f - %s%s",
                id, nome, categoria, preco,
                disponivel ? "Disponível" : "Indisponível",
                descricao != null && !descricao.isEmpty() ? " - " + descricao : "");
    }
}
