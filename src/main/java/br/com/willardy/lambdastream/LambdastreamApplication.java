package br.com.willardy.lambdastream;

import br.com.willardy.lambdastream.local.service.ConsumoAPIService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class LambdastreamApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LambdastreamApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        var consumoApi = new ConsumoAPIService();
        var json = consumoApi.obterDados("https://www.omdbapi.com/?t=Transform&apikey=8d1c9917");

        Logger logger = Logger.getLogger(getClass().getName());
        logger.info(json);
    }
}
