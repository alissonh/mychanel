package com.mychanel.repository.test;

import com.mychanel.repository.storage.MCStorage;

public class TestStorage {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MCStorage storage = new MCStorage();
		
		//storage.saveFile("thaisteste");
		
		//System.out.println("+++++OK salved sucessfully+++++");
		
		
		storage.readFile();
		
		System.out.println("+++++OK readed sucessfully+++++");

	}

}
