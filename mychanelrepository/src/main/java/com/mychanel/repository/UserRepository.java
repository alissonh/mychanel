package com.mychanel.repository;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.mychanel.model.User;
import com.mychanel.repository.config.SpringMongoConfig;

@Repository
public class UserRepository {
	
	ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
	

	public String persist(User user){
		
		mongoOperation.save(user); // now user object got the created id. 
		System.out.println("user : " + user.getId());
		
		return user.getId() ;
		
	}
	
	
	
	public void findAll(){
		List<User> listUser = mongoOperation.findAll(User.class); 
		
		for(User u:listUser){
			System.out.println(u.getFirstName() + " --- ID-->" +  u.getId());
			
		}
			
			
		System.out.println("4. Number of user = " + listUser.size());
	}
}
