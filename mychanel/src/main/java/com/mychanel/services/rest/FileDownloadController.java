package com.mychanel.services.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mychanel.repository.storage.MCStorage;
 
@Controller
public class FileDownloadController {
     
    /**
     * Size of a byte buffer to read/write file
     */
    private static final int BUFFER_SIZE = 40540096;
             
    /**
     * Path of the file to be downloaded, relative to application's directory
     */
    private String filePath = "/downloads/SpringProject.zip";
     
    /**
     * Method for handling file download request from client
     */
    @RequestMapping(value="/down/{id}", method=RequestMethod.GET)
    public void doDownload(@PathVariable String id,HttpServletRequest request,
            HttpServletResponse response ) throws IOException {
 
        // get absolute path of the application
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        System.out.println("appPath = " + appPath);
 
        // construct the complete absolute path of the file
        String fullPath ="C:/temp/black_metal.mp4";       
        File downloadFile = new File(fullPath);
        FileInputStream inputStream = new FileInputStream(downloadFile);
       

        // get MIME type of the file
      //  String mimeType = context.getMimeType(fullPath);
      //  if (mimeType == null) {
            // set to binary type if MIME mapping not found
            String mimeType = "application/octet-stream";
      //  }
       
 
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
 
    }
    
    
    @RequestMapping(value="/stream/{id}", method=RequestMethod.GET)
    public void getVideoStream(@PathVariable String id,HttpServletRequest request,
            HttpServletResponse response ) throws IOException {
 
        // get absolute path of the application
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        
        MCStorage storage = new MCStorage();
        InputStream inputStream = storage.getFileStream(id);
       
    
        String mimeType = "application/octet-stream";
           
 
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength(inputStream.available());
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                "video.mp4");
        response.setHeader(headerKey, headerValue);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
 
    }
    
    
    @RequestMapping(value="/image/{id}", method=RequestMethod.GET)
    public void getImage(@PathVariable String id,HttpServletRequest request,
            HttpServletResponse response ) throws IOException {
 
        // get absolute path of the application
        ServletContext context = request.getServletContext();
        String appPath = context.getRealPath("");
        
        MCStorage storage = new MCStorage();
        InputStream inputStream = storage.getFileStream(id);
       
    
        String mimeType = "image/png";
           
 
        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength(inputStream.available());
 
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                "imagem.png");
        response.setHeader(headerKey, headerValue);
 
        // get output stream of the response
        OutputStream outStream = response.getOutputStream();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;
 
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
 
        inputStream.close();
        outStream.close();
 
    }
    
    
    
    
}
