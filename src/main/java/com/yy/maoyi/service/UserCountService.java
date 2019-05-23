package com.yy.maoyi.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.scheduling.annotation.Async;

public class UserCountService {

	
	private static String path = System.getProperty("user.dir")+"/u";
	
	public UserCountService() {
		isExist();
	}
	
	private void isExist() {
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
	}
	
	@Async
	public int dual(String user) throws IOException {
		
		File file = new File(path+"/"+user);
		if(file.exists()) {
			//向文件里写入内容
			write(file);
		}else {
			try {
				boolean flag = file.createNewFile();
				write(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			//向文件里写入内容;
		}
		
		return -1;
	}
	
	
	private int write(File file) throws IOException {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file, true);
			writer.write(",");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(writer!=null)
				writer.close();
		}
		
		return 0;
	}
	
	public static void main(String[] args) throws IOException {
		
		UserCountService userCountService = new UserCountService();
		
		for(int i = 0 ;i<100;i++) {
			userCountService.dual("123");
			
		}
		
		
	}
	
}
