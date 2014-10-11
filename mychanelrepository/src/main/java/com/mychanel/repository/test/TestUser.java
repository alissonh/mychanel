package com.mychanel.repository.test;

import java.util.Date;

import com.mychanel.model.User;
import com.mychanel.repository.UserRepository;

public class TestUser {

	public static void main(String[] args) {
		
		
		UserRepository ur = new UserRepository();
		
		User user = new User();
		
		user.setDatanasc(new Date());
		user.setFirstName("Alisson");
		user.setLastName("Ricardo");
		user.setPassword("1234");
		user.setEmail("alisson@gmail.com");
		ur.persist(user);
		
		System.out.println("---------------OK -------------------");
		
		ur.findAll();
		
		//Thais --- ID-->5438a564d5220debbb910e3c
		//Alisson --- ID-->5438a5c3d52294d670f27cff
		//4. Number of user = 2
		
		

	}

}
