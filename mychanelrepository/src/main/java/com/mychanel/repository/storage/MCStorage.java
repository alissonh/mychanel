package com.mychanel.repository.storage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Repository;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
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
	
public String saveFile(byte[] bytes, String fileName){
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");

		DBObject metaData = new BasicDBObject();
		metaData.put("username", "thais");
		metaData.put("email", "thaissantos@gmail.com");

		
		try {
			
			
			String mime = getMagicMimeType(bytes);
			 InputStream inputStream = new ByteArrayInputStream(bytes);
			
			 System.out.println("O Miime Type é " +  mime);
			gridOperations.store(inputStream, fileName, mime, metaData);

		} catch (Exception e) {
			e.printStackTrace();
			return "nok" ;
		} 
		

		System.out.println( fileName + " Salvo com sucesso !!!!");	
		return "ok";
	}
	


//public String getMimeTypeTika(){
//	
//	Tika tika = new Tika();
//	InputStream in = null;
//	FileOutputStream out = null;
//	String mimeType= "ah sei la bro";
//	try{
//	   out = new FileOutputStream("c:/temp/thais2_gata.png");
//	   IOUtils.copy(in, out);
//	    mimeType = tika.detect(in);
//	}catch(Exception e){
//	   e.printStackTrace();
//	} 
//	
//	return mimeType;
//	
//}

public String getMimeType(byte[] b){
		
	Magic parser = new Magic() ;
	// getMagicMatch accepts Files or byte[],
	// which is nice if you want to test streams
	MagicMatch match;
	String mime = "";
	try {
		match = parser.getMagicMatch(b, true);
		mime = match.getMimeType();
		
	} catch (MagicParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (MagicMatchNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (MagicException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 return mime ;
			
	}



public static String getMimeTypen(String fileUrl)
	    throws java.io.IOException, MalformedURLException
	  {
	    String type ="DESCONHECIDO";
	    URL u = new URL(fileUrl);
	    URLConnection uc = null;
	    uc = u.openConnection();
	    type = uc.getContentType();
	    return type;
	  }


public String getMagicMimeType(String arquivo){
	ContentInfoUtil util = new ContentInfoUtil();
	ContentInfo info=null;
	try {
		 info = util.findMatch(arquivo);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return info.getName() ;
	
}

public String getMagicMimeType(byte[] bytes){
	ContentInfoUtil util = new ContentInfoUtil();
	ContentInfo info=null;
	String mimeType = "UNKNOWN";
	info = util.findMatch(bytes);
	
	String typeFile = info.getName();
	
	if (typeFile.equals("png")){
		mimeType = "image/png";
	}
	else if (typeFile.equals("mpeg")){
		mimeType = "audio/mpeg3";
	}
	
	else if (typeFile.equals("jpeg")){
		mimeType = "image/jpeg";
	}
	
	else if (typeFile.equals("mp4a")){
		//video/mp4
		mimeType = "video/mp4";
	}
	
	else if (typeFile.equals("avi")){
		mimeType="video/avi";
	}
	
	System.out.println("O Mime Type eh " + mimeType);
	return mimeType ;
	
}
	
public static byte[] getBytes(InputStream is) throws IOException {

    int len;
    int size = 102478;
    byte[] buf;

    if (is instanceof ByteArrayInputStream) {
      size = is.available();
      buf = new byte[size];
      len = is.read(buf, 0, size);
    } else {
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      buf = new byte[size];
      while ((len = is.read(buf, 0, size)) != -1)
        bos.write(buf, 0, len);
      buf = bos.toByteArray();
    }
    return buf;
  }

	
	public void readFile() {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");


		List<GridFSDBFile> result = gridOperations.find(new Query().addCriteria(Criteria.where(
				"filename").is("grace.mp3")));
		for (GridFSDBFile file : result) {
			try {
				System.out.println( "File Name -->" + file.getFilename());
				System.out.println("Content Type -->" + file.getContentType());
				
				//save as another image
				file.writeTo("c:/temp/grace_upada.mp3");
				//para pegar o inputStream
				//file.getInputStream();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Done file readed");

	}
	
	
	
	
}
