package com.mychanel.web.mb;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mychanel.web.bean.PostVO;
import com.mychanel.web.http.RestClient;

@ManagedBean
@ViewScoped
public class PostMB implements Serializable {

	List postList = null;
	
	public List getPostList() {
		return postList;
	}

	public void setPostList(List postList) {
		this.postList = postList;
	}

	public List<PostVO> listAllPosts(){
		
		//RestTemplate restTemplate2 = new RestTemplate();
		//Person response2 = restTemplate2.getForObject("http://localhost:8080/mychanel/service/sayhelloj/Ursius" , Person.class);
		List<PostVO> lista= new ArrayList<PostVO>() ;
		
		try{
			RestClient rest = new RestClient();
			  String ret =rest.doGet("/service/getposts/1");
			  System.out.println(" ===retorno ====== ");
			  System.out.print(ret);
			  System.out.println(" ============= ");	  
			  
			  
				  Gson gson = new Gson(); 			    
				  
				  Type listType = new TypeToken<ArrayList<PostVO>>() {}.getType();
	               lista = new Gson().fromJson(ret, listType);
	              
	              for (PostVO vo:lista){
	            	  System.out.println( " a porra do arquivo eh ->" + vo.getFileName());
	              }
			  
			  
			
			}
			catch(Exception erro){
				System.out.print("Erro do lado client:" + erro.toString());
				
			}
		
		return lista;
		
		//return this.postList;
	}
	
	
}
