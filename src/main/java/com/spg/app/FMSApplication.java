package com.spg.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FMSApplication
 * @author Ayush Verma
 * Main application responsible for starting rest service
 */
@SpringBootApplication
public class FMSApplication {

	/**
	 * Main method for application, this will start FMS application
	 */
    public static void main(String[] args) {
        SpringApplication.run(FMSApplication.class, args);
    }
}
