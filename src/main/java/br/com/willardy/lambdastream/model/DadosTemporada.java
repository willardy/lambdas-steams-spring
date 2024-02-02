package br.com.willardy.lambdastream.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(
        @JsonProperty("Season") Integer temporada,
        @JsonProperty("Episodes") List<DadosEpisodio> episodios) {
}
