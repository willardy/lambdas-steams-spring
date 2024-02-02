package br.com.willardy.lambdastream.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosSerieDto(
        @JsonProperty("Title") String titulo,
        @JsonProperty("totalSeasons") Integer totalTemporadas,
        @JsonProperty("imdbRating") String avaliacao) {
}
