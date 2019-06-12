package com.yy.maoyi.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.yy.maoyi.entity.OperaLog;
import com.yy.maoyi.entity.User;
import com.yy.maoyi.service.OperaLogService;
import com.yy.maoyi.service.UserService;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;
import org.springframework.beans.factory.annotation.Autowired;
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

	private static final Logger logger = LoggerFactory.getLogger(MaoYiAction.class);


	@Autowired
	private UserService userService;

	@Autowired
	private OperaLogService operaLogService;

	@GetMapping("/test")
	public String test(String u,String p){
		User user = new User();
		user.setUname(u);
		user.setPassword(p);
		User users = userService.getUserByEntity(user);

		return users.getUname();

	}

	@GetMapping("/abc")
	public String abc() {
		System.out.println("abc");
		return "abc";
	}
	
	@GetMapping("/getData")
    public String menuList(String user, String password,String queryNum) throws Exception {
		String valueCode = "";
		String valueName = "";

		User u = new User();
		u.setUname(user);
		u.setPassword(password);
		User users = userService.getUserByEntity(u);
		if(users==null){
			logger.info("没有访问权限:[{}]",user);
			valueCode = "001";
			valueName = "没有权限访问";
			String reValue="{\"ok\":false,\"errorCode\":"+valueCode+",\"mygType\":null,\"data\":{\"message\":"+valueName+"！\"},\"errors\":null,\"messageList\":[],\"messageType\":null}";
			return reValue;
		}


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
			valueCode = "001";
			valueName = "用户名和密码错误";
			String reValue="{\"ok\":false,\"errorCode\":"+valueCode+",\"mygType\":null,\"data\":{\"message\":"+valueName+"！\"},\"errors\":null,\"messageList\":[],\"messageType\":null}";
			return reValue;
		}else if(Objects.equals(urlString, "-2") || Objects.equals(urlString, "-3")) {
			valueCode = "002";
			valueName = "系统错误";
			String reValue="{\"ok\":false,\"errorCode\":"+valueCode+",\"mygType\":null,\"data\":{\"message\":"+valueName+"！\"},\"errors\":null,\"messageList\":[],\"messageType\":null}";
			return reValue;
		}else if(Objects.equals(urlString, "-4")) {
			valueCode = "001";
			valueName = "请完成实名验证。。。";
			String reValue="{\"ok\":false,\"errorCode\":"+valueCode+",\"mygType\":null,\"data\":{\"message\":"+valueName+"！\"},\"errors\":null,\"messageList\":[],\"messageType\":null}";
			return reValue;
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
			logger.info("header:[{}]",headerString);
			MaoYiService.headerMap.put("Cookie", headerString);
		}
		String string = maoYiService.getData(queryNum);//("I20190000224038701");
		String matString = "{\"ok\":false";
		if(string.startsWith(matString)) {
			logger.info("查询内容出错:[{}]",string);
			return string;
		}
		
//		UserCountService ucsCountService =  new UserCountService();
//		ucsCountService.dual(user);
		OperaLog operaLog = new OperaLog();
		operaLog.setOperaDate(new Date());
		operaLog.setOperaNum(queryNum);
		operaLog.setUname(user);
		operaLogService.add(operaLog);
		logger.info("将信息存入到数据库[{}]", operaLog);
		return string;
    }

}
