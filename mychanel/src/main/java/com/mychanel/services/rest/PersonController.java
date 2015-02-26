package com.mychanel.services.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mychanel.repository.UserRepository;
import com.mychanel.model.User;

@RestController
@RequestMapping("/service")
public class PersonController {

	
	@RequestMapping(value = "/insertnewuser/", method = RequestMethod.POST)
	//http://localhost:8080/mychanel/service/saynewhelloj/
	public Person postNewUser(@RequestBody  Person pessoa) {
		System.out.println("dum dum dum dum INSERT NOVO uSUARIO!");
		  System.out.println("A pessoa que foi postada: " + pessoa.getNome());  
		  
		  UserRepository pr = new UserRepository();
		  User user = new User();
		  
		  user.setDatanasc(pessoa.getDatanasc());
		  user.setEmail(pessoa.getNome());
		  user.setFirstName(pessoa.getNome());
		  user.setLastName("");
		  user.setPassword(pessoa.getPassword());
		  user.setUsername(pessoa.getUsername());
		  
		  pr.persist(user);
		  
		  Person ps = new Person();
		  ps.setIdade(20);
		  ps.setNome("MA OEEEEE DEU CERTO!!!!");	  
		  return ps;
		 }
	
	
}
