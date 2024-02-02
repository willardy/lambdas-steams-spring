package br.com.willardy.lambdastream.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverteDadosService implements IConverteDados {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T converteJsonParaClasse(String json, Class<T> classe) {
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
