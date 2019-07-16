package com.yy.maoyi.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("maoyiDown")
public class MaoYiDown extends MaoYiAction{

	
	
	@GetMapping("/downFile")
	@ResponseBody
	public void downFile(String user, String password,String queryNum, HttpServletResponse response) throws Exception {

		LoginModel status = parseLogin(user, password);

		if(Objects.equals(status.getStatus(), "-1")) {
			logger.info(status.getMessage());
			return;
		}
		InputStream is = maoYiService.downFile(queryNum);
		byte[] buffer = readInputStream(maoYiService.downFile(queryNum));

		if (null != buffer && buffer.length > 0) {
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition","attachment;filename="+queryNum+".pdf");
			response.addHeader("Content-Length", "" + buffer.length);
			OutputStream toClient = response.getOutputStream();
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		}
	}
	
	
	private byte[] readInputStream(InputStream fis) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=fis.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        fis.close();  
        return outStream.toByteArray();

	}	
	
}
