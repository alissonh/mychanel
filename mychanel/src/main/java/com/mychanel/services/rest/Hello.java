package com.mychanel.services.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	 
	System.out.println("foi enviado " + name);
	Person ps = new Person();
	  ps.setIdade(20);
	  ps.setNome("Naninha");	  
	  return ps;
	 }


@RequestMapping(value = "/saynewhelloj/", method = RequestMethod.POST)
//http://localhost:8080/mychanel/service/saynewhelloj/
public Person postNewGreetingJson(@RequestBody  Person pessoa) {
	System.out.println("dum dum dum dum  vai um post");
	  System.out.println("A pessoa que foi postada: " + pessoa.getNome());  
	  Person ps = new Person();
	  ps.setIdade(20);
	  ps.setNome("MA OEEEEE");	  
	  return ps;
	 }

	
}
