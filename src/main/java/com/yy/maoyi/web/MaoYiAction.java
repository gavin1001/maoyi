package com.yy.maoyi.web;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import com.yy.maoyi.service.MaoYiService;
import com.yy.maoyi.service.UserCountService;
import com.yy.maoyi.tools.CryptUtils;
import com.yy.maoyi.tools.https.HttpClientUtil;

@RestController
@RequestMapping("maoyi")
public class MaoYiAction {

	
	@GetMapping("/abc")
	public String abc() {
		System.out.println("abc");
		return "abc";
	}
	
	@GetMapping("/getData")
    public String menuList(String user, String password,String queryNum) throws Exception {
        System.out.println("-------------");
		MaoYiService maoYiService = new MaoYiService();
		String username = user;//"zdbg1231";
		String psw = password;//"!zdbg1231";
		String pswString = CryptUtils.GetMD5Code(psw);
		maoYiService.getHeader();
		
		Map<String, String> loginMap = maoYiService.getLogin();
		String itString = loginMap.get("It");
		String execution = loginMap.get("execution");
		String vertString = loginMap.get("vert");
		
		String urlString = maoYiService.getLoginRequest(username,pswString,vertString,itString,execution,0);
		if(Objects.equals(urlString, "-1")) {
			return "error";
		}
		MaoYiService.headerMap.remove("Content-Type");
		MaoYiService.headerMap.remove("Origin");
		MaoYiService.headerMap.put("P3P", "CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\"");
		MaoYiService.headerMap.put("Host","swapp.singlewindow.cn");
		MaoYiService.headerMap.put("Upgrade-Insecure-Requests", "1");
		HttpResponse response = HttpClientUtil.getResponseByPost(urlString, MaoYiService.headerMap, null, "UTF-8");
	
		if(response.getStatusLine().getStatusCode()==302) {
			Header[] headers = response.getHeaders("Set-Cookie");
			String headerString = "";
			for(Header h:headers) {
				headerString+=h.getValue();
			}
			headerString = maoYiService.dualCookie(headerString);
			String arg[] = headerString.split(";");
			headerString = "";
			headerString+=arg[0]+";"+arg[1]+";";
			System.out.println(headerString);
			MaoYiService.headerMap.put("Cookie", headerString);
		}
		String string = maoYiService.getData(queryNum);//("I20190000224038701");
		String matString = "{\"ok\":false";
		if(string.startsWith(matString)) {
			System.out.println("查询内容出错");
			return string;
		}
		
		UserCountService ucsCountService =  new UserCountService();
		ucsCountService.dual(user);
		System.out.println(string);
		return string;
    }
	
	
}
