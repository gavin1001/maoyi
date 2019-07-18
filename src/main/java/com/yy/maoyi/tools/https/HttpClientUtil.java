package com.yy.maoyi.tools.https;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 利用HttpClient进行post请求的工具类
 * 
 * @ClassName: HttpClientUtil
 * @Description: TODO
 * @author Devin <xxx>
 * @date 2017年2月7日 下午1:43:38
 * 
 */

public class HttpClientUtil {
	
	public static String doData(HttpResponse response,String charset) {
		String result = "";
		try {
			if (response != null) {
				HttpEntity resEntity = response.getEntity();
				if (resEntity != null) {
					result = EntityUtils.toString(resEntity, charset);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("resource")
	public static HttpResponse getResponseByPost(String url,Map<String,String> headerMap,Map<String,String> params, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);

			for(Iterator<String> iterator = headerMap.keySet().iterator();iterator.hasNext();) {
				String keyString = iterator.next();
				String value = headerMap.get(keyString);
				httpPost.addHeader(keyString, value);
			}
			
			if(params!=null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Iterator<String> iter = params.keySet().iterator(); iter.hasNext();) {
					String name =  iter.next();
					String value = String.valueOf(params.get(name));
					nvps.add(new BasicNameValuePair(name, value));
					
					// System.out.println(name +"-"+value);
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			}

			HttpResponse response = httpClient.execute(httpPost);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	
	@SuppressWarnings("resource")
	public static HttpResponse getResponseByPost1(String url,Map<String,String> headerMap,String params, String charset) {
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		try {
			httpClient = new SSLClient();
			httpPost = new HttpPost(url);

			for(Iterator<String> iterator = headerMap.keySet().iterator();iterator.hasNext();) {
				String keyString = iterator.next();
				String value = headerMap.get(keyString);
				httpPost.addHeader(keyString, value);
			}
			
			StringEntity entity1 = new StringEntity(params, "UTF-8");
            httpPost.setEntity(entity1);
            entity1.setContentType("text/html");

			HttpResponse response = httpClient.execute(httpPost);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
		}
		return null;

	}
	
	
	
	@SuppressWarnings("resource")
	public static HttpResponse getResponseByGet(String url,Map<String,String> headerMap,Map<String,String> params, String charset) {
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		try {
			httpClient = new SSLClient();
			httpGet = new HttpGet(url);

			for(Iterator<String> iterator = headerMap.keySet().iterator();iterator.hasNext();) {
				String keyString = iterator.next();
				String value = headerMap.get(keyString);
				httpGet.addHeader(keyString, value);
			}
			
			
			HttpResponse response = httpClient.execute(httpGet);
			return response;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	

//	public static void main(String[] args) {
//
//		Map hMap = new HashMap();
//
//		hMap.put("swy", "zdbg1231");
//		hMap.put("swm2", "");
//		hMap.put("swm", "43482b3854d6fecc2e915e8a735af47c");
//		hMap.put("verifyCode", "4u8b");
//		hMap.put("lt", "LT-341623-Kcfn5NGEFexNX5yImBwa-app.singlewindow.cn");
//		hMap.put("execution",
//				"dc277ddf-c45a-4f43-b8fa-971a0cabef12_ZXlKaGJHY2lPaUpJVXpVeE1pSjkuUkZvMGFtbFRlWGN4UTNCV01FdFVSMHBaTHpsdGFWTkVWa2x2YUdReVNYSkVLekYzTWxWMEsyaDVaMVJpYVVGS2N6WmhZM2RWTlM5SFRYZDZkV2hQWVU5aFNERnZLMG81U2xCSE1GWkxjM2hXUWxkcFQwaGtjbk4xVjJacmVIaFRabko1TldKSUx6UXpPV2RuTUV0VlIwcFdlVWxOTVd0MFMwbGhlVk13YmxwdFdEbHVOMlV6U2pWNksxaFZiSGR6VmxsWmJ6TmtVRkJYWmpGRk1YQnZWV1UzUTFGNlFuVklaM2RwTVRGa1kyNWlkR1ZUYmtNMlRqWTBhRmhqTUdzNUwzWnZSRUZWUTFjNFNqWXhkVWxMUkd0U05FRmpNQ3N6Y2t0bWVHNTZNRWQ0T1hoSGRrNXpiMGRGVXpoVlYxVXhlSE5aTVVoeVQwRkVVbmRMZG5KM2NFTTFZVTV4UzJveFVURTRXbkYzUVV0WVFsb3ZXbmRGZFV3eE5WUm9Mek4yU0hGNVR6QnlVeXR6S3pSc1FUTm1UbkZrY0hNNU1GbDBaM1Z0YUU5S2VsaHpVMDlCTTJ0dmRGVXZZbXhQWjBvclNsaEZURlZvWWpCT1MwWkJTMkZ3TDNOc1NuWjVVWFZuYjNkMFdVZEliVnBaVDBrMlVEVnhlVWswYWtGbGExTjNXWGRXZFRNeE9XbEZXR0pXYTJJM2R6SktlRkZTVEd4b1dEQTRVM2RLYkM5clIzSnhha3gwYjJWR1NDdDJXbGczVkNzMWQwOUlVV1UzVFZsS1VVcE9jV3czUnpKeWRHbHdibEZMUnpoeVQwcDVTVkJpY2tKQ1dqVTBObFphT0ZrcldGazFhRWR0Y2tGclkzRjNNMGhtYTNwRWVVUjJUbEpaUW5Gbk56VkVLMjlJV1ZaeUx6TmxiVkYwY0hvd1FtVlRURUZOZW5aeE9WUklTVmRVWmxOemEzSk5UVFZsUkU0elQybDFhbEpKTHpKclRsZHVaaTlSUmpKdk1FcHBTM1JLVXpNd1RFazFOakZOVm5OVFJWaDNkSFU1ZFdKT1JYaEZjemhqY25BM05uaGtLemt2V2pNdkszQk5URkZPVldRNVJHRnNNRUpCYUhwV1VrZDNhVWgyZURNNE4yaHVPVVF5VjBjd0wxY3hURUZVZGs5cVNHTklha1JoYlZwUVZWQXlWMEZWZEVFeVVXUmpObWxSWm5Wd1RXeHNaM05GTVVSaWJ6VnhjVzVKTDFvNVNsaGxORmt4V2xwMk55OW1aM2hRTjJOUmFWWkZOMGRrYmxOQmVVdEhiRE50Tm1VcmRtdDZaa1J6V25kNFlXVnhkbW92V1dwUVVuZExNamhLZFhkdFNFYzFTMDA0WkZFM1pIbEtWblF2TjJ0NWMzRnRXa0ZrU21jeE5XRm9kMUJyYm1wUmVVaHVSbkY0YW5wQ09UUTBkRzEwYkVOUGVDODFkMUJyY1Uxa2JsaHVjVmhuVmxwMk5qVnpVbE40VW5kdFVGZGpVekJ6UlRNdk1UWjZabTlqYVV0c05EaGFja1JMT0RKd1NFTTRja2h5YjJkbGFqZDFOR2t4VUVSaWIweFFRVkF3VEZOc1NYQkVabWx0WW04eWNsYzVhVkYzU1ZoNE9IbGFVMDB2Ym5kdGVIQmFaa0poUVZSemFEbGhRV1E1ZUM5bGIwRldUa3RYYTJseU4yaFhWMFYxYmxobk9DczNOazlsYUZWYVdFTkNaR3AxUW5aalJDOVJRMGh3TlVReVIzZFpiMmhLTmpSeGRITTNiR1JTZHpaT1JIZFdkR1YxUlUxTWJIWmxOVVZKZDJkT1NsZFZRakpxWnk5SVdWTkhiREZDZWxsYUwyRkhibXhPVTJ0Q1dVaEllV0ZJZVhoNE1ETXpRVGhvV0haSWN6Um9WbTA0WjB0a1RXdDFlVU0xWTBrNFN6QXdSWFZoTUVwNlpVZE9Na3R4Wm5Ga055OUdVVmxpVFZkSFRsRkZPVUZpVTBGRE5FWlFVbGx6ZFZKb01ESkdiMFExVWs5dVJXeE5kVFlyUVN0VFJHazVObmg0VFZNMU5XcHZhR05SZFc5dlprUk5TM295Y2xwRGRsSnRlRVZ6TjB3MGRrZ3dWRm94Y0VOMVlrbzROMmh1UzI1QlZEQTRkVzB5ZFhOQ1R6SlpZMXBETkhRMWFVMW9XR2h5TjNKclVubHhkbEprV1hCT2JqTnlWR2RNWjNwdk1sVlpTREpNVnpka1NXVkdjbGxqT0dWQmRUSmFUMU5SYVVWbldIaG1jRUY1YjBaTmNtVTFPV1ZhU1haMU9XazJXamxoS3pKNU4zVnhiQ3RDYkVNdlFXSk5hbWhDVEhJNVR6QklSMmRDUkVWdlJuSnJaak0zVjI5YWFEUlBNbE01TmtoeGVtWk5iVVJ3VkhSSU4wNVZWa1p2YUdWT2RYb3lkVm96TDFSVFlXVjZMME4zV2tkcVdXaEVZemN4WTBNekt6SlBOVmt4TDBWM1dsUTRMMU41VkhGU2JVaFBXbk5GWVdWV2FVSjNZV1ZHYkc1b1lsbGtXRTVQUlVseVFWaEROM05tZEhsd05rTnRXazltWnpsUFRtRm5ia2x4V0UweFFtRkRaWGRYVWxaeFEzWTJVVWRJYkRoT09VMUphM2syUVU5RFVucFdTV3AxTTJ0RVRVaFpWMloyWTJka09VWk9WbUpUTW5ONFdWbEhjR281TkU5S0szRkpjMWxvV1RaVk5GVnVlVFpDY0dOQlVXWmlWWFY0U1ZwRWN6UktVMUJIYmpKUE4xaHhWbEJEYUc5S1NsaERiMU5qV1hrMUswRTBiMDVoYWxCM1lucGxaVzkzVkdsaU5tRmhRVmRtVkhsalFVSk1TRU5SWjNaSVJHOUZkVGxhVEVOYU5YZFFWM0ZMWnpOcmRVWlNjSEpFZGpWcVZURlRUM05UYlVOcWIzVjVaSE5oZUZreU5ubFdhbE5RY2taWGNIbE9XVk5ZVDA1SGVFcExTMVpOV1VRM04xcHljRE41Y1d3d2FuTm1VelZUYVV4S00wRXdXa1Z0VUVZM2IyOW1VM054U3paV0wxUkplbWRzTkhORk5GTlJVMlZpSzNkMFlreHNXSFZVWkVoMlQwSjZjbEpVUmxGVFkxSTNVbTR5WjFnNVNtSjBOVU0yV0VRelVEQTBNazlzYWpoUk0wOUJlVFZoZUVocVUxSXJiRFZUSzFNeWFXWlBValJvWWtjNWREaDViRlppTUdKM2RYazRVRmxsYkdsNmNreFdVRWRZU1hwaGNVeHJhVWxvTHpoVU1tRnNkRkpoVVV0aVNFZFFlamwyVTJZM1NWZFBjRk5wWlVVNE1GbGlkMm94UzBjM2ExcDVRa3QwZFZFd2VsTlZiamxyT0U5d1ExWkZTRUZDVjJjd2VXRkVRblZZWlhSbloxZ3dOWFJoY0ZaaWJXd3lVMVpaV1d0Q1dYTkJkREo2Y2xvMVduZzBhVVpCUVc5eFJrSnJRa2hxT0Voa00wWnNUR1pIYVdGU1RWY3lkMmRSYlVWU1FYUktkMFowWVhOWlJXUm5kRm8xYlRnM09WRTNSVWtyU1Rac1FsSkZiR2RIYmtkVk1tZ3dVR0Z5WkVGWlIxSkxjM2g2TlV0R2JIQkNXakpMUTI4MmMxQkVlRUpuZG5aM1RuWnlOSFJFVEVoeWVFaEtiVFpSYVc5ME1XTnRlRGhUVDJ0T2JHNDVjR2RoVm1wNlFtaDVXVk4zVTJGdFVWbDRaamN6VVdOc1pFNUhVblZMZFZSalp6QjJjRWxWYVVkRksyVkxPRWd2YlhGYWVqTnRjV01yYlVsdmNtdHBhMWRpWmpsYWRuUXlaemhMYzNOaVlWbFBUVWx6T0VGRWVEZGhWVFpoZVROd2R6WkRaWGg0ZGxKekwzcFphRTluZEhWVGJHcGFaR0UwUlVZME0xRkhaRVZRVlV4bWRXVlBibVoyUlVoNkwzcHpjbWx4YkZWT1FsTmxWRTB4VTJSaVJXUlphVU5XYVRKUmVXTlhZbmxrUkVwWFRWQTVjVWR0T0VkcWVIVTJkR1JwVEdwMEx6RkdOVTVQTTFoNldrc3dVRVV2ZW1ZNFV6RlVZMnhRYzNKTmMwSk9PVXBrZW5kNU5qSjZWazFZTlcwNVVVNTJhRWh1UVRob1drOHJlUzh6TDJWdlZYTmpTbTFUWnpCWlMzVXhNQzgxYkM4MFVFZDFSMEpsUmpKalNXMW1PVkZsVjJwUlJGUTFkWGh6TVdzd2VtVkliMHAzVjJodUsybzBXSFJrY0dkRVVEQkhWWEp6TVdsMlJuaDVlWFJUTDFkaVNXaDFUeXQyS3pWdllVMDNNV3Q1YkM5c0x5ODFhUzlhS3pJMVJVNXZUMVJPWTJVM05XRlRSekY1YjNGR2FGZDZNMGhpWml0U1JVZEZRV1JIVVhWSFFsSkhSVlpTY0d0aVZqTlpUR3QxZW5odVZqWkVlV05oV2sxSlEwVkdiREpCVXpWVE0weG5iVnB3YmpaT09FWnBSVVJ5TWs1TWRIaGlWVUZIZUVrclJubFBZMlpvTld0a01tSTJZWHBGV1ZCNlUzQnhZV2M0VDFRMWJVOXJWbGdyWVcxV1VEQXpNbnBPVldaNk1VSktaV3RDUjJkQlJHWnVXalI2Tm5rNU9VdzFjM05ZVkVoelVWZERaRTlqU2pkME0wVkxaVVJxUzNFMlUzcHZNMWhxVGxndk1qRndVSFJ0Y1ZObVVHVmxjMG92TjNNeFZYSlFRVzV2Wm5vcldWSnZkMHhpVmxkT1NrNHpNMWRyYVRsR1YwcHRRbXBMYURaSGVHOVlVMWhZUkhGVE1rMHpabTQwY0V0cE5rZG9iVXA2THpObFowOHpZMEVyWW5oamNtOVFlV1JVV1ZCUGJWaEpUR1JaZDJoNVRWaEpkVlpUZVRsMkt6VjFhRkoyUjAxbFkzUjBaSEI0Y25RME0xUlFTREZLYUVGalZIcHVPRVJHY0hoV1pIWnVMMU5sVW5Kek1GUnJiU3R1WVRGMVltMW1OVlE1WlRWYVUyNXJNWHBUWkhGdWVrUndhMnRyY1ZsTlVEVllTM1UxTDFsdGJqbEtNR1JPU1hwTVpFeENSMUYwVmpVell6UTNRMVZuUlZaNWNXYzBTazFLU0hsRVRWWnJZekpHVEN0dlJYTm9SMVp1TVZKaVRtdHliVzgyUkdSamJtMVlRVk5IVFVsd0szbHZZV1ZMTHpSWlNqZ3JaRTl0U205Qk1IVm5aMDloYmxZelNUSXpkRlZ2UmpSUlJHbFBaRXhFZEZCMFlqSnNORGxFV0dneVRsZE1WWE5aTDJGS1lXRlpjalJGYW05S1JGRlViSE5OVm01YWJIRlJRVzR6TXpWcFdVcHFURVpqTlcxUE1uVm1iRTlNUkdSMldIQnZiV2Q1UlZsUVRXZExjelZaVVRWc1JWQlhUR1V6UldzemJVOWhVRmgxY0dWRlFsRTNSRk5CTkhnMFNrSjNiVEJvU2psa2NIUldRbXRTUW5KdlNUZHJaMHczZEM4MVNGaFJVVUpDSzNFd09GWTNOWE42YzI0NVR6RlpOVXgwUzBkSFkzZzNjMHh3WVZKM2NUQjRNaTlrTVhFNWJHeFhhbk0wZVVOQ0syeG9ZWFU0ZDJSbVZ6UlFhMU41UW5wNEswdFhSMEp6Vm5aVmJWaFpXR0ZIZFdKa1NqSnhRWEpVYUhsWk1URnlURk0wUTJwbFYyWlRNVUo2T0hGVE9IQnJTMU52VFc1R01EZFplbEpCTTBOS09XUkhUMUJSTUZCS2EwdFNNM1JzVW5veWJFNTVlVWRVWW5vd1JEUXJjbmRaTUZJMFkyMWlRMjVSUkdkRU5HUXdVVE5sT0hkQ2JUTkVWR2xIV2pFM1EwZDNVbEI0VGtsRE9IZERZMFZPWWpkT1VrRktha1JsWjIxVFZHcFlZMjAzZUdWUU0zUXZNVWR0UzFsSVNIQm9kRTVOWmpod2JsWk9SV2QyYVd0WlluSnhaVzFJWlZkdFVuZFZja2suaVVCYkhzWkFQMXByejdmbHBVY3dIZGREdlB4UlRfQkhhSWt5RmppeUR5LWRLQThYNTY5X0hQMmdYT3JrbkN2bGRlZk56YmlhcnJ5N1VVWll0VmFaQmc=");
//		hMap.put("swLoginFlag", "swUp");
//		hMap.put("lpid", "P1");
//		hMap.put("_eventId", "submit");
//		hMap.put("name", "");
//
////    	String urlString  = "https://app.singlewindow.cn/cas/login?service=https%3A%2F%2Fswapp.singlewindow.cn%2Fdeskserver%2Fj_spring_cas_security_check";
//		String urlString = "https://app.singlewindow.cn/cas/login?service=https://swapp.singlewindow.cn/deskserver/j_spring_cas_security_check";
//
////		String argString = doData(HttpClientUtil.getResponse(urlString, hMap, "UTF-8"),"UTF-8");
////		System.out.println(argString);
//	}

}