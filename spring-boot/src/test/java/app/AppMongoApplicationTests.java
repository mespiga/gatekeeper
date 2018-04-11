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

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.OutputCapture;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertTrue;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import app.data.jpa.domain.*;
import app.data.jpa.repository.*;
import app.properties.ReportConstants;
import java.util.List;
import java.util.ArrayList;



/**
 *
 * @author Miguel Espiga
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(AppMongoApplication.class)
@IntegrationTest
@WebIntegrationTest
public class AppMongoApplicationTests {

	@Value("${server.port}")
	private int port;

	private String testNumber = "300";

	@ClassRule
	public static OutputCapture outputCapture = new OutputCapture();

	// @Test
	// public void testHttpsClient() throws Exception {
	// 		ResponseEntity<String> entity = new TestRestTemplate()
	// 				.getForEntity("http://localhost:" + this.port + "/1/maps/test", String.class);
	// 	assertTrue("Wrong body:\n" + entity.getBody(),
	// 			entity.getBody().contains("a"));
	// 	System.out.println(entity.getBody());
	// }

	// @Test
	// public void testCreateProduct() throws Exception {
	// 		ResponseEntity<String> entity = new TestRestTemplate()
	// 				.postForEntity("http://localhost:" + this.port + "/api/product","call-center" ,String.class);
	// 	assertTrue("Wrong body:\n" + entity.getBody(),
	// 			entity.getBody().contains(" "));
	// 	System.out.println(entity.getBody());
	// }

	// @Test
	// public void testCreateProductWifi() throws Exception {
	// 		ResponseEntity<String> entity = new TestRestTemplate()
	// 				.postForEntity("http://localhost:" + this.port + "/api/product","wifi" ,String.class);
	// 	assertTrue("Wrong body:\n" + entity.getBody(),
	// 			entity.getBody().contains(" "));
	// 	System.out.println(entity.getBody());
	// }

	// @Test
	// public void testCreateUser() throws Exception {
	// 		ResponseEntity<String> entity = new TestRestTemplate()
	// 				.postForEntity("http://localhost:" + this.port + "/api/user",
	// 								"{\"firstName\": \"y\",\"lastName\": \"y\",\"email\": \"y1\",\"password\": \"y1\",\"company\": \"y\"}" ,
	// 								  String.class);
	// 	assertTrue("Wrong body:\n" + entity.getBody(),
	// 			entity.getBody().contains("y"));
	// 	System.out.println(entity.getBody());
	// }

	// @Test
	// public void testCreateUser2() throws Exception {
	// 		ResponseEntity<String> entity = new TestRestTemplate()
	// 				.postForEntity("http://localhost:" + this.port + "/api/user",
	// 								"{\"firstName\": \"qa\",\"lastName\": \"aqa\",\"email\": \"qqaaqa\",\"password\": \"qaqaqa\",\"company\": \"qz\"}" ,
	// 								  String.class);
	// 	assertTrue("Wrong body:\n" + entity.getBody(),
	// 			entity.getBody().contains("a"));
	// 	System.out.println(entity.getBody());
	// }

	// @Test
	// public void testCreateChildren() throws Exception {
	// 		ResponseEntity<String> entity = new TestRestTemplate()
	// 				.postForEntity("http://localhost:" + this.port + "/user/17/children",
	// 								"{\"firstName\": \"ad\",\"lastName\": \"ad\",\"email\": \"ad\",\"password\": \"ad\",\"company\": \"ad\",\"notifications\":[], \"childrens\":[], "
	// 								+"\"products\": ["
 //                                +"\"id\":\"1\","
 //                                +"\"name\": \"wifi\","
 //                                +"\"sites\": ["
 //                                  +"  {"
 //                                    +"    \"id\":\"1\","
 //                                      +"  \"city\": \"Elvas\","
 //                                        +"\"state\": \"Portugal\","
 //                                        +"\"zip\": \"560212\","
 //                                        +"\"name\": \"elvas\""
 //                                    +"},"
 //                                    +"{"
 //                                      +"  \"id\":\"2\","
 //                                        +"\"city\": \"Mafra\","
 //                                        +"\"state\": \"Portugal\","
 //                                        +"\"zip\": \"5t0212\","
 //                                        +"\"name\": \"mafra\""
 //                                    +"}"
 //                                +"],"
 //                                +"\"img\":           \"data:image/png;base64,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\","
 //                                +"\"description\":   \"Cisco WiFi in the cloud. Understanding your customer habits!\","
 //                                +"\"phone\":         \"+351 213 213 312\","
 //                                +"\"email\":         \"wifi@cilnet.pt\","
 //                                +"\"active\":        \"true\","
 //                                +"\"url\":           \"/wifi\""
 //                            +"}]}" , String.class);
	// 	assertTrue("Wrong body:\n" + entity.getBody(),
	// 			true);
	// 	System.out.println(entity.getBody());
	// }



	// @Test
	// public void testLogin() throws Exception {
	// 		ResponseEntity<String> entity = new TestRestTemplate()
	// 				.postForEntity("http://localhost:" + this.port + "/api/authenticate",
	// 								"{\"email\": \"a\",\"password\": \"a\"}" ,
	// 								  String.class);
	// 	assertTrue("Wrong body:\n" + entity.getBody(),
	// 			entity.getBody().contains("token"));
	// 	System.out.println(entity.getBody());
	// }

	// @Test
	// public void testGetChildrens() throws Exception {
		// 	ResponseEntity<String> entity = new TestRestTemplate()
		// 			.getForEntity("http://localhost:" + this.port + "/user/1/children",
		// 							  String.class);
		// assertTrue("Wrong body:\n" + entity.getBody(),
		// 		entity.getBody().contains(""));
		// System.out.println(entity.getBody());
	// }

	// @Test
	// public void testGetVisitorsNoPermission() throws Exception {
	// 		ResponseEntity<String> entity = new TestRestTemplate()
	// 				.getForEntity("http://localhost:" + this.port + "/user/1/cmx/visitors?areas=ola&period=today",
	// 								  String.class);
	// 	assertTrue("Wrong body:\n" + entity.getBody(),
	// 			entity.getBody().contains("No permission"));
	// 	System.out.println(entity.getBody());
	// }

	// @Test
	// public void testGetVisitors() throws Exception {
	// 		ResponseEntity<String> entity = new TestRestTemplate()
	// 				.getForEntity("http://localhost:" + this.port + "/user/1/cmx/visitors?areas=DevNetCampus&period=today",
	// 								  String.class);
	// 	assertTrue("Wrong body:\n" + entity.getBody(),
	// 			entity.getBody().contains("startTime"));
	// 	System.out.println(entity.getBody());
	// }
}
