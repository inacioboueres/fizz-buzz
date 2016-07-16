package com.inacio.boueres.fizz.buzz.controller;

import static com.jayway.restassured.RestAssured.when;

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

import com.inacio.boueres.fizz.buzz.Application;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.parsing.Parser;

@RunWith(SpringJUnit4ClassRunner.class)  
@SpringApplicationConfiguration(classes = Application.class)  
@WebAppConfiguration  
@IntegrationTest("server.port:0")  
public class ClassicControllerTest {

	@Value("${local.server.port}") 
	int port;

	@Before
	public void setUp() {

		RestAssured.port = port;
		RestAssured.registerParser("text/plain", Parser.JSON);
	}

	//Test of classicFast Return
	@Test
	public void classicReturnOk() {
		String ret = "1, 2, Fizz, 4, Buzz" ;

		when().get("/classicFast/{numbers}", "1 2 3 4 5")
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
	
	@Test
	public void classicReturnOkWithChars() {
		String ret = "1, 2, Fizz, 4, Buzz" ;

		
		when().get("/classicFast/{numbers}", "1 2ssdfxckwlfthi!@#$%¨&*()_+qwertyuiop´[asdfghjklç~]zxcvbnm,;3 4 5")
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
	
	@Test
	public void classicReturnOkLimitNumbers() {
		//didn't test the body because I don't have the output
		when().get("/classicFast/{numbers}", "1-1000000")
		.then().statusCode(HttpStatus.OK.value());

	}
	
	@Test
	public void classicReturnFailLimitNumbers() {
		String ret = "Too bad, you choose too many numbers, and this overload our servers, please try again with fewer numbers" ;

		
		when().get("/classicFast/{numbers}", "1-1000001")
		.then().statusCode(HttpStatus.INSUFFICIENT_STORAGE.value())
		.body("error", Matchers.is(ret));

	}
	
	@Test
	public void classicReturnFailBigNumbers() {
		String ret = "You choose a very large number, please try the 'ClassicLN' version" ;

		
		when().get("/classicFast/{numbers}", "1 20000000000000000000000000000000000000000")
		.then().statusCode(HttpStatus.PRECONDITION_FAILED.value())
		.body("error", Matchers.is(ret));

	}
	
	//Test of classicBigNumber Return
	@Test
	public void classicBNReturnOk() {
		String ret = "1, 2, Fizz, 4, Buzz" ;

		when().get("/classicBigNumber/{numbers}", "1 2 3 4 5")
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
	
	@Test
	public void classicBNReturnOkWithChars() {
		String ret = "1, 2, Fizz, 4, Buzz" ;

		
		when().get("/classicBigNumber/{numbers}", "1 2ssdfxckwlfthi!@#$%¨&*()_+qwertyuiop´[asdfghjklç~]zxcvbnm,;3 4 5")
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
	
	@Test
	public void classicBNReturnOkLimitNumbers() {
		//didn't test the body because I don't have the output
		when().get("/classicBigNumber/{numbers}", "1-1000000")
		.then().statusCode(HttpStatus.OK.value());

	}
	
	@Test
	public void classicBNReturnFailLimitNumbers() {
		String ret = "Too bad, you choose too many numbers, and this overload our servers, please try again with fewer numbers" ;

		
		when().get("/classicBigNumber/{numbers}", "1-1000001")
		.then().statusCode(HttpStatus.INSUFFICIENT_STORAGE.value())
		.body("error", Matchers.is(ret));

	}
	
	@Test
	public void classicBNReturnOkBigNumbers() {
		String ret = "1, Buzz" ;

		
		when().get("/classicBigNumber/{numbers}", "1 20000000000000000000000000000000000000000")
		.then().statusCode(HttpStatus.OK.value())
		.body("result", Matchers.is(ret));

	}
}
