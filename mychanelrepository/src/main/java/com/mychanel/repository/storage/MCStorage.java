package com.mychanel.repository.storage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.imageio.ImageIO;

import net.sf.jmimemagic.Magic;
import net.sf.jmimemagic.MagicException;
import net.sf.jmimemagic.MagicMatch;
import net.sf.jmimemagic.MagicMatchNotFoundException;
import net.sf.jmimemagic.MagicParseException;

import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
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
import com.mychanel.model.Post;
import com.mychanel.repository.PostRepository;
import com.mychanel.repository.config.SpringMongoConfig;
import com.mychanel.util.UtilFunc;




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
	

public Post saveFile(byte[] bytes){
	
	ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
	GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
	String fileNameGenerated = UtilFunc.generateFileID();
	String thumbnail = UtilFunc.generateFileID();
	Post post = new Post();
	
	DBObject metaData = new BasicDBObject();
	metaData.put("username", "thais");
	metaData.put("email", "thaissantos@gmail.com");

	
	try {
		
		
		String mime = getMagicMimeType(bytes);
		InputStream inputStream = new ByteArrayInputStream(bytes);
		
		System.out.println("O Miime Type é " +  mime);
		//Salva o arquivo
		gridOperations.store(inputStream, fileNameGenerated, mime, metaData);
		
		if (mime.equals("video/mp4")){
				//Gerar Thumbnail
				System.out.println("Gravando video no DIsco");
				this.writeFileToDisk(bytes, UtilFunc.tempDir + fileNameGenerated + ".mp4" );
				
				System.out.println("Gerando Thumbnail");
				byte[] byteThumb=  this.generateThumnail(UtilFunc.tempDir + fileNameGenerated + ".mp4", UtilFunc.tempDir + thumbnail + ".png");
				InputStream inputStreamThumb = new ByteArrayInputStream(byteThumb);
				gridOperations.store(inputStreamThumb, thumbnail, mime, metaData);
				System.out.println("Thumbnail gerado : " + thumbnail);
				
			
		}
		
		post.setFileName(fileNameGenerated);
		post.setFileType(mime);
		post.setThumbNail(thumbnail);
		
		
		//Persist in mongoDB
		PostRepository pr = new PostRepository();
		pr.persist(post);

	} catch (Exception e) {
		e.printStackTrace();
		
	} 
	

	System.out.println( fileNameGenerated + " Salvo com sucesso !!!!");	
	System.out.println( thumbnail + " é o ThumNail");	
	return post;
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

	
//only for test	
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
	
	
	// This method is used to get a file Stream from the Storage MongoDB
	public InputStream getFileStream(String fileName){
		
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
		GridFsOperations gridOperations = (GridFsOperations) ctx.getBean("gridFsTemplate");
		InputStream stream = null;

		List<GridFSDBFile> result = gridOperations.find(new Query().addCriteria(Criteria.where(
				"filename").is(fileName)));
		for (GridFSDBFile file : result) {
			try {
				System.out.println( "File Name retrieved -->" + file.getFilename());
				System.out.println("Content Type -->" + file.getContentType());
				
				
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				file.writeTo(out);
				stream = new ByteArrayInputStream(out.toByteArray());	
				out.close();
				out=null;
				System.out.println("avaliable do bixo: " + stream.available());
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("Done file streamed ");
		
		return stream;
		
		
		
	}
		
	
	 public  int writeFileToDisk(byte bWrite [] , String name){
		   
		   try{
		     
		      OutputStream os = new FileOutputStream(name);
		      for(int x=0; x < bWrite.length ; x++){
		         os.write( bWrite[x] ); // writes the bytes
		      }
		      os.close();
		     
		     
		   }catch(IOException e){
		      System.out.print("Exception");
		      e.printStackTrace();
		      return -1;
		      
		   }	
		   
		   return 0;
		   
	 }

	  	  
	  private byte[] readFileFromDisk(File file) throws IOException {

		  
		    ByteArrayOutputStream ous = null;
		    InputStream ios = null;
		    try {
		        byte[] buffer = new byte[4096];
		        ous = new ByteArrayOutputStream();
		        ios = new FileInputStream(file);
		        int read = 0;
		        while ( (read = ios.read(buffer)) != -1 ) {
		            ous.write(buffer, 0, read);
		        }
		    } finally { 
		        try {
		             if ( ous != null ) 
		                 ous.close();
		        } catch ( IOException e) {
		        }

		        try {
		             if ( ios != null ) 
		                  ios.close();
		        } catch ( IOException e) {
		        }
		    }
		    return ous.toByteArray();
		}
	  
	  
	  
	  private byte[] generateThumnail(String video , String image) throws IOException, JCodecException {
			
			int frameNumber = 150;					
							
			BufferedImage frame = FrameGrab.getFrame(new File(video), frameNumber);
						
			ImageIO.write(frame, "png", new File(image));		
			
			
			System.out.println("OKOK Thumnail");	
			
			return readFileFromDisk(new File(image));

		} 
	
}
