package app.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.http.HttpStatus;
import app.data.jpa.domain.*;
import app.data.jpa.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AuthenticationFilter extends OncePerRequestFilter {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UsexRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(request.getMethod().equals("OPTIONS")){
            log.info("Filtering Request with Header OPTIONS: " + request.toString());
        }
        log.info("Filtering Request with Header: " + request.toString());
        String xAuth = request.getHeader("x-authorization");
        if(xAuth == null || xAuth == ""){
            throw new SecurityException();
        }
        
        // validate the value in xAuth
        // String token = request.getHeader(this.tokenHeader);                          
        
        // The token is 'valid' so magically get a user id from it
        Usex user = new Usex(xAuth);
        String email = user.getClaimsFromToken("email");
        
        if(!user.validateToken()){
            throw new SecurityException();
        }
        
        
        // Create our Authentication and let Spring know about it
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, email, user.getAuthorities());
        

        SecurityContextHolder.getContext().setAuthentication(auth);            
        
        filterChain.doFilter(request, response);
    }

}