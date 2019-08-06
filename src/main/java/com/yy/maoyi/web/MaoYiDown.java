package com.yy.maoyi.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yy.maoyi.ca.SignPdf;
import com.yy.maoyi.entity.User;

@Controller
@RequestMapping()
public class MaoYiDown extends MaoYiAction {

	/**
	 * 
	 * @param user
	 * @param password
	 * @param cusCiqNo
	 * @param isSignature
	 * @param pdfType     1:报关单 2：放行单
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("maoyi/getPdf")
	@ResponseBody
	public String downFile(String user, String password, String cusCiqNo, String isSignature, String pdfType,
			HttpServletResponse response) throws Exception {
		User users = this.isExistUser(user, password);
		LoginModel status = null;
		InputStream is = null;
		if (Objects.equals(pdfType, "1")) {// 报关单
			if (Objects.equals(users.getDownBG(), "0")) {
				logger.info("没有下载报关单的权限，用户名为:" + user);
				return "没有下载报关单的权限，用户名为:" + user;
			}

			status = parseLogin(users);
			if (Objects.equals(status.getStatus(), "-1")) {
				logger.info(status.getMessage());
				return "查询出问题";
			}

			is = maoYiService.downBGFile(cusCiqNo);
		} else if (Objects.equals(pdfType, "2")) {// 放行单

			if (Objects.equals(users.getDownFX(), "0")) {
				logger.info("没有下载放行单的权限，用户名为:" + user);
				return "没有下载放行单的权限，用户名为:" + user;
			}

			status = parseLogin(users);
			if (Objects.equals(status.getStatus(), "-1")) {
				logger.info(status.getMessage());
				return "查询出问题";
			}

			is = maoYiService.downFXFile(cusCiqNo);
		}
//		byte[] buffer = readInputStream(is);

//		File file = new File(System.getProperty("user.dir") + "/file/" + queryNum + ".pdf");
//		if (!file.exists()) {
//			file.createNewFile();
//		}

//		FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "/file/" + queryNum + ".pdf");
//
//		byte[] b = new byte[1024];
//
//		int length;
//		while ((length = is.read(b)) > 0) {
//			fos.write(b, 0, length);
//		}
//		is.close();
//		fos.close();
		byte[] fileData = null;
		if (Objects.equals(isSignature, "0")) {// 不签章
			fileData = readInputStream(is);
		} else if (Objects.equals(isSignature, "1")) {// 签章
			int x = 100;
			int y = 290;
			if (Objects.equals(pdfType, "2")) {
				x = -50;
			}
			fileData = SignPdf.sign("101012", System.getProperty("user.dir") + "/key/" + "cert.p12", //
					is, //
					System.getProperty("user.dir") + "/icon/" + user + ".png", x, y);
		}

		if (null != fileData && fileData.length > 0) {
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + cusCiqNo + ".pdf");
			response.addHeader("Content-Length", "" + fileData.length);
			OutputStream toClient = response.getOutputStream();
			response.setContentType("application/octet-stream");
			toClient.write(fileData);
			toClient.flush();
			toClient.close();
		}
		File file = new File(System.getProperty("user.dir") + "/file/" + cusCiqNo + ".pdf");

		if (file.exists()) {
			file.delete();
		}

		this.saveLog(cusCiqNo, status, pdfType);
		return "success";
	}

	protected byte[] readInputStream(InputStream fis) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = fis.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		fis.close();
		return outStream.toByteArray();

	}

}
