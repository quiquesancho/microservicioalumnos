package edu.qui.microservicios.microservicioalumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EntityScan("edu.qui.microservicios")
@EnableEurekaClient
public class MicroservicioalumnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioalumnosApplication.class, args);
	}

}
