package br.com.willardy.lambdastream;

import br.com.willardy.lambdastream.dto.DadosEpisodioDto;
import br.com.willardy.lambdastream.dto.DadosSerieDto;
import br.com.willardy.lambdastream.local.service.ConsumoAPIService;
import br.com.willardy.lambdastream.local.service.ConverteDadosService;
import br.com.willardy.lambdastream.local.service.IConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        final var consumoAPIService = new ConsumoAPIService();
        final var buscaPorTitulo = URL + BUSCA_POR_TITULO + "Supernatural";
        var json = consumoAPIService.obterDados(buscaPorTitulo);

        Logger logger = Logger.getLogger(getClass().getName());
        logger.info(json);

        var consumoDadosService = new ConverteDadosService();
        DadosSerieDto dadosSerieDto = consumoDadosService.converteJsonParaClasse(json, DadosSerieDto.class);
        logger.info(dadosSerieDto.toString());

        final var temporada = "1";
        final var episodio = "1";
        final var buscaPorEpisodio = buscaPorTitulo + BUSCA_POR_TEMPORADA + temporada + BUSCA_POR_EPISODIO + episodio;
        json = consumoAPIService.obterDados(buscaPorEpisodio);

        DadosEpisodioDto dadosEpisodioDto = consumoDadosService.converteJsonParaClasse(json, DadosEpisodioDto.class);
        logger.info(dadosEpisodioDto.toString());

    }
}
