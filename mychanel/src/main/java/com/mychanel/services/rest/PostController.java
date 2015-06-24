package com.mychanel.services.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mychanel.model.Post;
import com.mychanel.repository.PostRepository;

@RestController
@RequestMapping("/service")
public class PostController {

	
	@RequestMapping(value = "/getposts/{id}", method = RequestMethod.GET)
	public List<PostVO> getPosts(@PathVariable String id){
		List<PostVO> lista = new ArrayList<PostVO>();
		PostRepository pr = new PostRepository();
				
		List<Post> posts  = pr.findAll(id);
		
		for (Post p:posts){
			//remover esse if
			if ( p.getThumbNail()!=null){
				System.out.println("addzado video " + p.getFileName());
				PostVO postVO = new PostVO();				
				postVO.setFileName(p.getFileName());
				postVO.setThumbNail(p.getThumbNail());
				lista.add(postVO);	
				
			}
			
		}
		
		
		return lista ;
		
	}
	
	
}
