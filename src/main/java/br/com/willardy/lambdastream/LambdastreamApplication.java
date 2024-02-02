package br.com.willardy.lambdastream;

import br.com.willardy.lambdastream.dto.DadosEpisodioDto;
import br.com.willardy.lambdastream.dto.DadosSerieDto;
import br.com.willardy.lambdastream.dto.DadosTemporadaDto;
import br.com.willardy.lambdastream.local.service.ConsumoAPIService;
import br.com.willardy.lambdastream.local.service.ConverteDadosService;
import br.com.willardy.lambdastream.local.service.IConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SpringBootApplication
public class LambdastreamApplication implements CommandLineRunner {

    private final String URL = "https://www.omdbapi.com/?apikey=8d1c9917";
    private final String BUSCA_POR_EPISODIO = "&episode=";
    private final String BUSCA_POR_TITULO = "&t=";
    private final String BUSCA_POR_TEMPORADA = "&season=";

    public static void main(String[] args) {
        SpringApplication.run(LambdastreamApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Criado as variáveis para titulo, temporada e episodio da consulta da API de forma statica
        final var titulo = "Supernatural";
        final var temporada = "1";
        final var episodio = "1";

        // classe responsável por imprimir no console um log
        Logger logger = Logger.getLogger(getClass().getName());

        // Variavel para guardar a busca inicial por titulo
        final var buscaPorTitulo = URL + BUSCA_POR_TITULO + titulo;

        // Instanciando o servico de consumo de API
        final var consumoAPIService = new ConsumoAPIService();
        // Obtendo a string do json da url de buscar Titulo
        var json = consumoAPIService.obterDados(buscaPorTitulo);

        logger.info(json);

        // Instanciando o servico de consumo de Dados
        var consumoDadosService = new ConverteDadosService();
        // Convertendo o Json para a classe Dados Serie
        DadosSerieDto dadosSerieDto = consumoDadosService.converteJsonParaClasse(json, DadosSerieDto.class);
        logger.info(dadosSerieDto.toString());

        // Url de consulta para episodios
        final var buscaPorEpisodio =buscaPorTitulo + BUSCA_POR_TEMPORADA + temporada + BUSCA_POR_EPISODIO + episodio;
        // Realizando uma busca por Episodio de uma determinada temporada de uma determinada serie
        json = consumoAPIService.obterDados(buscaPorEpisodio);

        // Convertendo o Json para a classe Dados Episodio
        DadosEpisodioDto dadosEpisodioDto = consumoDadosService.converteJsonParaClasse(json, DadosEpisodioDto.class);

        // Inicializacao vazia da lista de temporadas
        List<DadosTemporadaDto> temporadas = new ArrayList<>();

        // Percorrer todas as temporadas existentes na serie escolhida
        for (Integer numeroTemporada = 1; numeroTemporada <= dadosSerieDto.totalTemporadas(); numeroTemporada++) {
            json = consumoAPIService.obterDados(buscaPorTitulo + BUSCA_POR_TEMPORADA + numeroTemporada);
            DadosTemporadaDto dadosTemporadaDto = consumoDadosService.converteJsonParaClasse(json, DadosTemporadaDto.class);
            temporadas.add(dadosTemporadaDto);
        }

        // Imprimindo todas as Temporadas
        temporadas.forEach(System.out::println);
    }
}
