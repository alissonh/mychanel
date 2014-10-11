package com.mychanel.services.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



	
@RestController
@RequestMapping("/service")
	
public class Hello {
@RequestMapping(value = "/sayhello/{name}", method = RequestMethod.GET)


//http://localhost:8080/mychanel/service/sayhello/Ursius
public String getGreeting(@PathVariable String name) {
	  String result="Hello "+name;  
	  return result;
	 }

	
	
@RequestMapping(value = "/sayhelloj/{name}", method = RequestMethod.GET,headers="Accept=application/json")
//http://localhost:8080/mychanel/service/sayhelloj/Ursius
public Person getGreetingJson(@PathVariable String name) {
	  Person ps = new Person();
	  ps.setIdade(20);
	  ps.setNome("Naninha");	  
	  return ps;
	 }
	
}
