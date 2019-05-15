package com.hks.exporter;

import io.prometheus.client.hotspot.DefaultExports;
import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnablePrometheusEndpoint
public class ExporterApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ExporterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		DefaultExports.initialize();
	}
}
