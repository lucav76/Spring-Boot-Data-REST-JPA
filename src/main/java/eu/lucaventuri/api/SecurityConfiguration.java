package eu.lucaventuri.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private enum Roles {
        CUSTOMER, ADMIN
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("user1"))
                .roles(Roles.CUSTOMER.name());
        auth.inMemoryAuthentication()
                .withUser("admin1").password(passwordEncoder().encode("admin1"))
                .roles(Roles.ADMIN.name());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, "/news/**", "/products/**").hasAnyRole(Roles.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/news/**", "/products/**").hasAnyRole(Roles.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/news/**", "/products/**").hasAnyRole(Roles.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/orders/**").hasAnyRole(Roles.CUSTOMER.name())
                .antMatchers(HttpMethod.PUT, "/orders/**").hasAnyRole(Roles.CUSTOMER.name())
                .antMatchers(HttpMethod.DELETE, "/orders/**").hasAnyRole(Roles.CUSTOMER.name())
                .anyRequest().authenticated()
                .and()
                .httpBasic();

        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

