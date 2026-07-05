package Util;
import Exception.SenhaInvalidaException;

public final class ValidadorSenha {
    private ValidadorSenha() {
    }
    public static void validar(String senha) throws SenhaInvalidaException {
        if (senha == null || senha.length() < 8) {
            throw new SenhaInvalidaException("A senha deve ter no mínimo 8 caracteres");
        }
        boolean temDigito = senha.chars().anyMatch(Character::isDigit);
        if (!temDigito) {
            throw new SenhaInvalidaException("A senha deve conter ao menos um dígito numérico");
        }
    }
}
