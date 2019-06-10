package com.cjl.util;


import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 

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
		Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");// 连接协议
        properties.put("mail.smtp.host", "smtp.qq.com");// 主机名
        properties.put("mail.smtp.port", 465);// 端口号
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");// 设置是否使用ssl安全连接 ---一般都使用
        properties.put("mail.debug", "false");// 设置是否显示debug信息 true 会在控制台显示相关信息
        // 得到回话对象
        Session session = Session.getInstance(properties);
        // 获取邮件对象
        Message message = new MimeMessage(session);
        // 设置发件人邮箱地址
        message.setFrom(new InternetAddress("ziji_20@qq.com"));
        // 设置收件人邮箱地址 
        message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(emial),new InternetAddress(emial),new InternetAddress(emial)});
        // 设置邮件标题
        message.setSubject("【紫极博客】");
        // 设置邮件内容
        message.setText(text);
        // 得到邮差对象
        Transport transport = session.getTransport();
        // 连接自己的邮箱账户
        transport.connect("ziji_20@qq.com", "ktvfvxrhumardedb");// 密码为QQ邮箱开通的stmp服务后得到的客户端授权码
        // 发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
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