package com.mychanel.repository;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.mychanel.model.Post;
import com.mychanel.model.User;
import com.mychanel.repository.config.SpringMongoConfig;

@Repository
public class PostRepository {
	
	ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	MongoOperations mongoOperation = (MongoOperations) ctx.getBean("mongoTemplate");
	

	public String persist(Post post){
		
		mongoOperation.save(post); // now user object got the created id. 
		System.out.println("post id : " + post.getId());
		
		return post.getId() ;
		
	}
	
	
	
	public void findAll(){
		List<Post> listPosts = mongoOperation.findAll(Post.class); 
		
		for(Post p:listPosts){
			System.out.println(p.getFileName() + " --- ID-->" +  p.getId());
			
		}
			
			
		System.out.println("4. Number of user = " + listPosts.size());
	}
}
