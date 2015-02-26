package com.mychanel.web.mb;

 import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.mychanel.web.bean.Person;
import com.mychanel.web.http.RestClient;


@ManagedBean
@ViewScoped
public class UserMB implements Serializable{

	private Person user = new Person();
	
	
public Person getUser() {
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

public void edit(){
		System.out.println(" edit a parada");
}
	
public void logout(){
	System.out.println(" logout a parada");
	}


public void test(){
	
	// RestTemplate restTemplate = new RestTemplate();
	
	//Person p = new Person();
	//p.setIdade(21);
	//p.setNome("Thais");
	
	

	
	System.out.println("+++++++++++++++TESTANDO UM GET +++++++++++++++++"); 
	
	//RestTemplate restTemplate2 = new RestTemplate();
	//Person response2 = restTemplate2.getForObject("http://localhost:8080/mychanel/service/sayhelloj/Ursius" , Person.class);

	
	try{
	RestClient rest = new RestClient();
	  String ret =rest.doGet("/service/sayhelloj/Ursius");
	  System.out.println(" ===retorno ====== ");
	  System.out.print(ret);
	  System.out.println(" ============= ");
	
	}
	catch(Exception erro){
		System.out.print("Erro do lado client:" + erro.toString());
		
	}
	
	//System.out.println(" test a parada COM GET e o nome retornado eh " + response2.getNome());
}



public String insertUser(){
	
	RestTemplate restTemplate = new RestTemplate();		
	Gson gson = new Gson();   
	String gsonStr= gson.toJson(this.user);   	
	System.out.println(" + + + + O GSON da parada eh ++++++ ");	
	System.out.println(gsonStr);
	
	//(Deserialization)
	//BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);   
	
	try{
	   RestClient rest = new RestClient();
	   String ret =rest.doPost("/service/insertnewuser/", gsonStr);
	   System.out.println(" ===retorno ====== ");
	   System.out.print(ret);
	   System.out.println(" ============= ");
	  
	    Person pepe = gson.fromJson(ret, Person.class);	  
	    System.out.println("Parsed JSON ->" + pepe.getNome());    
	   
	
	}
	catch(Exception erro){
		System.out.print("Erro do lado client:" + erro.toString());
		
	}
	
	new FacesMessage(FacesMessage.SEVERITY_INFO,    "Hello Hello ;]","Congratulations , now you have your Chanel !");
	 
	return "/index.jsf?faces-redirect=true" ;
}



public void testPost(){
	
	 RestTemplate restTemplate = new RestTemplate();
	
	Person p = new Person();
	p.setIdade(21);
	p.setNome("Thais");
	
	
	Gson gson = new Gson();
    
	String gsonStr= gson.toJson(p);
    	
	System.out.println(" + + + + O GSON da parada eh ++++++ ");
	
	System.out.println(gsonStr);
	
	//(Deserialization)
	//BagOfPrimitives obj2 = gson.fromJson(json, BagOfPrimitives.class);   
	
	try{
	RestClient rest = new RestClient();
	  String ret =rest.doPost("/service/saynewhelloj/", gsonStr);
	  System.out.println(" ===retorno ====== ");
	  System.out.print(ret);
	  System.out.println(" ============= ");
	  
	    Person pepe = gson.fromJson(ret, Person.class);
	  
	    System.out.println("Parsed JSON ->" + pepe.getNome());
	
	}
	catch(Exception erro){
		System.out.print("Erro do lado cliente :" + erro.toString());
		
	}
	
	
}




}
