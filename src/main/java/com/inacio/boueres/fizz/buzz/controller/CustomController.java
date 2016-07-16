package com.inacio.boueres.fizz.buzz.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inacio.boueres.fizz.buzz.entity.CustomParam;
import com.inacio.boueres.fizz.buzz.entity.Rules;
import com.inacio.boueres.fizz.buzz.exceptions.MalformedJsonException;
import com.inacio.boueres.fizz.buzz.exceptions.TooManyNumbersException;

/**
 * 
 * @author Inacio da Cunha Boueres Filho
 * CustomController is the controller responsible to perform the additional task of the RESP API
 * It provide 1 main methods, that is based on execute a custom game with the rules choose by player
 *
 */
@RestController
public class CustomController {

	//to avoid out of memory the amount of different numbers tested is limited
	private final static Long MAX_NUMBERS = 1000000L;
	

	/**
	 * 
	 * @param numbers
	 * @return Json with the system result
	 * This is the basic class to solve FizzBuzz game, this implementation is based on Long to provide a faster result  
	 */
    @RequestMapping(value = "/custom/{param}", method = RequestMethod.GET )
    public String solveCustom(@PathVariable  String param) {

    	//parse the Json param string to object
    	CustomParam cp = new CustomParam();
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		cp=mapper.readValue(param, CustomParam.class);
		} catch (JsonParseException e) {
			throw new MalformedJsonException();
		} catch (JsonMappingException e) {
			throw new MalformedJsonException();
		} catch (IOException e) {
			throw new MalformedJsonException();
		}
    	//the output will be stored on a StringBuilder because use a string is too inefficient 
    	StringBuilder sb = new StringBuilder();
    	//list of numbers that will be tested
    	List<BigDecimal> numbersList = retriveNumbers(cp);
    	if(cp.getOrderId().equals("A")){//Ascending order
    		Collections.sort(numbersList);
    	}else if(cp.getOrderId().equals("D")){
    		Collections.sort(numbersList, Collections.reverseOrder());
    	}else if(!cp.getOrderId().equals("U")){//exception if it's not an expected id
    		throw new MalformedJsonException();
    	}
    	
    	for(BigDecimal num : numbersList){
    		check(sb, num, cp);
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
     * @param cp
     * @return list of numbers
     * As one of the custom parameters is the order, Will be required to transform it into a number list;
     */
    private List<BigDecimal> retriveNumbers(CustomParam cp ){
    	List<BigDecimal> ret = new ArrayList<BigDecimal>();
    	//a Pattern is used to find inside the string only the numbers (1 2 3 4 5), and the 'ranges' (1-59 30-40) any other thing is considerate a separator
    	Pattern p = Pattern.compile("(\\d+-\\d+)|\\d+");
    	Matcher m = p.matcher(cp.getNumbers());
    	
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
	    			  ret.add(i);
	    			  n++;
	    			  if(n>MAX_NUMBERS){ //number of tests is grater than the limit
	    				  throw new TooManyNumbersException();
	    			  }
	    		  }
    		  }
    	  }else{
    		//number
    		  ret.add(new BigDecimal(number));
    		  n++;
    		  if(n>MAX_NUMBERS){//number of tests is grater than the limit
				  throw new TooManyNumbersException();
			  }
    	  }
    	}
    	return ret;
    }
    
    
    /**
     * 
     * @param sb 
     * @param n
     * 
     * Passing the StringBuilder that are retrieving the return, it will check n and add the appropriate piece of text to the StringBuilder
  	 *
     */
    private void check (StringBuilder sb, BigDecimal n, CustomParam cp  ){
    	StringBuilder ret = new StringBuilder();
    	for(Rules r : cp.getRules()){
    		if(cp.getTypeId().equals("D")){//Divisible 
    			if(n.remainder(r.getId()).equals(BigDecimal.ZERO)){
    				ret.append(r.getDesc()+" ");
    			}
    		}else if(cp.getTypeId().equals("R")){//Repetition 
    			if(n.toString().contains(r.getId().toString())){
    				ret.append(r.getDesc()+" ");
    			}
    		}else if(cp.getTypeId().equals("B")){//Both
    			if(n.remainder(r.getId()).equals(BigDecimal.ZERO)||n.toString().contains(r.getId().toString())){
    				ret.append(r.getDesc()+" ");
    			}
    		}else{//exception if it's not an expected id
        		throw new MalformedJsonException();
    		}
    		
    	}
    	
    	if(ret.length()==0){
			sb.append(n+", ");
		}else{
			ret = ret.deleteCharAt(ret.length()-1);
			sb.append(ret+", ");
		}
    	
//    	StringBuilder ret = new StringBuilder();
//    	if(n.remainder(TREE).equals(BigDecimal.ZERO)){//if it is divisible by 3 add "Fizz"
//    		ret.append("Fizz");
//    		if(n.remainder(FIVE).equals(BigDecimal.ZERO)){//if it is divisible by 3 and 5 add "Fizz Buzz"
//        		ret.append(" Buzz");
//        	}
//    	}else if(n.remainder(FIVE).equals(BigDecimal.ZERO)){//if it is divisible by 5 add "Buzz"
//    		ret.append("Buzz");
//    	}else{// else add number
//    		ret.append(n);
//    	} 
//    	sb.append(ret+", ");//append the result + ", " to make the output more acceptable
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
    
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDefaultException(MalformedJsonException e) {
    	return "{\"error\": \""+"The Json parameter is invalid!"+"\"}";
    }

}
