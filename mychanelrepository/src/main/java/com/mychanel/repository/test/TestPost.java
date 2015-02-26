package com.mychanel.repository.test;

import com.mychanel.model.Post;
import com.mychanel.repository.PostRepository;
import com.mychanel.util.UtilFunc;

public class TestPost {

	public static void main(String[] args) {
		PostRepository pr = new PostRepository();
		
		Post p = new Post();
		
		p.setFileName(UtilFunc.generateFileID());
		
		pr.persist(p);
		
		System.out.print("Persistido post id = " + p.getId() + " de nome " + p.getFileName());
		

	}

}
