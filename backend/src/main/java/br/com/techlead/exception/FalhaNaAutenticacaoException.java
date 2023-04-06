package br.com.techlead.exception;

public class FalhaNaAutenticacaoException extends RuntimeException{
    public FalhaNaAutenticacaoException() {
        super("Usuário ou senha inválidos");
    }
}
