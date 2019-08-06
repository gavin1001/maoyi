package com.yy.maoyi.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yy.maoyi.entity.OperaLog;
import com.yy.maoyi.entity.User;
import com.yy.maoyi.service.MaoYiService;
import com.yy.maoyi.service.OperaLogService;
import com.yy.maoyi.service.UserService;
import com.yy.maoyi.tools.CryptUtils;
import com.yy.maoyi.tools.https.HttpClientUtil;

@RestController
@RequestMapping("maoyi")
public class MaoYiAction {

	@Value("${server.switch}")
	private int switchs;

	@Autowired
	private UserService userService;

	protected static Map<String, String> sessIdMap = new HashMap<String, String>();
	protected static final Logger logger = LoggerFactory.getLogger(MaoYiAction.class);

	@Autowired
	private OperaLogService operaLogService;

	protected MaoYiService maoYiService = new MaoYiService();

	@GetMapping("/test")
	public String test(String u, String p) {
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
	public String menuList(String user, String password, String queryNum) throws Exception {
		User users = this.isExistUser(user, password);
		if (Objects.equals(users.getQueryData(), "0")) {
			logger.info("没有查询权限:[{}]", users.getUname() + "-queryData-" + users.getQueryData());
			return "{\"ok\":false ,'code':'-1','mess':'没有查询权限'}";
		}

		LoginModel status = parseLogin(users);

		if (Objects.equals(status.getStatus(), "-1")) {
			logger.info(status.getMessage());
			return status.getMessage();
		}

		String string = maoYiService.getData(queryNum);// ("I20190000224038701");
		String matString = "{\"ok\":false";

		if (string.startsWith(matString)) {
			logger.info("查询内容出错:[{}]", string);
			return string;
		}

//		UserCountService ucsCountService =  new UserCountService();
//		ucsCountService.dual(user);
		saveLog(queryNum, status, "0");
		return string;
	}

	protected void saveLog(String queryNum, LoginModel status, String type) {
		OperaLog operaLog = new OperaLog();
		operaLog.setOperaDate(new Date());
		operaLog.setOperaNum(queryNum);
		operaLog.setUname(status.getUser().getUname());
		operaLog.setOperaType(type);
		operaLogService.add(operaLog);
		logger.info("将信息存入到数据库[{}]", operaLog);
	}

	@GetMapping("/getCusList")
	public String getCusList(String user, String password, String flag, String startTime, String endTime)
			throws Exception {
		User users = this.isExistUser(user, password);

		LoginModel status = parseLogin(users);

		if (Objects.equals(status.getStatus(), "-1")) {
			logger.info(status.getMessage());
			return status.getMessage();
		}

		String string = maoYiService.getCusList(flag, startTime, endTime);

		return string;
	}

	@GetMapping("/getDecByCus")
	public String getDecByCus(String user, String password, String queryNum) throws Exception {
		User users = this.isExistUser(user, password);
		LoginModel status = parseLogin(users);

		if (Objects.equals(status.getStatus(), "-1")) {
			logger.info(status.getMessage());
			return status.getMessage();
		}

		String string = maoYiService.getDecByCus(queryNum);

		return string;

	}

	@GetMapping("/getGoodsType")
	public String getGoodsType(String user, String password, String goodsNum, String inOutFlag) throws Exception {
		User users = this.isExistUser(user, password);
		LoginModel status = parseLogin(users);

		if (Objects.equals(status.getStatus(), "-1")) {
			logger.info(status.getMessage());
			return status.getMessage();
		}

		String string = maoYiService.getGoodsType(goodsNum, inOutFlag);

		return string;

	}

	protected User isExistUser(String user, String password) {
		User u = new User();
		u.setUname(user);
		u.setPassword(password);
		User users = userService.getUserByEntity(u);
		return users;
	}

	protected LoginModel parseLogin(User users) throws Exception {
		LoginModel lModel = new LoginModel();

		String valueCode = "";
		String valueName = "";

		if (users == null) {
			logger.info("没有访问权限:[{},{}]");
			valueCode = "001";
			valueName = "没有权限访问";
			String reValue = "{\"ok\":false,\"errorCode\":" + valueCode + ",\"mygType\":null,\"data\":{\"message\":"
					+ valueName + "！\"},\"errors\":null,\"messageList\":[],\"messageType\":null}";

			lModel.setStatus("-1");
			lModel.setMessage(reValue);

			return lModel;
		}

		lModel.setUser(users);

		logger.info("访问校验通过:[{}],[{}]", users.getVuname(), users.getVpassword());

		MaoYiService maoYiService = new MaoYiService();

		MaoYiService.headerMap.remove("Content-Type");
		MaoYiService.headerMap.remove("Origin");
		MaoYiService.headerMap.put("P3P", "CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\"");
		MaoYiService.headerMap.put("Host", "swapp.singlewindow.cn");
		MaoYiService.headerMap.put("Upgrade-Insecure-Requests", "1");

		// 登录缓存
		boolean flag = false;
		String cacheSession = sessIdMap.get(users.getVuname());
		if (Objects.nonNull(cacheSession)) {
			MaoYiService.headerMap.put("Cookie", cacheSession);
			if (maoYiService.checkLogin()) {
				flag = true;
				logger.info("缓存登录，不需要再次登录");

			} else {
				flag = false;
			}

		} else {
			flag = false;
		}

		if (switchs == 0) {
			flag = false;
		}

		if (flag == false) {
			String username = users.getUname();// "zdbg1231";
			String psw = users.getPassword();// "!zdbg1231";
			String pswString = CryptUtils.GetMD5Code(psw);
			maoYiService.getHeader();

			Map<String, String> loginMap = maoYiService.getLogin();
			String itString = loginMap.get("It");
			String execution = loginMap.get("execution");
			String vertString = loginMap.get("vert");

			String urlString = maoYiService.getLoginRequest(username, pswString, vertString, itString, execution, 0);
			if (Objects.equals(urlString, "-1")) {
				valueCode = "001";
				valueName = "用户名和密码错误";
				String reValue = "{\"ok\":false,\"errorCode\":" + valueCode + ",\"mygType\":null,\"data\":{\"message\":"
						+ valueName + "！\"},\"errors\":null,\"messageList\":[],\"messageType\":null}";
				lModel.setStatus("-1");
				lModel.setMessage(reValue);
				return lModel;
			} else if (Objects.equals(urlString, "-2") || Objects.equals(urlString, "-3")) {
				valueCode = "002";
				valueName = "系统错误";
				String reValue = "{\"ok\":false,\"errorCode\":" + valueCode + ",\"mygType\":null,\"data\":{\"message\":"
						+ valueName + "！\"},\"errors\":null,\"messageList\":[],\"messageType\":null}";
				lModel.setStatus("-1");
				lModel.setMessage(reValue);
				return lModel;
			} else if (Objects.equals(urlString, "-4")) {
				lModel.setStatus("1");
				logger.info("30天警告");
				lModel.setMessage("30天警告");
				return lModel;
			}

			MaoYiService.headerMap.remove("Content-Type");
			MaoYiService.headerMap.remove("Origin");
			MaoYiService.headerMap.put("P3P",
					"CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\"");
			MaoYiService.headerMap.put("Host", "swapp.singlewindow.cn");
			MaoYiService.headerMap.put("Upgrade-Insecure-Requests", "1");

			HttpResponse response = HttpClientUtil.getResponseByPost(urlString, MaoYiService.headerMap, null, "UTF-8");

			if (response.getStatusLine().getStatusCode() == 302) {
				Header[] headers = response.getHeaders("Set-Cookie");
				String headerString = "";
				for (Header h : headers) {
					headerString += h.getValue();
				}
				headerString = maoYiService.dualCookie(headerString);
				String arg[] = headerString.split(";");
				headerString = "";
				headerString += arg[0] + ";" + arg[1] + ";";
				logger.info("header:[{}]", headerString);
				sessIdMap.put(username, headerString);
				MaoYiService.headerMap.put("Cookie", headerString);
				lModel.setStatus("1");
				lModel.setMessage("success");
				return lModel;

			} else {
				lModel.setStatus("-1");
				lModel.setMessage("状态码非302");
				return lModel;
			}
		} else {
			lModel.setStatus("1");
			lModel.setMessage("success");
			return lModel;
		}

	}

	protected class LoginModel {
		private String status;
		private String message;
		private User user;

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		private String password;

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
