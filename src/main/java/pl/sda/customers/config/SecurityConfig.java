package pl.sda.customers.config;

import static pl.sda.customers.config.SecurityUserRole.CUSTOMER_READ;
import static pl.sda.customers.config.SecurityUserRole.CUSTOMER_WRITE;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/public/**").permitAll()
            .and()
            .csrf().disable()
            .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("{noop}admin")
            .roles(CUSTOMER_READ, CUSTOMER_WRITE)
            .and()
            .withUser("test")
            .password("{noop}test")
            .roles(CUSTOMER_READ);
    }
}
