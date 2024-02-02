package br.com.willardy.lambdastream.local.service;

public interface IConverteDados {
    <T> T converteJsonParaClasse(String json, Class<T> classe);
}
