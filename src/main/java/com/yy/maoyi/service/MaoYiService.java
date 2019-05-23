package com.yy.maoyi.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.cookie.SetCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.yy.maoyi.entity.CusLicenseListVo;
import com.yy.maoyi.entity.DecMergeListVo;
import com.yy.maoyi.entity.PreDecHeadVo;
import com.yy.maoyi.entity.ReturnData;
import com.yy.maoyi.tools.CaptchaUtil;
import com.yy.maoyi.tools.CryptUtils;
import com.yy.maoyi.tools.HttpTransfer;
import com.yy.maoyi.tools.JsonUtils;
import com.yy.maoyi.tools.https.HttpClientUtil;

public class MaoYiService {

	public static final String AI = "I";
	public static final String AE = "E";
	
	
	public static Map<String, String> headerMap = new HashMap<String, String>();
	public Map<String, String> getHeader() {
		
		headerMap.put("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
		headerMap.put("Accept-Encoding", "gzip, deflate, br");
		headerMap.put("Accept-Language", "zh-CN,zh;q=0.9");
		headerMap.put("Cookie","");
		headerMap.put("Host", "app.singlewindow.cn");
		headerMap.put("Origin", "https://app.singlewindow.cn");
		headerMap.put("Content-Type", "application/x-www-form-urlencoded");
		headerMap.put("Referer",
				"https://app.singlewindow.cn/cas/login?service=https%3A%2F%2Fswapp.singlewindow.cn%2Fdeskserver%2Fj_spring_cas_security_check");
		headerMap.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");

		return headerMap;
	}
	
	
	public String run(String username,String psw,String number) {
		
		
		
		
		return "-1";
	}
	
	
//	private Map<String,String> headerMap = new HashMap<>();
//	public Map<String, String> getHeaderMap(){
//		headerMap.put("Cookie", "Cookie:TGC=eyJhbGciOiJIUzUxMiJ9.WlhsS2FHSkhZMmxQYVVwcllWaEphVXhEU214aWJVMXBUMmxLUWsxVVNUUlJNRXBFVEZWb1ZFMXFWVEpKYmpBdUxtc3haV0V0YkdoSlpEUjNObU5rVkdSTVZscEJVWGN1ZDFBM1dtUldhVkZsTmpZMU1sRjZkSGRZVVdsdU4ydGtXRE5FVXpFMldqUlZZVEV3V21WTE5pMUhiRzVrVGtZeVpuWnpVMmh4TFdwQ1ExaDNkR001ZFU5a2VtMUNRak5NUjFFMVVIUnJPVk5vZEVFeFNFVkNWMDEwU0VKcFNrcHNiR2RyYjFkbVlWUk5ibmgyTVVwclQyNU1iV1l0ZW1sM1pEbFhUbUZCY1haeU4wVnZSVE4xTkVsdFNWRkhVM2szTmpBMldrSmplalE1YmpKSlFXVkVhV053T1dkd1pqbElkR3hyVTJ0T1ZFdG5lRmxaZG5Bd0xVMW9YMmRaU1U5a04xOXRPRkU1UWpreFgxbEtlVVZMVlZwS2JrMUpSVzloU2xkRE5FTkxjWEEzYm5KNmNrbFhSVVpTV2tSNWRreFBWems1TTJSTVlXTlhZVU5PV0hWdFdYY3hWVVJ1TURKMlRXcFhjRkJrY2w5WWVVWnNOSGN1TFRGa2FFMUxSelIxVEZOVFVsZDZjSGRZT1RKbGR3PT0.kddvpsGwyfRDDxhpHWG3RYwh02Qz8WXerM5ljqk5Gm8m4fRuLJWBY26UC6OtImgf8BJ_mvnFbjp37z3a6OnlwA;");
//		headerMap.put("Content-Type", "text/html");
//		return headerMap;
//		
//	}
	
	public String getData(String pas) {
		
		try {
			this.paseCookie(null);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String urlString = "https://swapp.singlewindow.cn/decserver/sw/dec/merge/queryDecData";
		Map<String, String> dataMap = new HashMap<String, String>();
		//cusCiqNo: "I20190000224038701" cusIEFlag: "I" operationType: "cusEdit"
		dataMap.put("cusCiqNo", pas);
		String typeStr = "";
		if(pas.startsWith(AI)) {
			dataMap.put("cusIEFlag", AI);
			typeStr = AI;
		}else if(pas.startsWith(AE)) {
			dataMap.put("cusIEFlag", AE);
			typeStr = AE;
		}
		dataMap.put("operationType", "cusEdit");
		//{"cusCiqNo":"I20190000222681833","cusIEFlag":"I","operationType":"cusEdit"}
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("{");
		sBuffer.append("\"cusCiqNo\":").append("\"").append(pas).append("\",");
		sBuffer.append("\"cusIEFlag\":").append("\"").append(typeStr).append("\",");
		sBuffer.append("\"operationType\":").append("\"").append("cusEdit").append("\"");
		sBuffer.append("}");
		System.out.println(sBuffer.toString());
//		String akString = HttpTransfer.doPost(urlString, headerMap, dataMap);
//		System.out.println(headerMap.get("cookie"));
		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("Cookie", headerMap.get("Cookie"));
		headMap.put("Content-Type","text/html");
		HttpResponse response = HttpClientUtil.getResponseByPost1(urlString, headMap, sBuffer.toString(), "UTF-8");
		String dataString = HttpClientUtil.doData(response, "UTF-8");
//		dataString = dataString.replaceAll("\\\\\"", "\\\"");
		System.out.println(dataString);
		ReturnData rData = JsonUtils.jsonToObject(dataString, ReturnData.class);
		
//		parseModel(rData);
		return dataString;
	}
	
	
	public String parseModel(ReturnData re) {
		
		PreDecHeadVo phv = re.getData().getPreDecHeadVo();
		StringBuffer sBuffer = new StringBuffer();
		sBuffer.append("<DecHead>\n");
		sBuffer.append("<IEFlag>").append(phv.getCusIEFlag()).append("</IEFlag>");
		sBuffer.append("<CustomMaster>").append(phv.getCustomMasterName()).append("</CustomMaster>");
		sBuffer.append("<SeqNo>").append(phv.getSpDecSeqNo()).append("</SeqNo>");
		sBuffer.append("<PreEntryId>").append(phv.getPreEntryId()).append("</PreEntryId>");
		sBuffer.append("<EntryId>").append(phv.getEntryId()).append("</EntryId>");
		sBuffer.append("<IEPort>").append(phv.getiEPort()).append("</IEPort>");
		sBuffer.append("<ManualNo>").append(phv.getManualNo()).append("</ManualNo>");
//		sBuffer.append("<ContrNo>").append(phv).append("</ContrNo>");
		sBuffer.append("<IEDate>").append(phv.getiEDate()).append("</IEDate>");
//		sBuffer.append("<TradeCoScc>").append(phv).append("</TradeCoScc>");
		sBuffer.append("<TradeCode>").append(phv.getRcvgdTradeCode()).append("</TradeCode>");
//		sBuffer.append("<TradeCiqCode>").append(phv.).append("</TradeCiqCode>");
//		sBuffer.append("<TradeName>").append(phv.).append("</TradeName>");
//		sBuffer.append("<TradeCiqCode>").append(phv.).append("</TradeCiqCode>");
//		sBuffer.append("<OverseasConsignorCode>").append(phv.).append("</OverseasConsignorCode>");
//		sBuffer.append("<OverseasConsignorEname>").append(phv.).append("</OverseasConsignorEname>");
//		sBuffer.append("<OverseasConsignorAddr>").append(phv.).append("</OverseasConsignorAddr>");
//		sBuffer.append("<OverseasConsigneeCode>").append(phv.).append("</OverseasConsigneeCode>");
//		sBuffer.append("<OverseasConsigneeEname>").append(phv).append("</OverseasConsigneeEname>");
//		sBuffer.append("<DomesticConsigneeEname>").append(phv).append("</DomesticConsigneeEname>");
//		sBuffer.append("<OwnerCodeScc>").append(phv.).append("</OwnerCodeScc>");
		sBuffer.append("<OwnerCode>").append(phv.getOwnerCode()).append("</OwnerCode>");
//		sBuffer.append("<OwnerCiqCode>").append(phv.).append("</OwnerCiqCode>");
		sBuffer.append("<OwnerName>").append(phv.getOwnerName()).append("</OwnerName>");
//		sBuffer.append("<AgentCodeScc>").append(phv.).append("</AgentCodeScc>");
		sBuffer.append("<AgentCode>").append(phv.getAgentCode()).append("</AgentCode>");
//		sBuffer.append("<DeclCiqCode>").append(phv.).append("</DeclCiqCode>");
		sBuffer.append("<AgentName>").append(phv.getAgentName()).append("</AgentName>");
		sBuffer.append("<TrafMode>").append(phv.getCiqTrafMode()).append("</TrafMode>");
		sBuffer.append("<TrafName>").append(phv.getCiqTrafModeName()).append("</TrafName>");
//		sBuffer.append("<BillNo>").append(phv.).append("</BillNo>");
		sBuffer.append("<TradeMode>").append(phv.getTradeModeCode()).append("</TradeMode>");
		sBuffer.append("<CutMode>").append(phv.getCutMode()).append("</CutMode>");
//		sBuffer.append("<LicenseNo>").append(phv.).append("</LicenseNo>");
		sBuffer.append("<TradeCountry>").append(phv.getCiqTradeCountryCode()).append("</TradeCountry>");
		sBuffer.append("<DistinatePort>").append(phv.getDistinatePort()).append("</DistinatePort>");
//		sBuffer.append("<FeeMark>").append(phv.).append("</FeeMark>");
//		sBuffer.append("<FeeRate>").append(phv.).append("</FeeRate>");
//		sBuffer.append("<FeeCurr>").append(phv.).append("</FeeCurr>");
//		sBuffer.append("<InsurMark>").append(phv.).append("</InsurMark>");
//		sBuffer.append("<InsurRate>").append(phv.).append("</InsurRate>");
//		sBuffer.append("<InsurCurr>").append(phv.).append("</InsurCurr>");
//		sBuffer.append("<OtherMark>").append(phv.).append("</OtherMark>");
//		sBuffer.append("<OtherRate>").append(phv.).append("</OtherRate>");
//		sBuffer.append("<OtherCurr>").append(phv.).append("</OtherCurr>");
		sBuffer.append("<PackNo>").append(phv.getPackNo()).append("</PackNo>");
		sBuffer.append("<WrapType>").append(phv.getWrapType()).append("</WrapType>");
//		sBuffer.append("<GrossWet>").append(phv.).append("</GrossWet>");
		sBuffer.append("<NetWt>").append(phv.getNetWt()).append("</NetWt>");
//		sBuffer.append("<TradeAreaCode>").append(phv.).append("</TradeAreaCode>");
		sBuffer.append("<EntyPortCode>").append(phv.getCiqEntyPortCode()).append("</EntyPortCode>");
		sBuffer.append("<GoodsPlace>").append(phv.getGoodsPlace()).append("</GoodsPlace>");
		sBuffer.append("<DespPortCode>").append(phv.getDespPortCode()).append("</DespPortCode>");
		sBuffer.append("<EntryType>").append(phv.getEntryTypeName()).append("</EntryType>");
//		sBuffer.append("<Type>").append(phv.Type).append("</Type>");
		sBuffer.append("<EdiId>").append(phv.getEdiId()).append("</EdiId>");
//		sBuffer.append("<DeclTrnRel>").append(phv.).append("</DeclTrnRel>");
//		sBuffer.append("<BillType>").append(phv.).append("</BillType>");
		sBuffer.append("<NoteS>").append(phv.getNoteS()).append("</NoteS>");
//		sBuffer.append("<PromiseItmes>").append(phv.).append("</PromiseItmes>");
		sBuffer.append("<MarkNo>").append(phv.getMarkNo()).append("</MarkNo>");
//		sBuffer.append("<ChkSurety>").append(phv.).append("</ChkSurety>");
//		sBuffer.append("<CheckFlow>").append(phv.).append("</CheckFlow>");
//		sBuffer.append("<TaxAaminMark>").append(phv.).append("</TaxAaminMark>");
//		sBuffer.append("<Risk>").append(phv.).append("</Risk>");
//		sBuffer.append("<TgdNo>").append(phv.).append("</TgdNo>");
//		sBuffer.append("<AppNro>").append(phv.).append("</AppNro>");
//		sBuffer.append("<CopCodeScc>").append(phv.).append("</CopCodeScc>");
//		sBuffer.append("<CopCode>").append(phv.).append("</CopCode>");
//		sBuffer.append("<CopName>").append(phv.).append("</CopName>");
		sBuffer.append("<PDate>").append(phv.getUpdateTime()).append("</PDate>");
		sBuffer.append("<TypistNo>").append(phv.getTypistNo()).append("</TypistNo>");
//		sBuffer.append("<InputerName>").append(phv.).append("</InputerName>");
//		sBuffer.append("<PartenerID>").append(phv.).append("</PartenerID>");
//		sBuffer.append("<DataSource>").append(phv.).append("</DataSource>");
//		sBuffer.append("<OrgCode>").append(phv.).append("</OrgCode>");
//		sBuffer.append("<PartenerID>").append(phv.).append("</PartenerID>");
//		sBuffer.append("<VsaOrgCode>").append(phv.).append("</VsaOrgCode>");
//		sBuffer.append("<InspOrgCode>").append(phv.).append("</InspOrgCode>");
//		sBuffer.append("<DespDate>").append(phv.).append("</DespDate>");
//		sBuffer.append("<CmplDschrgDt>").append(phv.).append("</CmplDschrgDt>");
//		sBuffer.append("<BLNo>").append(phv.).append("</BLNo>");
//		sBuffer.append("<PurpOrgCode>").append(phv.).append("</PurpOrgCode>");
//		sBuffer.append("<CorrelationNo>").append(phv.).append("</CorrelationNo>");
//		sBuffer.append("<CorrelationReasonFlag>").append(phv.).append("</CorrelationReasonFlag>");
//		sBuffer.append("<OrigBoxFlag>").append(phv.).append("</OrigBoxFlag>");
		sBuffer.append("<SpecDeclFlag>").append(phv.getSpecDeclFlag()).append("</SpecDeclFlag>");
//		sBuffer.append("<DeclareName>").append(phv.).append("</DeclareName>");
//		sBuffer.append("<NoOtherPack>").append(phv.).append("</NoOtherPack>");
		sBuffer.append("<EdiRemark>").append(phv.getEdiRemark()).append("</EdiRemark>");
//		sBuffer.append("<EdiRemark2>").append(phv.).append("</EdiRemark2>");
		sBuffer.append("</DecHead>\n");
		
		String decString = phv.getDecMergeListVo();
		System.out.println("------------------"+decString);
		
		List<DecMergeListVo> list = JsonUtils.jsonToArray(decString, DecMergeListVo.class);
		
		
		sBuffer.append("<DecLists>");
		sBuffer.append("<DecList>");
		sBuffer.append("</DecList>");
		
		sBuffer.append("</DecLists>");
		
		return "";
	}
	
	
	
	private String paseCookie(String cookie) throws ClientProtocolException, IOException {
		
		//1、首先执行返回登录得页面 解析后拿到值
		//2、带着登录页面得参数进行登录；
		//3、通过302得location值执行httpget方法。
		return null;
		
	}
	
	public Map<String, String> getLogin() throws Exception{
		
		String urString = "https://app.singlewindow.cn/cas/login?service=https%3A%2F%2Fswapp.singlewindow.cn%2Fdeskserver%2Fj_spring_cas_security_check";
		HttpResponse response = HttpClientUtil.getResponseByGet(urString, headerMap,null, "UTF-8");
		String dataString2 = HttpClientUtil.doData(response, "UTF-8");
		
		int codeString = response.getStatusLine().getStatusCode();
		
		Header[] headers = response.getHeaders("Set-Cookie");
		
		String headerString = "";
		for(Header he:headers) {
			headerString+=he.getValue();
		}
		
		if(headers!=null)
			headerMap.put("Cookie", dualCookie(headerString));
		Document doc = Jsoup.parse(dataString2);
		
		Elements eleIts = doc.select("input[id$=lt]");
		Elements eleExes = doc.select("input[id$=execution]");

		String itValue = eleIts.get(0).attr("value");
		String exeValue = eleExes.get(0).attr("value");
		String vert = getVert();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("It", itValue);
		map.put("execution", exeValue);
		map.put("vert", vert);
		System.out.println("------------------------------------------"+vert);
		return map;
	}
	
	
	public String dualCookie(String cookie) {
		
		cookie = cookie.replaceAll("Path=/cas/;", "");
		cookie = cookie.replaceAll("HttpOnly", "");
		cookie = cookie.replaceAll("Path=/", "");
		cookie = cookie.replaceAll("Set-Cookie:", "");
		return cookie;
//		return cookie.split(";")[0];
	}
	
	
	public static void main(String[] args) throws Exception {
		MaoYiService maoYiService = new MaoYiService();
//		maoYiService.paseCookie("route1sw_portal=f6672fad7cd5e1f20e91179b25df7656; clientlanguage=zh_CN; JSESSIONID=9D54B18ED22CD08217D4990A8EA867AB");
//		maoYiService.getData("I20190000224038701");
		
		String username = "zdbg1231";
		String psw = "!zdbg1231";
		String pswString = CryptUtils.GetMD5Code(psw);
		maoYiService.getHeader();
		
		Map<String, String> loginMap = maoYiService.getLogin();
		String itString = loginMap.get("It");
		String execution = loginMap.get("execution");
		String vertString = loginMap.get("vert");
		
		String urlString = maoYiService.getLoginRequest(username,pswString,vertString,itString,execution,0);
		headerMap.remove("Content-Type");
		headerMap.remove("Origin");
		headerMap.put("P3P", "CP=\"IDC DSP COR ADM DEVi TAIi PSA PSD IVAi IVDi CONi HIS OUR IND CNT\"");
		headerMap.put("Host","swapp.singlewindow.cn");
		headerMap.put("Upgrade-Insecure-Requests", "1");
		HttpResponse response = HttpClientUtil.getResponseByPost(urlString, headerMap, null, "UTF-8");
	
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
			headerMap.put("Cookie", headerString);
		}
//		maoYiService.getData("I20190000224038701");
		maoYiService.getData("I20190000229797119");
		
		//开始执行查询数据
		
		
	}
	
	

	
	public String getLoginRequest(String username,String psw,String vert,String it,String execution,int runTime) throws Exception {
		
		String urlString = "https://app.singlewindow.cn/cas/login?service=https://swapp.singlewindow.cn/deskserver/j_spring_cas_security_check";
		Map<String, String> hMap = new HashMap<String, String>();
		hMap.put("swy", username);
		hMap.put("swm2", "");
		hMap.put("swm", psw);
		hMap.put("verifyCode", vert);
		hMap.put("lt", it);
		hMap.put("execution",execution);
		hMap.put("swLoginFlag", "swUp");
		hMap.put("lpid", "P1");
		hMap.put("_eventId", "submit");
		hMap.put("name", "");
		
		System.out.println(vert+"==="+it+"----开始执行验证的url");
		HttpResponse response = HttpClientUtil.getResponseByPost(urlString, headerMap, hMap, "UTF-8");
		
		if(response.getStatusLine().getStatusCode()==302) {
			System.out.println("正确执行");
			Header header = response.getFirstHeader("Location");
			System.out.println(header.getValue());
			
			Header[] headers = response.getHeaders("Set-Cookie");
			
			String headerString = "";
//			for(Header he:headers) {
//				headerString+=he.getValue();
//			}
			headerString+=headers[headers.length-1];
			if(headers!=null)
				headerMap.put("Cookie", dualCookie(headerString));
			
			return header.getValue();
		}else if(response.getStatusLine().getStatusCode()==200){
			System.out.println("执行失败");
			if(runTime<5) {
				System.out.println("开始第"+(runTime++)+"次执行重试");
				String vertString = getVert();
				return this.getLoginRequest(username, psw, vertString, it, execution,runTime++);
			}
			return "-1";
		}
		
		return "-1";
	}
	
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
	
	
	private String getVert() throws Exception{
		HttpResponse response = HttpClientUtil.getResponseByGet("https://app.singlewindow.cn/cas/plat_cas_verifycode_gen?r=0.25418528038792365", headerMap, null, "UTF-8");
		
		
		InputStream inputStream = response.getEntity().getContent();
		
//		File file = new File("d:/3.jpg");
//		FileOutputStream fStream = new FileOutputStream(file);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();  
		byte[] buffer = new byte[1024];  
		int len;  
		while ((len = inputStream.read(buffer)) > -1 ) {  
			byteArrayOutputStream.write(buffer, 0, len);  
//			fStream.write(buffer, 0, len);
		}  
		byteArrayOutputStream.flush();
		CaptchaUtil.removeBackground(byteArrayOutputStream);
		File file2 = new File(CaptchaUtil.PATH+"/amd.jpg");
		String s = CaptchaUtil.getCaptcha(file2,4);
		
		System.out.println(s);
		String parentString = "[a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9][a-zA-Z0-9]";
		if(match(parentString, s)) {
//			HttpClientUtil.SetCookie(dualCookie(header.getValue()));
			return s;
		}else {
			return getVert();
		}
		
	}
	
	
}