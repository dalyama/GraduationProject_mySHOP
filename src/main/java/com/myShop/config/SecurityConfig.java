package com.myShop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.myShop.authentication.CustomAuthenticationFailureHandler;
import com.myShop.authentication.CustomAuthenticationSuccessHandler;
import com.myShop.repository.userRepo;
import com.myShop.services.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		  prePostEnabled = true, 
		  securedEnabled = true, 
		  jsr250Enabled = true)
@EnableJpaRepositories(basePackageClasses = userRepo.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	/*
	 * @Autowired private DataSource dataSource;
	 * 
	 * @Override public void configure(AuthenticationManagerBuilder builder) throws
	 * Exception { builder.authenticationProvider(new
	 * CustomAuthenticationProvider()); }
	 */

	@Autowired
	MyUserDetailsService myUserDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*
		 * auth.jdbcAuthentication().dataSource(dataSource)
		 * .usersByUsernameQuery("select email as principal, password as credentails,1 from users where email = ?"
		 * )
		 * .authoritiesByUsernameQuery("select email as principal, role as role from users where email = ?"
		 * ) .passwordEncoder(passwordEncoder()).rolePrefix("ROLE_");
		 * auth.authenticationProvider(new CustomAuthenticationProvider());
		 */
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());
		// auth.authenticationProvider(new CustomAuthenticationProvider());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public final String [] PUBLIC_END_POINTS = {
			"/css/**", "/images/**", "/js/**", "/login**", "/", "/home","/home**" ,"/home/**", "/offers",
			"/support", "/quickHelp", "/service", "/**/register", 
			"/account/activation**","/account/activation/checkEmail","/account/sendActivationCode**",
			"/**/address", "/**/saveAddress",
			"/test/search", "/h2", "/h2/**", "/api/product/getProduct/{id}","/api/product/search**"
			// ,"/cart","/cart/{id}/addCart","/cart/{id}/update/{quantity}","/cart/2/addCart"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requiresChannel().requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null).requiresSecure();
		http.authorizeRequests()
				.antMatchers(PUBLIC_END_POINTS).permitAll()
				.antMatchers("/cart").hasAnyRole("ADMIN","USER")
				
				.anyRequest().authenticated()
				.antMatchers("api/store/**").hasRole("ADMIN")
				.and().formLogin()
				// .permitAll()
				.loginPage("/login")
				// .loginProcessingUrl("/login/process")
				/* .successHandler(authenticationSuccessHandler) */
				// .loginProcessingUrl("login")
				.defaultSuccessUrl("/home")
				// .failureUrl("/login?error=true")
				.failureHandler(new CustomAuthenticationFailureHandler())
				.successHandler(new CustomAuthenticationSuccessHandler()) // occur problem : was not access into service
				.permitAll().and().logout().permitAll().logoutSuccessUrl("/home").invalidateHttpSession(true).and()
				.csrf().disable();
		// http.exceptionHandling().accessDeniedPage("/home");
		http.headers().frameOptions().sameOrigin();
	}
}