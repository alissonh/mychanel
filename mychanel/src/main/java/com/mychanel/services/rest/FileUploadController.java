package com.mychanel.services.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mychanel.repository.storage.MCStorage;

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
               
//            	BufferedOutputStream stream = 
//                        new BufferedOutputStream(new FileOutputStream(new File("c:/temp/" + name + "-uploaded.jpg")));
//                stream.write(bytes);
//                stream.close();
            	
            	MCStorage storage = new MCStorage();
            	String ret =storage.saveFile(bytes, file.getOriginalFilename());
                System.out.println("Retorno = " + ret);
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
    
}