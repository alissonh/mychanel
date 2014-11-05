package com.mychanel.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.mychanel.web.http.MultipartUtility;

public class UploaderTest {

	
	public static void main(String[] args) {
        String charset = "UTF-8";
        File uploadFile1 = new File("C:/Users/user/Pictures/KM/Thais5.jpg");
        File uploadFile2 = new File("e:/Test/PIC2.JPG");
        String requestURL = "http://localhost:8080/mychanel/upload";
 
        try {
            MultipartUtility multipart = new MultipartUtility(requestURL, charset);
             
            multipart.addHeaderField("User-Agent", "CodeJava");
            multipart.addHeaderField("Test-Header", "Header-Value");
             
            multipart.addFormField("description", "Cool Pictures");
            multipart.addFormField("keywords", "Java,upload,Spring");
             
            multipart.addFilePart("fileUpload", uploadFile1);
           // multipart.addFilePart("fileUpload", uploadFile2);
 
            List<String> response = multipart.finish();
             
            System.out.println("Servidor respondeu:");
             
            for (String line : response) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

	

}
