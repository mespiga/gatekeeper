/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package app;

// import org.h2.server.web.WebServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import app.data.jpa.domain.*;
import app.data.jpa.repository.*;
import app.properties.ReportConstants;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import app.utils.GetProperties;


@SpringBootApplication
@Configuration
@EnableConfigurationProperties
@ComponentScan
@EnableAutoConfiguration
public class AppMongoApplication extends SpringBootServletInitializer implements CommandLineRunner {


	@Override
	public void run(String... args) throws Exception {
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AppMongoApplication.class);
	}

	// CORS
	// @Bean
	// FilterRegistrationBean corsFilter(@Value("${tagit.origin:http://localhost:3000}") final String origin) {
	// 	return new FilterRegistrationBean(new Filter() {
	// 		public void doFilter(ServletRequest req, ServletResponse res,
	// 				FilterChain chain) throws IOException, ServletException {
	// 			HttpServletRequest request = (HttpServletRequest) req;
	// 			HttpServletResponse response = (HttpServletResponse) res;
	// 			String method = request.getMethod();
	// 			// this origin value could just as easily have come from a database
	// 			response.setHeader("Access-Control-Allow-Origin", origin);
	// 			response.setHeader("Access-Control-Allow-Methods",
	// 					"POST,GET,OPTIONS,DELETE");
	// 			response.setHeader("Access-Control-Max-Age", Long.toString(60 * 60));
	// 			response.setHeader("Access-Control-Allow-Credentials", "true");
	// 			response.setHeader(
	// 					"Access-Control-Allow-Headers",
	// 					"Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization,X-Authorization");
	// 			if ("OPTIONS".equals(method)) {
	// 				response.setStatus(HttpStatus.OK.value());
	// 			}
	// 			else {
	// 				chain.doFilter(req, res);
	// 			}
	// 		}

	// 		public void init(FilterConfig filterConfig) {
	// 		}

	// 		public void destroy() {
	// 		}
	// 	});
	// }

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppMongoApplication.class, args);		
	}

}
