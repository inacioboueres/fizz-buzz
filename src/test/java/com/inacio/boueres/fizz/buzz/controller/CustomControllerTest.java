package com.inacio.boueres.fizz.buzz.controller;

import static com.jayway.restassured.RestAssured.when;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inacio.boueres.fizz.buzz.Application;
import com.inacio.boueres.fizz.buzz.entity.CustomParam;
import com.inacio.boueres.fizz.buzz.entity.Rules;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringApplicationConfiguration(classes = Application.class)  
@WebAppConfiguration  
@IntegrationTest("server.port:0")  
public class CustomControllerTest {

	@Value("${local.server.port}") 
	int port;
	
	ObjectMapper mapper = null;

	@Before
	public void setUp() {

		RestAssured.port = port;
		RestAssured.registerParser("text/plain", Parser.JSON);
		mapper = new ObjectMapper();
	}

	//Test of custom Return
	@Test
	public void customReturnOk() {
		String ret = "1, 2, Fizz, 4, Buzz" ;
		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1 2 3 4 5");
		cp.setOrderId("U");
		cp.setTypeId("D");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
	
	@Test
	public void customReturnOkAscending() {
		String ret = "1, 2, Fizz, 4, Buzz" ;
		
		CustomParam cp = new CustomParam();
		cp.setNumbers("5 4 3 2 1");
		cp.setOrderId("A");
		cp.setTypeId("D");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
	
	@Test
	public void customReturnOkDescending() {
		String ret = "Buzz, 4, Fizz, 2, 1" ;
		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1 2 3 4 5");
		cp.setOrderId("D");
		cp.setTypeId("D");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
	
	@Test
	public void customReturnOkRepetition() {
		String ret = "1, 2, Fizz, 4, Buzz, Fizz, Fizz Buzz" ;
		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1 2 3 4 5 31 53");
		cp.setOrderId("U");
		cp.setTypeId("R");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
	
	@Test
	public void customReturnOkBoth() {
		String ret = "1, 2, Fizz, 4, Buzz, Fizz, Fizz Buzz, Fizz Buzz" ;
		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1 2 3 4 5 31 53 30");
		cp.setOrderId("U");
		cp.setTypeId("B");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
	
	@Test
	public void customReturnOkRules() {
		String ret = "1, 2, Fizz, 4, Buzz, Fizz, Woof" ;
		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1 2 3 4 5 6 7");
		cp.setOrderId("U");
		cp.setTypeId("D");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		cp.getRules().add(new Rules(new BigDecimal("7"),"Woof"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
	
	@Test
	public void customReturnOkWithChars() {
		String ret = "1, 2, Fizz, 4, Buzz" ;
		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1 2ssdfxckwlfthi!@#$%¨&*()_+qwertyuiop´[asdfghjklç~]zxcvbnm,;3 4 5");
		cp.setOrderId("U");
		cp.setTypeId("D");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
	
	@Test
	public void customReturnOkLimitNumbers() {
		//didn't test the body because I don't have the output
		CustomParam cp = new CustomParam();
		cp.setNumbers("1-1000000");
		cp.setOrderId("U");
		cp.setTypeId("D");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.OK.value());

	}
	
	@Test
	public void customReturnOkBigNumbers() {
		String ret = "1, Buzz" ;

		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1 20000000000000000000000000000000000000000");
		cp.setOrderId("U");
		cp.setTypeId("D");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
	
	@Test
	public void customFailLimitNumbers() {
		String ret = "Too bad, you choose too many numbers, and this overload our servers, please try again with fewer numbers" ;

		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1-1000001");
		cp.setOrderId("U");
		cp.setTypeId("D");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.INSUFFICIENT_STORAGE.value())
		.body("error", Matchers.is(ret));

	}
	
	@Test
	public void customFailInvalidOrder() {
		String ret = "The Json parameter is invalid!" ;

		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1-1000");
		cp.setOrderId("X");
		cp.setTypeId("D");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.EXPECTATION_FAILED.value())
		.body("error", Matchers.is(ret));

	}
	
	@Test
	public void customFailInvalidType() {
		String ret = "The Json parameter is invalid!" ;

		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1-1000");
		cp.setOrderId("U");
		cp.setTypeId("X");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.EXPECTATION_FAILED.value())
		.body("error", Matchers.is(ret));

	}
	
	@Test
	public void customFailInvalidRules() {
		String ret = "The Json parameter is invalid!" ;

		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1-1000");
		cp.setOrderId("U");
		cp.setTypeId("D");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("5"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		param = param.replace("5", "X");
		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.EXPECTATION_FAILED.value())
		.body("error", Matchers.is(ret));

	}
	
	@Test
	public void customFailInvalidRulesZero() {
		String ret = "The Rules Id must be greater than 0!" ;

		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1-1000");
		cp.setOrderId("U");
		cp.setTypeId("D");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("0"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.EXPECTATION_FAILED.value())
		.body("error", Matchers.is(ret));

	}
	
	@Test
	public void customFailInvalidRulesNegative() {
		String ret = "The Rules Id must be greater than 0!" ;

		
		CustomParam cp = new CustomParam();
		cp.setNumbers("1-1000");
		cp.setOrderId("U");
		cp.setTypeId("D");
		cp.setRules(new ArrayList<Rules>());
		cp.getRules().add(new Rules(new BigDecimal("3"),"Fizz"));
		cp.getRules().add(new Rules(new BigDecimal("-1"),"Buzz"));
		
		String param = "";
		try {
			param = mapper.writeValueAsString(cp);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		when().get("/custom/{param}", param)
		.then().statusCode(HttpStatus.EXPECTATION_FAILED.value())
		.body("error", Matchers.is(ret));

	}
	
	
	@Test
	public void customFailInvalidJson() {
		String ret = "The Json parameter is invalid!" ;
		
		when().get("/custom/{param}", "{sadsd}")
		.then().statusCode(HttpStatus.EXPECTATION_FAILED.value())
		.body("error", Matchers.is(ret));

	}
	
	
	
	
}
