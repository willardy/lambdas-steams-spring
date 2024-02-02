package br.com.willardy.lambdastream.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerie(
        @JsonProperty("Title") String titulo,
        @JsonProperty("totalSeasons") Integer totalTemporadas,
        @JsonProperty("imdbRating") String avaliacao) {
}
