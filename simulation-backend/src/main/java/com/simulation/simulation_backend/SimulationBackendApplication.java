package com.simulation.simulation_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SimulationBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimulationBackendApplication.class, args);
		System.out.println("hello the server is running");
	}

}
