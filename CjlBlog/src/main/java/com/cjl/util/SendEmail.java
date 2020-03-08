package com.cjl.util;


import java.util.Properties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;

/**
 * 邮箱
 * 之前是短信
 * @author hasee
 *
 */
public class SendEmail {
	
	/**
	 * 发送邮箱 
	 * 
	 * @param emial
	 * @param text
	 * @return
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public boolean SendEmailFicationCode(String emial,String text)throws AddressException,MessagingException {
		 try {
	            
	            //设置发件人
	            final String from = "ziji_20@qq.com";
	            
	            //设置邮件发送的服务器，这里为QQ邮件服务器
	            String host = "smtp.qq.com";
	            
	            //获取系统属性
	            Properties properties = System.getProperties();
	            
	            //SSL加密
	            MailSSLSocketFactory sf = new MailSSLSocketFactory();
	            sf.setTrustAllHosts(true);
	            properties.put("mail.smtp.ssl.enable", "true");
	            properties.put("mail.smtp.ssl.socketFactory", sf);
	            
	            //设置系统属性
	            properties.setProperty("mail.smtp.host", host);
	            properties.put("mail.smtp.auth", "true");
	            
	            //获取发送邮件会话、获取第三方登录授权码
	            Session session = Session.getDefaultInstance(properties, new Authenticator() {
	                @Override
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(from, "vliylniqwmekdfei");
	                }
	            });
	            
	            Message message = new MimeMessage(session);
	            
	            //防止邮件被当然垃圾邮件处理，披上Outlook的马甲
	            message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
	            
	            message.setFrom(new InternetAddress(from));
	            
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emial));
	            
	            //邮件标题
	            message.setSubject("【紫极博客】");
	            
	            BodyPart bodyPart = new MimeBodyPart();
	            
	            bodyPart.setText(text);
	            
	            Multipart multipart = new MimeMultipart();
	            
	            multipart.addBodyPart(bodyPart);
	            
	            message.setContent(multipart);
	            
	            Transport.send(message);
	            System.out.println("mail transports successfully");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
        
		return true;
	}	
	/**
	 * 生成随机验证码
	 * 
	 * @return
	 */
	public  String VerificationCode() {
		String vcode = "";
		for (int i = 0; i < 6; i++) {
			vcode = vcode + (int) (Math.random() * 9);
		}
		return vcode;
	}
}