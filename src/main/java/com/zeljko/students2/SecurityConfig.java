package com.zeljko.students2;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.inMemoryAuthentication()
                .withUser("marko").password("{noop}12345").roles("USER").and()
                .withUser("jelena").password("{noop}12345").roles("USER").and()
                .withUser("zeljko").password("{noop}12345").roles("ADMIN");
    }

   /* @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().and().authorizeRequests()
                .antMatchers("/addItem","/delete").hasRole("ADMIN")
                .antMatchers("/getAllItems").hasRole("USER")
                .and().csrf().disable().headers().frameOptions().disable();
    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/").hasAnyRole("USER", "ADMIN")
                .antMatchers("/console/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/students").hasAnyRole("USER", "ADMIN")
                .antMatchers("/add").hasRole("ADMIN")
                .antMatchers("/save").hasRole("ADMIN")
                .antMatchers("/update").hasRole("ADMIN")
                .antMatchers("/update/{id}").hasRole("ADMIN")
                .antMatchers("/delete/{id}").hasRole("ADMIN")
                .antMatchers("/addStudentCourse/{id}").hasRole("ADMIN")
                .antMatchers("/student/{id}/courses").hasRole("ADMIN")
                .antMatchers("/resources/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/showMyLoginPage")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/access_denied");

        http.csrf().disable();
        http.headers().frameOptions().disable();

    }
}