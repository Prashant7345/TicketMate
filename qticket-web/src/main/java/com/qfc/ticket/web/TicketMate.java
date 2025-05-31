package com.qfc.ticket.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.qfc.ticket.config.SecurityConfig;

@SpringBootApplication(scanBasePackages = {"com.qfc.ticket"}, exclude = {HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class})
//@Import({SecurityConfig.class})
@EnableScheduling // Enable scheduling if needed for periodic tasks like data cleanup
@EnableJpaRepositories(basePackages = {"com.qfc.ticket.service"}, entityManagerFactoryRef = "sessionFactory") // Enable configuration properties for externalized settings
public class TicketMate extends SpringBootServletInitializer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
	
	private static ConfigurableApplicationContext context;

    public static void main(String[] args) {
    	
        context =  SpringApplication.run(TicketMate.class, args);
        System.out.println("Ticketing Service Application started successfully!");
    }

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		// TODO Auto-generated method stub
		
	}
}
