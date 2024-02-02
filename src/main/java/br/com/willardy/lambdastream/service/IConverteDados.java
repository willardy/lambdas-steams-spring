package br.com.willardy.lambdastream.service;

public interface IConverteDados {
    <T> T converteJsonParaClasse(String json, Class<T> classe);
}
