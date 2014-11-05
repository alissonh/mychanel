package com.mychanel.web.mb;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@ManagedBean
@ViewScoped
public class FileMB {

	private UploadedFile file;
	 
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
	
	
}
