package com.yy.maoyi.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;

public class CaptchaUtil {

	private static String TESSDATA_FOLDER = LoadLibs.extractTessResources("tessdata").getAbsolutePath();
	public static String PATH = System.getProperty("user.dir")+"/img";
	

public static void removeBackground(ByteArrayOutputStream byteArrayOutputStream){
        //定义一个临界阈值
        int threshold = 150;
        
        InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray()); 
		DataInputStream dataInputStream = new DataInputStream(inputStream);
        
        try{
            BufferedImage img = ImageIO.read(dataInputStream);
            int width = img.getWidth();
            int height = img.getHeight();
            for(int i = 1;i < width;i++){
                for (int x = 0; x < width; x++){
                    for (int y = 0; y < height; y++){
                        Color color = new Color(img.getRGB(x, y));
//                        System.out.println("red:"+color.getRed()+" | green:"+color.getGreen()+" | blue:"+color.getBlue());
                        int num = color.getRed()+color.getGreen()+color.getBlue();
                        if(num >= threshold){
                            img.setRGB(x, y, Color.BLACK.getRGB());
                        }
                    }
                }
            }
            for(int i = 1;i<width;i++){
                Color color1 = new Color(img.getRGB(i, 1));
                int num1 = color1.getRed()+color1.getGreen()+color1.getBlue();
                for (int x = 0; x < width; x++)
                {
                    for (int y = 0; y < height; y++)
                    {
                        Color color = new Color(img.getRGB(x, y));
 
                        int num = color.getRed()+color.getGreen()+color.getBlue();
                        if(num==num1){
                            img.setRGB(x, y, Color.WHITE.getRGB());
                        }else{
                            img.setRGB(x, y, Color.BLACK.getRGB());
                        }
                    }
                }
            }
            File file = new File(CaptchaUtil.PATH+"/amd.jpg");
            if (!file.exists())
            {
                File dir = file.getParentFile();
                if (!dir.exists())
                {
                    dir.mkdirs();
                }
                try
                {
                    file.createNewFile();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            ImageIO.write(img, "jpg", file);
        }catch (Exception e){
            e.printStackTrace();
        }

}	
	
	
	public static String getCaptcha(ByteArrayOutputStream byteArrayOutputStream, int num) throws Exception {
		num = num + 1;
		String captcha = "";
		InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray()); 
		DataInputStream dataInputStream = new DataInputStream(inputStream);
		try {
			BufferedImage bufferedImage = ImageIO.read(dataInputStream);
			if (bufferedImage != null) {
				bufferedImage = tessgray(bufferedImage, bufferedImage.getHeight(), bufferedImage.getWidth());
				ITesseract instance = new Tesseract(); // JNA Interface Mapping
				instance.setDatapath(TESSDATA_FOLDER); // 设置tessdata位置
				instance.setLanguage("eng");
				captcha = instance.doOCR(bufferedImage);
				if (StringUtils.isNotBlank(captcha)) {
					captcha = captcha.replaceAll(" ", "");
					captcha = removeSeparator(captcha);
					if (captcha.length() > 4) {
						captcha = captcha.substring(0, 4);
					}
				}
			}
		} finally {
			if (captcha.length() != 4 && !isNumberZero(captcha) && num <= 20)
				getCaptcha(byteArrayOutputStream, num);
			if (dataInputStream != null) {
				dataInputStream.close();
			}
		}
		return captcha;
	}

	
	public static String getCaptcha(File file, int num) throws Exception {
		num = num + 1;
		String captcha = "";
		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			if (bufferedImage != null) {
				bufferedImage = tessgray(bufferedImage, bufferedImage.getHeight(), bufferedImage.getWidth());
				ITesseract instance = new Tesseract(); // JNA Interface Mapping
				instance.setDatapath(TESSDATA_FOLDER); // 设置tessdata位置
				instance.setLanguage("eng");
				captcha = instance.doOCR(bufferedImage);
				if (StringUtils.isNotBlank(captcha)) {
					captcha = captcha.replaceAll(" ", "");
					captcha = removeSeparator(captcha);
					if (captcha.length() > 4) {
						captcha = captcha.substring(0, 4);
					}
				}
			}
		} finally {
			if (captcha.length() != 4 && !isNumberZero(captcha) && num <= 20)
				getCaptcha(file, num);
		}
		return captcha;
	}
	
	private static BufferedImage tessgray(BufferedImage bufferedImage, int height, int width) {
		BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				int rgb = bufferedImage.getRGB(i, j);
				grayImage.setRGB(i, j, rgb);
			}
		}
		return grayImage;
	}
	
        
	public static void main(String[] args) throws Exception {
		
		File file = new File("d:/g.jpg");
		String string =getCaptcha(file,4);
		System.out.println("-----"+string);
	}
	

	
	private static boolean isNumberZero(String str) {
		Pattern pattern = Pattern.compile("^?[0-9]\\d*$");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	private static String removeSeparator(String str) {
		if (org.apache.commons.lang3.StringUtils.isBlank(str))
			return str;
		str = str.replaceAll("\\r\\n", "");
		str = str.replaceAll("\\r", "");
		str = str.replaceAll("\\n", "");
		str = str.replaceAll("\r\n", "");
		str = str.replaceAll("\r", "");
		str = str.replaceAll("\n", "");
		return str;
	}
	
	
}