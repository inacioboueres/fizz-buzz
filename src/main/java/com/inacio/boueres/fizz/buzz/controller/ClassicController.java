package com.inacio.boueres.fizz.buzz.controller;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.inacio.boueres.fizz.buzz.exceptions.TooManyNumbersException;

/**
 * 
 * @author Inacio da Cunha Boueres Filho
 * ClassicController is the controller responsible to perform the main task of the RESP API
 * It provide 2 main methods, the first is based on performance so it use a Long class to manipulate the numbers
 * the second is based on large numbers, make possible to get return for any number tried so it use BigDecimal instead of Long
 *
 */
@RestController
public class ClassicController {

	//to avoid out of memory the amount of different numbers tested is limited
	private final static Long MAX_NUMBERS = 1000000L;
	
	//used on reminder 
	private final static BigDecimal TREE = new BigDecimal("3");
	private final static BigDecimal FIVE = new BigDecimal("5");

	/**
	 * 
	 * @param numbers
	 * @return Json with the system result
	 * This is the basic class to solve FizzBuzz game, this implementation is based on Long to provide a faster result  
	 */
    @RequestMapping(value = "/classicFast/{numbers}", method = RequestMethod.GET )
    public String solveClassic(@PathVariable  String numbers) {
    	
    	//a Pattern is used to find inside the string only the numbers (1 2 3 4 5), and the 'ranges' (1-59 30-40) any other thing is considerate a separator
    	Pattern p = Pattern.compile("(\\d+-\\d+)|\\d+");
    	Matcher m = p.matcher(numbers);
    	//the output will be stored on a StringBuilder because use a string is too inefficient 
    	StringBuilder sb = new StringBuilder();
    	
    	//n will count how many numbers has been tested
    	int n = 0;
    	
    	while (m.find()) {
    	  String number = m.group();
    	  //test if the first pattern is a number or a range
    	  if(number.contains("-")){//range
    		  String[] range = number.split("-");
    		  long min = Long.valueOf(range[0]);
    		  long max = Long.valueOf(range[1]);
    		  if(min<=max){//if the first number is grater than the last (9-4) it will be ignored
	    		  for(long i = min; i<=max;i++){//for each number in range it will perform the check
	    			  check(sb, i);
	    			  n++;
	    			  if(n>MAX_NUMBERS){ //number of tests is grater than the limit
	    				  throw new TooManyNumbersException();
	    			  }
	    		  }
    		  }
    	  }else{//number
    		  check(sb, Long.valueOf(number));//perform check
    		  n++;
    		  if(n>MAX_NUMBERS){//number of tests is grater than the limit
				  throw new TooManyNumbersException();
			  }
    	  }
    	}
    	//as the result is based on ", " on the end, the last 2 characters of the strings are removed
    	if(sb.length()>0){
    		sb = sb.deleteCharAt(sb.length()-1);
    		sb = sb.deleteCharAt(sb.length()-1);
    	}
    	//return the Json
    	return "{\"result\": \""+sb.toString()+"\"}";
    }
    
    /**
     * 
     * @param numbers
     * @return Json with the system result
     * This is the improved class to solve FizzBuzz game, this implementation is based on BigDecimal to provide a greater range of results  
     */
    @RequestMapping(value = "/classicBigNumber/{numbers}", method = RequestMethod.GET )
    public String solveClassicBigNumber(@PathVariable  String numbers) {
    	
    	//a Pattern is used to find inside the string only the numbers (1 2 3 4 5), and the 'ranges' (1-59 30-40) any other thing is considerate a separator
    	Pattern p = Pattern.compile("(\\d+-\\d+)|\\d+");
    	Matcher m = p.matcher(numbers);
    	//the output will be stored on a StringBuilder because use a string is too inefficient 
    	StringBuilder sb = new StringBuilder();
    	
    	//n will count how many numbers has been tested
    	int n = 0;
    	
    	while (m.find()) {
    	  String number = m.group();
    	  //test if the first pattern is a number or a range
    	  if(number.contains("-")){//range
    		  String[] range = number.split("-");
    		  BigDecimal min = new BigDecimal(range[0]);
    		  BigDecimal max = new BigDecimal(range[1]);
    		  if(min.compareTo(max)<=0){//if the first number is grater than the last (9-4) it will be ignored
	    		  for(BigDecimal i = min; i.compareTo(max)<=0;i = i.add(BigDecimal.ONE)){//for each number in range it will perform the check
	    			  check(sb, i);
	    			  n++;
	    			  if(n>MAX_NUMBERS){ //number of tests is grater than the limit
	    				  throw new TooManyNumbersException();
	    			  }
	    		  }
    		  }
    	  }else{
    		//number
    		  check(sb, new BigDecimal(number));//perform check
    		  n++;
    		  if(n>MAX_NUMBERS){//number of tests is grater than the limit
				  throw new TooManyNumbersException();
			  }
    	  }
    	}
    	//as the result is based on ", " on the end, the last 2 characters of the strings are removed
    	if(sb.length()>0){
    		sb = sb.deleteCharAt(sb.length()-1);
    		sb = sb.deleteCharAt(sb.length()-1);
    	}
    	//return the Json
    	return "{\"result\": \""+sb.toString()+"\"}";
    }
    
    /**
     * 
     * @param sb 
     * @param n
     * 
     * Passing the StringBuilder that are retrieving the return, it will check n and add the appropriate piece of text to the StringBuilder
  	 *
     */
    private void check (StringBuilder sb, Long n){
    	StringBuilder ret = new StringBuilder();
    	if(n%3==0){//if it is divisible by 3 add "Fizz"
    		ret.append("Fizz");
    		if(n%5==0){//if it is divisible by 3 and 5 add "Fizz Buzz"
        		ret.append(" Buzz");
        	}
    	}else if(n%5==0){//if it is divisible by 5 add "Buzz"
    		ret.append("Buzz");
    	}else{// else add number
    		ret.append(n);
    	}
    	sb.append(ret+", ");//append the result + ", " to make the output more acceptable
    }
    
    /**
     * 
     * @param sb 
     * @param n
     * 
     * Passing the StringBuilder that are retrieving the return, it will check n and add the appropriate piece of text to the StringBuilder
  	 *
     */
    private void check (StringBuilder sb, BigDecimal n){
    	StringBuilder ret = new StringBuilder();
    	if(n.remainder(TREE).equals(BigDecimal.ZERO)){//if it is divisible by 3 add "Fizz"
    		ret.append("Fizz");
    		if(n.remainder(FIVE).equals(BigDecimal.ZERO)){//if it is divisible by 3 and 5 add "Fizz Buzz"
        		ret.append(" Buzz");
        	}
    	}else if(n.remainder(FIVE).equals(BigDecimal.ZERO)){//if it is divisible by 5 add "Buzz"
    		ret.append("Buzz");
    	}else{// else add number
    		ret.append(n);
    	} 
    	sb.append(ret+", ");//append the result + ", " to make the output more acceptable
    }
    
    //handlers for exceptions
    
    //Generic
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDefaultException(Exception e) {
    	return "{\"error\": \""+ "Exception"+"\"}";
    }
    
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDefaultException(RuntimeException e) {
    	return "{\"error\": \""+ "RuntimeException"+"\"}";
    }
    
    
    //expected erros
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDefaultException(NumberFormatException e) {
    	return "{\"error\": \""+"You choose a very large number, please try the 'ClassicLN' version"+"\"}";
    }
    
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDefaultException(TooManyNumbersException e) {
    	return "{\"error\": \""+"Too bad, you choose too many numbers, and this overload our servers, please try again with fewer numbers"+"\"}";
    }

}
