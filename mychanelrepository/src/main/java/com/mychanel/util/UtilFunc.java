package com.mychanel.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class UtilFunc {

	public static final  String tempDir= "c:/temp/" ;
	
	  public static void main (String... arguments) {
		    try {
		      //Initialize SecureRandom
		      //This is a lengthy operation, to be done only upon
		      //initialization of the application
		      SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

		      //generate a random number
		      String randomNum = new Integer(prng.nextInt()).toString();

		      //get its digest
		      MessageDigest sha = MessageDigest.getInstance("SHA-1");
		      byte[] result =  sha.digest(randomNum.getBytes());

		      System.out.println("Random number: " + randomNum);
		      System.out.println("Message digest: " + hexEncode(result));
		    }
		    catch (NoSuchAlgorithmException ex) {
		      System.err.println(ex);
		    }
		  }
	
	  public static String generateFileID(){
		  String id="" ;
		  try {
		      //Initialize SecureRandom
		      //This is a lengthy operation, to be done only upon
		      //initialization of the application
		      SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

		      //generate a random number
		      String randomNum = new Integer(prng.nextInt()).toString();

		      //get its digest
		      MessageDigest sha = MessageDigest.getInstance("SHA-1");
		      byte[] result =  sha.digest(randomNum.getBytes());

		     
		     id= randomNum +  hexEncode(result);
		    }
		    catch (NoSuchAlgorithmException ex) {
		      System.err.println(ex);
		    }
		   System.out.println("O ID gerado eh " + id);
		   return id.replaceAll("-", "");
	  }
	  
	  static private String hexEncode(byte[] aInput){
		    StringBuilder result = new StringBuilder();
		    char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
		    for (int idx = 0; idx < aInput.length; ++idx) {
		      byte b = aInput[idx];
		      result.append(digits[ (b&0xf0) >> 4 ]);
		      result.append(digits[ b&0x0f]);
		    }
		    return result.toString();
	  }
	  
	  
	 
	
}
