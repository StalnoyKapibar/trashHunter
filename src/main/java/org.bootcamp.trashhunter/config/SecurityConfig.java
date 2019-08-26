package org.bootcamp.trashhunter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean
    public NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select email, password, true"
                        + " from user where email=?")
                .authoritiesByUsernameQuery("select email, dtype "
                        + "from user where email=?")
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/")
                .loginProcessingUrl("/login")
                .failureUrl("/?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll();

        http.logout()
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

        http
                .authorizeRequests()
                .antMatchers("/registration", "/activate/*", "/api/user/resend_email_for_token_recovery").anonymous()
                .antMatchers("/reset_password", "/reset/**").anonymous()
                .antMatchers("/", "/api/offer/**", "/css/*", "/js/*", "/img/*", "/activate/*", "/**", "/favorites", "/chat/**").permitAll();
                //.antMatchers("/admin/**").access("hasAnyRole('Taker','Sender')").anyRequest().authenticated();
    }


//    PersistentTokenRepository persistentTokenRepository(){
//        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
//        tokenRepositoryImpl.setDataSource(dataSource);
//        return tokenRepositoryImpl;
//    }
}




