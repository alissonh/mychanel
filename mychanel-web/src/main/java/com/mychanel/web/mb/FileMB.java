package com.mychanel.web.mb;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@ManagedBean
@ViewScoped
public class FileMB implements Serializable{

	StreamedContent media;
	
	private UploadedFile file;
	
	public FileMB(){
		
		
		String fullPath ="C:/temp/Diaz.mp4";       
        File downloadFile = new File(fullPath);
        FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(downloadFile);
			  media = new DefaultStreamedContent(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
      
		
		
	}
	 
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }

	
	
    public void handleFileUpload(FileUploadEvent event) {
        System.out.println("Foooii Filéee!!");
    	UploadedFile file = event.getFile();
    	
    	 System.out.println("Arquivo enviado:" + file.getFileName());
    	
    	 //Chamar o Rest
    	RestTemplate restTemplate = new RestTemplate();
    	 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
    	 final String filename=file.getFileName();
    	 map.add("name", filename);
    	 map.add("filename", filename);
    	 ByteArrayResource contentsAsResource = new ByteArrayResource(file.getContents()){
    	             @Override
    	             public String getFilename(){
    	                 return filename;
    	             }
    	         };
    	 map.add("file", contentsAsResource);
    	 String result = restTemplate.postForObject("http://localhost:8080/mychanel/upload", map, String.class);
    	 
    	 System.out.println("O resultado retornado pelo Server foi " + result);
    	 ////////////////////
    	 
    	 //Arquivo upado meu video -1532273339564d5e17175b12844d502d65c407a3070afc2ed8
    	 //outro -6532743794424a989270da4308a1de111a16d3984859f3c7c
    	 
    	 FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
         FacesContext.getCurrentInstance().addMessage(null, message);
    	//application code
    	}
    
    public void upload(){
		 System.out.println("Foooii Filéee!!");
		 
		 if(file != null) {
			 System.out.println("Arquivo upado:" + file.getFileName()); 
			    
			//Chamar o Rest
		    	RestTemplate restTemplate = new RestTemplate();
		    	 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		    	
		    	 map.add("name", file.getFileName());
		    	 map.add("filename", file.getFileName());
		    	 ByteArrayResource contentsAsResource = new ByteArrayResource(file.getContents()){
		    	             @Override
		    	             public String getFilename(){
		    	                 return file.getFileName();
		    	             }
		    	         };
		    	 map.add("file", contentsAsResource);
		    	 String result = restTemplate.postForObject("http://localhost:8080/mychanel/upload", map, String.class);
		    	 
		    	 System.out.println("O resultado retornado pelo Server foi " + result);
		    	 ////////////////////
			 
			    FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
	            FacesContext.getCurrentInstance().addMessage(null, message);
	        }

		 
	}
	
    public void streamVideo() throws FileNotFoundException{
    	//InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("video.mp4");
    	//InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("video.mp4");
        
    	 String fullPath ="C:/temp/Diaz.mp4";       
         File downloadFile = new File(fullPath);
         FileInputStream inputStream = new FileInputStream(downloadFile);    	
         media = new DefaultStreamedContent(inputStream);

    	
    }
    
    
    public StreamedContent getMedia() {      
    	
    	return media;
    }

    public void setMedia(StreamedContent media) {
        this.media = media;
    }

	
}
