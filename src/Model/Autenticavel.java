package Model;

public interface Autenticavel {
    boolean autenticar(String email, String senha);
    String getEmail();
}
