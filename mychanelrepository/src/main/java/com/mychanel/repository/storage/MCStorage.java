package com.mychanel.repository.storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mychanel.repository.config.SpringMongoConfig;

@Repository
public class MCStorage {

	
	public void saveFile(String name){
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");

		DBObject metaData = new BasicDBObject();
		metaData.put("username", "thais");
		metaData.put("email", "thaissantos@gmail.com");

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("c:/temp/thais2.png");
			gridOperations.store(inputStream, "thais2.png", "image/png", metaData);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done file okokokokokk!!!!");		
	}
	
	
	public void readFile() {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");


		List<GridFSDBFile> result = gridOperations.find(new Query().addCriteria(Criteria.where(
				"filename").is("thais2.png")));
		for (GridFSDBFile file : result) {
			try {
				System.out.println( "File Name -->" + file.getFilename());
				System.out.println("File Name -->" + file.getContentType());
				
				//save as another image
				file.writeTo("c:/temp/thais2_gata.png");
				//para pegar o inputStream
				//file.getInputStream();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Done file readed");

	}
	
	
	
	
}
