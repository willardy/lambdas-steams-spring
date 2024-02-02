package br.com.willardy.lambdastream.principal;

import br.com.willardy.lambdastream.model.DadosEpisodio;
import br.com.willardy.lambdastream.model.DadosSerie;
import br.com.willardy.lambdastream.model.DadosTemporada;
import br.com.willardy.lambdastream.model.Episodio;
import br.com.willardy.lambdastream.service.ConsumoAPIService;
import br.com.willardy.lambdastream.service.ConverteDadosService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        DadosSerie dadosSerie = buscarSeriePorTitulo(nomeSerie);

        System.out.println("Listando todas as temporadas encontradas abaixo: ");
        buscarTodasTemporadasPorSerie(nomeSerie, dadosSerie.totalTemporadas());

//        System.out.print("Por favor digite o numero da Temporada que deseja buscar: ");
//        var numeroTemporada = leituraTeclado();
//        System.out.print("Por favor digite o numero do Episodio que deseja buscar: ");
//        var numeroEpisodio = leituraTeclado();
//        buscarSeriePorEpisodio(nomeSerie, numeroTemporada, numeroEpisodio);
    }

    private DadosSerie buscarSeriePorTitulo(String nomeSerie) {
        var jsonRetorno = consumoAPIService.obterDados(URL + nomeSerie + API_KEY);

        DadosSerie dadosSerie = converteDadosService.converteJsonParaClasse(jsonRetorno, DadosSerie.class);

        System.out.println(dadosSerie);
        return dadosSerie;
    }

    private List<DadosTemporada> buscarTodasTemporadasPorSerie(String nomeSerie, Integer totalTemporadas) {
        List<DadosTemporada> temporadas = new ArrayList<>();

        for (Integer numeroTemporada = 1; numeroTemporada <= totalTemporadas; numeroTemporada++) {
            var jsonRetorno = consumoAPIService.obterDados(URL + nomeSerie + TEMPORADA + numeroTemporada + API_KEY);
            DadosTemporada dadosTemporada = converteDadosService.converteJsonParaClasse(jsonRetorno, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }

        mostrarTop5Episodios(temporadas);
        listarTodosEpisodios(temporadas);




        return temporadas;
    }

    private void mostrarTop5Episodios(List<DadosTemporada> temporadas){
        System.out.println("\n\nListando os TOP 5 episodios com maior avaliacao");
        List<DadosEpisodio> dadosEpisodioList = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .collect(Collectors.toList());

        dadosEpisodioList.forEach(System.out::println);
    }

    private void listarTodosEpisodios(List<DadosTemporada> temporadas){
        System.out.println("\n\nListando os Episodios");
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios()
                        .stream()
                        .map(e -> new Episodio(t.temporada(), e)))
                .collect(Collectors.toList());

        episodios.forEach(System.out::println);
    }

    private DadosEpisodio buscarSeriePorEpisodio(String nomeSerie, String temporada, String episodio) {
        var jsonRetorno = consumoAPIService.obterDados(URL + nomeSerie + TEMPORADA + temporada + EPISODIO + episodio + API_KEY);

        DadosEpisodio dadosEpisodio = converteDadosService.converteJsonParaClasse(jsonRetorno, DadosEpisodio.class);

        System.out.println(dadosEpisodio);
        return dadosEpisodio;
    }

    private String leituraTeclado() {
        return leitura.nextLine();
    }
}
