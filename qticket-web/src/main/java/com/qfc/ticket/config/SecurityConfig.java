package com.qfc.ticket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	 private final JwtUtil jwtUtil;

	    public SecurityConfig(JwtUtil jwtUtil) {
	        this.jwtUtil = jwtUtil;
	    }

	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests()
	            .antMatchers("/tickets/**").hasAnyRole("USER", "HR", "IT", "FINANCE", "ANALYST") // Analyst can see all tickets
	            .antMatchers("/tickets/**/update").hasAnyRole("USER", "HR", "IT", "FINANCE")  // Restrict update access to others
	            .antMatchers("/tickets/**/delete").hasAnyRole("USER", "HR", "IT", "FINANCE")  // Restrict delete access to others
	            .anyRequest().authenticated()
	            .and()
	            .formLogin()
	            .permitAll()
	            .and()
	            .logout()
	            .permitAll();
	    }

		public JwtUtil getJwtUtil() {
			return jwtUtil;
		}
    
//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return authenticationManagerBean();
//    }
    
}