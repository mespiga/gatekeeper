package app.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.session.SessionManagementFilter;




@Configuration
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    CorsFilter corsFilter() {
        CorsFilter filter = new CorsFilter();
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {        
                
        http
        .addFilterBefore(corsFilter(), SessionManagementFilter.class)
        .csrf().disable();

        http
        .authorizeRequests()
            .anyRequest().authenticated();

        http.addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
                        
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/room")
            .antMatchers("/rooms")
            .antMatchers("/event")
            .antMatchers("/events")
            .antMatchers("/observation")
            .antMatchers("/observations") 
            .antMatchers("/api/user")
            .antMatchers("/api/authenticate")
            .antMatchers("/api/product")
            .antMatchers("/api/websocket")
//             .antMatchers("/user/**")
            .antMatchers(HttpMethod.POST, "/**")
            .antMatchers(HttpMethod.OPTIONS, "/**");
    }
    
}