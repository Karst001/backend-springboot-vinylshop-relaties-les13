package nl.novi.vinylshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VinylShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(VinylShopApplication.class, args);
    }
}


//notes
// Controller -> talks to service
// Service -> talks to repository
// Repository -> returns entities
// Mapper -> converts entities to DTOs
// Controller -> returns DTOs