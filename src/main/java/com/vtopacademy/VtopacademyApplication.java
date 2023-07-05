package com.vtopacademy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.vtopacademy"})
@EntityScan(basePackages = {"com.vtopacademy"})
@EnableJpaRepositories(basePackages = {"com.vtopacademy"}) 
@EnableMongoRepositories(basePackages = {"com.vtopacademy"}) 
public class VtopacademyApplication {

  public static void main(String[] args) {
    SpringApplication.run(VtopacademyApplication.class, args);
  }
  
}
 