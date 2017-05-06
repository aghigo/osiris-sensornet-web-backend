package br.uff.labtempo.osiris;

import br.uff.labtempo.omcp.common.exceptions.AbstractRequestException;
import br.uff.labtempo.osiris.schedule.VirtualSensorNetLoaderSchedule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URISyntaxException;

/**
 * Main class to start the application
 * @author andre.ghigo
 * @since 1.8
 * @version 1.0
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
