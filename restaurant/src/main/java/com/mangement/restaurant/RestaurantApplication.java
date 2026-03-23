package com.mangement.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class RestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantApplication.class, args);
	}
	
	
	@EventListener(ApplicationReadyEvent.class)
	public void onReady() {
	    String GREEN = "\033[32m";
	    String RESET = "\033[0m";

	    String banner = """
	            
          ███████╗ ███████╗ ██████╗  ██╗   ██╗ ██╗  ██████╗ ███████╗    
          ██╔════╝ ██╔════╝ ██╔══██╗ ██║   ██║ ██║ ██╔════╝ ██╔════╝     ███████
          ███████╗ █████╗   ██████╔╝ ██║   ██║ ██║ ██║      █████╗      
          ╚════██║ ██╔══╝   ██╔══██╗ ╚██╗ ██╔╝ ██║ ██║      ██╔══╝       ███████
          ███████║ ███████╗ ██║  ██║  ╚████╔╝  ██║ ╚██████╗ ███████╗    
          ╚══════╝ ╚══════╝ ╚═╝  ╚═╝   ╚═══╝   ╚═╝  ╚═════╝ ╚══════╝   
            
            ██╗   ██╗ ██████╗ 
            ██║   ██║ ██╔══██╗
            ██║   ██║ ██████╔╝
            ██║   ██║ ██╔═══╝ 
            ╚██████╔╝ ██║     
             ╚═════╝  ╚═╝     """;

	    System.out.println(GREEN + banner + RESET);
	}
	 
	

}
