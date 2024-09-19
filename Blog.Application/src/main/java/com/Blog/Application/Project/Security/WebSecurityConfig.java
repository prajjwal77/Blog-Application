package com.Blog.Application.Project.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig{
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        HttpStatusReturningLogoutSuccessHandler logoutSuccessHandler = new HttpStatusReturningLogoutSuccessHandler();

		
		http
		.authorizeHttpRequests(authorize -> authorize
		.requestMatchers("/","/login","/register","/about","/logout","/logout-success")
		.permitAll()
		.anyRequest().authenticated()
		)
		.formLogin(formLogin -> formLogin
		.loginPage("/login")
		.permitAll()
        .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error")) // Set custom failure handler
		)
		.logout(logout ->logout
		.logoutUrl("/logout")
		.logoutSuccessUrl("/logout?logout")
		.permitAll()
		);
		
		return http.build();
	}
	
	@Bean
	  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
      }
	
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
	
}
