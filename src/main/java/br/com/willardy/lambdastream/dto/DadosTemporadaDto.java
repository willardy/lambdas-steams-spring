package br.com.willardy.lambdastream.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporadaDto(
        @JsonProperty("Season") Integer temporada,
        @JsonProperty("Episodes") List<DadosEpisodioDto> episodios) {
}
