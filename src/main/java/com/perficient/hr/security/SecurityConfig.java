package com.perficient.hr.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("mkyong").password("user").roles("USER");
		  auth.inMemoryAuthentication().withUser("7373430966").password("adm").roles("admin");
		  auth.inMemoryAuthentication().withUser("7373430966").password("dba").roles("DBA");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  /*http.authorizeRequests()
	  	.antMatchers("/v-leave/**").access("hasRole('1')")
		.antMatchers("/v-leave/**").access("hasRole('DBA') or hasRole('USER')");
	  
	  http.authorizeRequests().and().formLogin().usernameParameter(PerfHrConstants.USER_NAME).passwordParameter(PerfHrConstants.PASSWORD).
		defaultSuccessUrl(PerfHrConstants.SUCCESS_PAGE).failureUrl(PerfHrConstants.ERROR_PAGE).and().
		exceptionHandling().accessDeniedPage(PerfHrConstants.ACCESS_DENIED_PAGE);
	  http.authorizeRequests().antMatchers("").access("hasRole('ADMIN') hasRole('DBA') or hasRole('USER')").and().
		exceptionHandling().accessDeniedPage("accessDenied.jsp");
	  */
		 http.authorizeRequests()
			.antMatchers("/v-leave/applyLeave").access("hasRole('ADMIN') or hasRole('DBA') or hasRole('USER')")
			.antMatchers("/v-leave/updateLeave").access("hasRole('DBA') or hasRole('USER')")
			.and().formLogin();
	  
	}
}