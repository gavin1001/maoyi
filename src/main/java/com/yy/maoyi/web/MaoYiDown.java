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

@Controller
@RequestMapping("maoyiDown")
public class MaoYiDown extends MaoYiAction {

	@GetMapping("/downFile")
	@ResponseBody
	public void downFile(String user, String password, String queryNum, HttpServletResponse response) throws Exception {

		LoginModel status = parseLogin(user, password);

		if (Objects.equals(status.getStatus(), "-1")) {
			logger.info(status.getMessage());
			return;
		}
		InputStream is = maoYiService.downFile(queryNum);
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

		byte[] fileData = SignPdf.sign("123456", System.getProperty("user.dir") + "/key/" + "keystore.p12", //
				is, //
				System.getProperty("user.dir") + "/icon/" + user + ".png", 100, 290);
		if (null != fileData && fileData.length > 0) {
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename=" + queryNum + ".pdf");
			response.addHeader("Content-Length", "" + fileData.length);
			OutputStream toClient = response.getOutputStream();
			response.setContentType("application/octet-stream");
			toClient.write(fileData);
			toClient.flush();
			toClient.close();
		}
		File file = new File(System.getProperty("user.dir") + "/file/" + queryNum + ".pdf");

		if (file.exists()) {
			file.delete();
		}

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
