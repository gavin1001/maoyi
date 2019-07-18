//package com.yy.maoyi.service;
//
//import java.util.Objects;
//
//public class Test {
//
//	public static void main(String[] args) {
//
//		String itValue = "2|Wrong verifycode.<br>1|用户名或密码错误！";
//		
//		String it[] = itValue.split("<br>");
//		
//		if(it.length==1) {
//			
//			String realString = itValue.split("|")[0];
//			if(Objects.equals(realString, "1")) {
//				//用户名或密码错误
//				System.out.println("-------------minghumingerror");
//			}else if(Objects.equals(realString, "2")){
//				//验证码错误
//				System.out.println("-------------yanzhengmaerror");
//			}
//			
//		}else {
//			
//			boolean flag = false;
//			for(String relString:it) {
//				String realString = itValue.split("|")[0];
//				if(Objects.equals(realString, "1")) {
//					//用户名或密码错误
//					flag =true;
//					System.out.println("-------------minghumingerror");
//				}else if(Objects.equals(realString, "2")){
//					//验证码错误
//					System.out.println("-------------yanzhengmaerror");
//				}
//			}
//			
//		}
//		
//	}
//	
//	
//
//}
