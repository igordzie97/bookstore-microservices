package com.agh.maingateway;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.servlet.http.HttpServletResponse;


//import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class SecutiryTokenConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/token/*","/users/signup").permitAll()


                .antMatchers("/accounts-service/logged/test_tylko_admin").hasAuthority("admin")
                .antMatchers("/accounts-service/logged/**").hasAuthority("user")
                .antMatchers("/accounts-service/**").permitAll()

                .antMatchers("/orders-service/order").hasAuthority("admin")
                .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()  //POST, /accounts-service/auth
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new JwtFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }

}
