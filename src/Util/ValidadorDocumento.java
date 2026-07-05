package Util;

import Exception.DocumentoInvalidoException;

public final class ValidadorDocumento {
    private ValidadorDocumento() {
    }
    private static String apenasDigitos(String documento) {
        return documento == null ? "" : documento.replaceAll("[^0-9]", "");
    }
    private static boolean todosDigitosIguais(String documento) {
        char primeiro = documento.charAt(0);
        for (int i = 1; i < documento.length(); i++) {
            if (documento.charAt(i) != primeiro) return false;
        }
        return true;
    }
    private static int calcularDigito(String base, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += Character.getNumericValue(base.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        return (resto < 2) ? 0 : 11 - resto;
    }
    public static void validarCPF(String cpf) throws DocumentoInvalidoException {
        String numeros = apenasDigitos(cpf);
        if (numeros.length() != 11 || todosDigitosIguais(numeros)) {
            throw new DocumentoInvalidoException("CPF inválido: " + cpf);
        }
        int[] pesosPrimeiroDigito = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int digito1 = calcularDigito(numeros.substring(0, 9), pesosPrimeiroDigito);
        if (digito1 != Character.getNumericValue(numeros.charAt(9))) {
            throw new DocumentoInvalidoException("CPF inválido (dígito verificador): " + cpf);
        }
        int[] pesosSegundoDigito = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        int digito2 = calcularDigito(numeros.substring(0, 10), pesosSegundoDigito);
        if (digito2 != Character.getNumericValue(numeros.charAt(10))) {
            throw new DocumentoInvalidoException("CPF inválido (dígito verificador): " + cpf);
        }
    }
    public static void validarCNPJ(String cnpj) throws DocumentoInvalidoException {
        String numeros = apenasDigitos(cnpj);
        if (numeros.length() != 14 || todosDigitosIguais(numeros)) {
            throw new DocumentoInvalidoException("CNPJ inválido: " + cnpj);
        }
        int[] pesosPrimeiroDigito = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int digito1 = calcularDigito(numeros.substring(0, 12), pesosPrimeiroDigito);
        if (digito1 != Character.getNumericValue(numeros.charAt(12))) {
            throw new DocumentoInvalidoException("CNPJ inválido: " + cnpj);
        }
        int[] pesosSegundoDigito = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int digito2 = calcularDigito(numeros.substring(0, 13), pesosSegundoDigito);
        if (digito2 != Character.getNumericValue(numeros.charAt(13))) {
            throw new DocumentoInvalidoException("CNPJ inválido: " + cnpj);
        }
    }
}
