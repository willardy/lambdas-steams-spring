package br.com.willardy.lambdastream.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(
        @JsonProperty("Title") String titulo,
        @JsonProperty("Episode") Integer numeroEpisodio,
        @JsonProperty("imdbRating") String avaliacao,
        @JsonProperty("Released") String dataLancamento) {
}
