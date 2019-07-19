package com.yy.maoyi.ca;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.UUID;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfSignatureAppearance.RenderingMode;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.security.BouncyCastleDigest;
import com.itextpdf.text.pdf.security.DigestAlgorithms;
import com.itextpdf.text.pdf.security.ExternalDigest;
import com.itextpdf.text.pdf.security.ExternalSignature;
import com.itextpdf.text.pdf.security.MakeSignature;
import com.itextpdf.text.pdf.security.MakeSignature.CryptoStandard;
import com.itextpdf.text.pdf.security.PrivateKeySignature;

public class SignPdf {
	/**
	 * @param password     秘钥密码
	 * @param keyStorePath 秘钥文件路径
	 * @param signPdfSrc   签名的PDF文件
	 * @param signImage    签名图片文件
	 * @param x            x坐标
	 * @param y            y坐标
	 * @return
	 */
	public static byte[] sign(String password, String keyStorePath, String signPdfSrc, String signImage, float x,
			float y) {
		File signPdfSrcFile = new File(signPdfSrc);
		PdfReader reader = null;
		ByteArrayOutputStream signPDFData = null;
		PdfStamper stp = null;
		FileInputStream fos = null;
		try {
			BouncyCastleProvider provider = new BouncyCastleProvider();
			Security.addProvider(provider);
			KeyStore ks = KeyStore.getInstance("PKCS12", new BouncyCastleProvider());
			fos = new FileInputStream(keyStorePath);
			// 私钥密码 为Pkcs生成证书是的私钥密码 123456
			ks.load(fos, password.toCharArray());
			String alias = (String) ks.aliases().nextElement();
			PrivateKey key = (PrivateKey) ks.getKey(alias, password.toCharArray());
			Certificate[] chain = ks.getCertificateChain(alias);
			reader = new PdfReader(signPdfSrc);
			signPDFData = new ByteArrayOutputStream();
			// 临时pdf文件
			File temp = new File(signPdfSrcFile.getParent(), System.currentTimeMillis() + ".pdf");
			stp = PdfStamper.createSignature(reader, signPDFData, '\0', temp, true);
			stp.setFullCompression();
			PdfSignatureAppearance sap = stp.getSignatureAppearance();
			sap.setReason("数字签名，不可改变");
			// 使用png格式透明图片
			Image image = Image.getInstance(signImage);
			sap.setImageScale(0);
			sap.setSignatureGraphic(image);
			sap.setRenderingMode(RenderingMode.GRAPHIC);
			// 是对应x轴和y轴坐标
			sap.setVisibleSignature(new Rectangle(975, 40, 110, 170), 1,
					UUID.randomUUID().toString().replaceAll("-", ""));
			stp.getWriter().setCompressionLevel(5);
			ExternalDigest digest = new BouncyCastleDigest();
			ExternalSignature signature = new PrivateKeySignature(key, DigestAlgorithms.SHA512, provider.getName());
			MakeSignature.signDetached(sap, digest, signature, chain, null, null, null, 0, CryptoStandard.CADES);
			stp.close();
			reader.close();
			return signPDFData.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (signPDFData != null) {
				try {
					signPDFData.close();
				} catch (IOException e) {
				}
			}

			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		byte[] fileData = sign("123456", "e:/keystore.p12", //
				"e:/I20190000252771651.pdf", //
				"e:/4444.png", 100, 290);
//				"e:/2222.jpg", 100, 290);
		FileOutputStream f = new FileOutputStream(new File("e:/signed.pdf"));
		f.write(fileData);
		f.close();
	}
}