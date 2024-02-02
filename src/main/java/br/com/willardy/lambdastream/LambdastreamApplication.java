package br.com.willardy.lambdastream;

import br.com.willardy.lambdastream.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LambdastreamApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(LambdastreamApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.exibeMenu();
    }
}
