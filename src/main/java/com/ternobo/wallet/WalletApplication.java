package com.ternobo.wallet;

import com.ternobo.wallet.client.records.ApplicationClientWithSecret;
import com.ternobo.wallet.client.services.ApplicationClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WalletApplication {
    public static void main(String[] args) {
        SpringApplication.run(WalletApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandRunner(ApplicationClientService clientService) {
        return args -> {
			if(!clientService.isAnyClientExists()) {
				ApplicationClientWithSecret initialClient = clientService.createApplicationClient("Init");
				System.out.println("Initial Client: ");
				System.out.println("Client token: " + initialClient.client().getClientToken());
				System.out.println("Client secret: " + initialClient.secret());
			}
			};
    }


}
