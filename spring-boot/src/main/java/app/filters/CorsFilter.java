package app.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import app.properties.ReportConstants;
import java.util.List;
import java.util.Arrays;


public class CorsFilter extends ReportConstants implements Filter{
    private final List<String> allowedOrigins = Arrays.asList("http://localhost:4200"); 


    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {
        // String origin = "*";
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String method = request.getMethod();

            String origin = request.getHeader("Origin");
            response.setHeader("Access-Control-Allow-Origin", allowedOrigins.contains(origin) ? origin : "");
            response.setHeader("Vary", "Origin");


        // this origin value could just as easily have come from a database
        // response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods",
                "POST,GET,OPTIONS,DELETE,PUT");
        response.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader(
                "Access-Control-Allow-Headers",
                "Origin,Accept,X-Requested-With,Content-Type,Authorization,x-authorization");
        if ("OPTIONS".equals(method)) {
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, x-authorization");
            response.setHeader("Access-Control-Max-Age", "3600");

            response.setStatus(HttpStatus.OK.value());
        }
        else {
            chain.doFilter(req, res);
        }
    }

    @Override
        public void init(FilterConfig filterConfig) {
    }

    @Override
        public void destroy() {
    }
}