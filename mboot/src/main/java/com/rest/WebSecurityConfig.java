package com.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;
/**
 * 
 * @author ABC
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	/*@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/mboot").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .csrf().disable()
            	//.and()
            .logout()
                .permitAll();
            
    }
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }*/
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	                .antMatcher("*/*").authorizeRequests()
	                .antMatchers("/", "/user**").permitAll()
	                .anyRequest().authenticated()
	                .and().csrf().csrfTokenRepository(csrfTokenRepository())
	                .and().addFilterAfter(csrfHeaderFilter(), SessionManagementFilter.class);
	    }

	    private Filter csrfHeaderFilter() {
	        return new OncePerRequestFilter() {

	            @Override
	            protected void doFilterInternal(HttpServletRequest request,
	                                            HttpServletResponse response,
	                                            FilterChain filterChain) throws ServletException, IOException {

	                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
	                if (csrf != null) {
	                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
	                    String token = csrf.getToken();
	                    if (cookie == null || token != null
	                            && !token.equals(cookie.getValue())) {

	                        // Token is being added to the XSRF-TOKEN cookie.
	                        cookie = new Cookie("XSRF-TOKEN", token);
	                        cookie.setPath("/");
	                        response.addCookie(cookie);
	                    }
	                }
	                filterChain.doFilter(request, response);
	            }
	        };
	    }

	    private CsrfTokenRepository csrfTokenRepository() {
	        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
	        repository.setHeaderName("X-XSRF-TOKEN");
	        //repository.setSessionAttributeName(("X-XSRF-TOKEN"));
	        return repository;
	    }
	}

