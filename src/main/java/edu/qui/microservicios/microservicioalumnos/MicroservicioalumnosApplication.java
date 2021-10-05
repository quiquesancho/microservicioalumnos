package edu.qui.microservicios.microservicioalumnos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("edu.qui.microservicios")
public class MicroservicioalumnosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioalumnosApplication.class, args);
	}

}
