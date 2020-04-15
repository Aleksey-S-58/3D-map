package application.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@ComponentScan("config.security")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;
	
	@Bean("noOpPasswordEncoder")
	public PasswordEncoder getPasswordEnc() {
		return NoOpPasswordEncoder.getInstance();		
	}
	
	@Override
	protected void configure(HttpSecurity https) throws Exception {
		https.portMapper().http(8780).mapsTo(8443)
		.http(11080).mapsTo(8443)
		.http(8080).mapsTo(8443);
		https.requiresChannel().anyRequest()
		.requiresSecure().antMatchers(HttpMethod.GET, "/locator.html")
		.requiresInsecure().antMatchers(HttpMethod.OPTIONS, "/map/**")
		.requiresInsecure().antMatchers(HttpMethod.GET, "/map")
		.requiresInsecure().antMatchers(HttpMethod.HEAD, "/map")
		.requiresSecure()
		.antMatchers(HttpMethod.GET, "/map/**")
		.requiresSecure()
		.antMatchers("/login/**", "/loginError");
		https.authorizeRequests()
		.antMatchers("/login/**").anonymous()
		.antMatchers("/map/**").anonymous()
		.antMatchers("/locator.html").anonymous()
		.and().formLogin().and().csrf().disable();
	}
}
