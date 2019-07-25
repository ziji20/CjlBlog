package com.cjl.util;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import net.sf.json.JSONObject;


/**
 * ����id��ȡ��������
 * @author hasee
 *
 */
public class GetIdtoAddress {

	public static String getAddresses(String content, String encodingString)
			throws UnsupportedEncodingException {
		// ��������Ա�API
		String urlStr = "http://ip.taobao.com/service/getIpInfo.php";
		// ��http://whois.pconline.com.cnȡ��IP���ڵ�ʡ������Ϣ
		String returnStr = getResult(urlStr, content, encodingString);
		if (returnStr != null) {
			// �����ص�ʡ������Ϣ
			returnStr = decodeUnicode(returnStr);
			String[] temp = returnStr.split(",");
			if(temp.length<3){
				return "0";//��ЧIP������������
			}
			return returnStr;
		}
		return null;
	}
	
	private static String getResult(String urlStr, String content, String encoding) {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();// �½�����ʵ��
			connection.setConnectTimeout(2000);// �������ӳ�ʱʱ�䣬��λ����
			connection.setReadTimeout(2000);// ���ö�ȡ���ݳ�ʱʱ�䣬��λ����
			connection.setDoOutput(true);// �Ƿ������� true|false
			connection.setDoInput(true);// �Ƿ��������true|false
			connection.setRequestMethod("POST");// �ύ����POST|GET
			connection.setUseCaches(false);// �Ƿ񻺴�true|false
			connection.connect();// �����Ӷ˿�
			DataOutputStream out = new DataOutputStream(connection
					.getOutputStream());// ����������Զ˷�����д����
			out.writeBytes(content);// д����,Ҳ�����ύ��ı� name=xxx&pwd=xxx
			out.flush();// ˢ��
			out.close();// �ر������
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encoding));// ���Զ�д�����ݶԶ˷�������������
			// ,��BufferedReader������ȡ
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();// �ر�����
			}
		}
		return null;
	}

	public static String decodeUnicode(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed      encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't') {
						aChar = '\t';
					} else if (aChar == 'r') {
						aChar = '\r';
					} else if (aChar == 'n') {
						aChar = '\n';
					} else if (aChar == 'f') {
						aChar = '\f';
					}
					outBuffer.append(aChar);
				}
			} else {
				outBuffer.append(aChar);
			}
		}
		return outBuffer.toString();
	}
	
	public String getAddressByIp(String ip) throws Exception {
		// json_result���ڽ��շ��ص�json����
		String json_result = null;
		try {
			json_result = GetIdtoAddress.getAddresses("ip=" + ip, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 String address = null;
		JSONObject json = JSONObject.fromObject(json_result);
		if ((json != null) || (json.get("data") != null)) {
	      String country = JSONObject.fromObject(json.get("data")).get("country").toString();
	      String region = JSONObject.fromObject(json.get("data")).get("region").toString();
	      String city = JSONObject.fromObject(json.get("data")).get("city").toString();
	      String county = JSONObject.fromObject(json.get("data")).get("county").toString();
	      String isp = JSONObject.fromObject(json.get("data")).get("isp").toString();
	      String area = JSONObject.fromObject(json.get("data")).get("area").toString();
	      address = country;
	      address = address + region + "ʡ";
	      address = address + city + "��";
	   }else{
	      address = "δ֪��ip����";
	   }
		return address;
	}
}
