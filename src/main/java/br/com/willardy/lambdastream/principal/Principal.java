package br.com.willardy.lambdastream.principal;

import br.com.willardy.lambdastream.dto.DadosEpisodioDto;
import br.com.willardy.lambdastream.dto.DadosSerieDto;
import br.com.willardy.lambdastream.dto.DadosTemporadaDto;
import br.com.willardy.lambdastream.local.service.ConsumoAPIService;
import br.com.willardy.lambdastream.local.service.ConverteDadosService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private final String URL = "https://www.omdbapi.com/?&t=";
    private final String API_KEY = "&apikey=8d1c9917";
    private final String EPISODIO = "&episode=";
    private final String TEMPORADA = "&season=";
    private ConsumoAPIService consumoAPIService = new ConsumoAPIService();
    private ConverteDadosService converteDadosService = new ConverteDadosService();
    private Scanner leitura = new Scanner(System.in);

    public void exibeMenu() {
        System.out.println("BEM VINDO A API DE CONSULTA DE SERIES");
        System.out.print("Por favor digite o nome da serie que deseja buscar: ");
        String nomeSerie = leituraTeclado();
        nomeSerie = nomeSerie.replace(" ", "+");
        DadosSerieDto dadosSerieDto = buscarSeriePorTitulo(nomeSerie);

        System.out.println("Listando todas as temporadas encontradas abaixo: ");
        buscarTodasTemporadasPorSerie(nomeSerie, dadosSerieDto.totalTemporadas());

        System.out.print("Por favor digite o numero da Temporada que deseja buscar: ");
        var numeroTemporada = leituraTeclado();
        System.out.print("Por favor digite o numero do Episodio que deseja buscar: ");
        var numeroEpisodio = leituraTeclado();
        buscarSeriePorEpisodio(nomeSerie, numeroTemporada, numeroEpisodio);
    }

    private DadosSerieDto buscarSeriePorTitulo(String nomeSerie) {
        var jsonRetorno = consumoAPIService.obterDados(URL + nomeSerie + API_KEY);

        DadosSerieDto dadosSerieDto = converteDadosService.converteJsonParaClasse(jsonRetorno, DadosSerieDto.class);

        System.out.println(dadosSerieDto);
        return dadosSerieDto;
    }

    private List<DadosTemporadaDto> buscarTodasTemporadasPorSerie(String nomeSerie, Integer totalTemporadas) {
        List<DadosTemporadaDto> temporadas = new ArrayList<>();

        for (Integer numeroTemporada = 1; numeroTemporada <= totalTemporadas; numeroTemporada++) {
            var jsonRetorno = consumoAPIService.obterDados(URL + nomeSerie + TEMPORADA + numeroTemporada + API_KEY);
            DadosTemporadaDto dadosTemporadaDto = converteDadosService.converteJsonParaClasse(jsonRetorno, DadosTemporadaDto.class);
            temporadas.add(dadosTemporadaDto);
        }

//        temporadas.forEach(System.out::println);

        temporadas.forEach(temporada -> temporada.episodios().forEach(episodio -> System.out.println(episodio.titulo())));

        return temporadas;
    }

    private DadosEpisodioDto buscarSeriePorEpisodio(String nomeSerie, String temporada, String episodio) {
        var jsonRetorno = consumoAPIService.obterDados(URL + nomeSerie + TEMPORADA + temporada + EPISODIO + episodio + API_KEY);

        DadosEpisodioDto dadosEpisodioDto = converteDadosService.converteJsonParaClasse(jsonRetorno, DadosEpisodioDto.class);

        System.out.println(dadosEpisodioDto);
        return dadosEpisodioDto;
    }

    private String leituraTeclado() {
        return leitura.nextLine();
    }
}
