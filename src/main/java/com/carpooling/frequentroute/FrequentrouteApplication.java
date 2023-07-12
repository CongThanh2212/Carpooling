package com.carpooling.frequentroute;

import com.github.fabiomaffioletti.firebase.EnableFirebaseRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableFirebaseRepositories
@SpringBootApplication
@RequiredArgsConstructor
public class FrequentrouteApplication {

    public static void main(String[] args) {
        SpringApplication.run(FrequentrouteApplication.class, args);
    }

}
