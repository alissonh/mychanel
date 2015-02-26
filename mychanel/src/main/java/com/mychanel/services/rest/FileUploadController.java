package com.mychanel.services.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mychanel.model.Post;

import com.mychanel.repository.storage.MCStorage;
import com.mychanel.util.UtilFunc;

@RestController
public class FileUploadController {
    
    @RequestMapping(value="/upload", method=RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }
    
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file){
    	String name = file.getOriginalFilename() ;
    	if (!file.isEmpty()) {
            try {
                          	
            	System.out.print("Enviado arquivo " +  file.getOriginalFilename());
            	
            	byte[] bytes = file.getBytes();              

            	
            	MCStorage storage = new MCStorage();
            	
            	
            	
            	// Storage file in MongoloidDB uheahua
            	//String ret =storage.saveFile(bytes, file.getOriginalFilename());            	
            	Post ret =storage.saveFile(bytes); 	
                           	
            	System.out.println("Thumbnail = " + ret.getThumbNail());
                
                return "You successfully uploaded " + name + " into " +  ret.getFileName();
                
                //Enviado arquivo black metal idol.mp4O ID gerado eh -6532743794424a989270da4308a1de111a16d3984859f3c7c
                //O Mime Type eh video/mp4
                //O Miime Type é video/mp4
                //-6532743794424a989270da4308a1de111a16d3984859f3c7c Salvo com sucesso !!!!
                //post id : 54d15e05daaaa41a4d85c1fe                
                
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
    
}