import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.mail.util.MailSSLSocketFactory;
 
public class Test {
	
	
	public static void main(String[] args) {
		 try {
	            
	            //���÷�����
	            final String from = "ziji_20@qq.com";
	            
	            //�����ռ���
	            String to = "2967925810@qq.com";
	            
	            //�����ʼ����͵ķ�����������ΪQQ�ʼ�������
	            String host = "smtp.qq.com";
	            
	            //��ȡϵͳ����
	            Properties properties = System.getProperties();
	            
	            //SSL����
	            MailSSLSocketFactory sf = new MailSSLSocketFactory();
	            sf.setTrustAllHosts(true);
	            properties.put("mail.smtp.ssl.enable", "true");
	            properties.put("mail.smtp.ssl.socketFactory", sf);
	            
	            //����ϵͳ����
	            properties.setProperty("mail.smtp.host", host);
	            properties.put("mail.smtp.auth", "true");
	            
	            //��ȡ�����ʼ��Ự����ȡ��������¼��Ȩ��
	            Session session = Session.getDefaultInstance(properties, new Authenticator() {
	                @Override
	                protected PasswordAuthentication getPasswordAuthentication() {
	                    return new PasswordAuthentication(from, "vliylniqwmekdfei");
	                }
	            });
	            
	            Message message = new MimeMessage(session);
	            
	            //��ֹ�ʼ�����Ȼ�����ʼ���������Outlook�����
	            message.addHeader("X-Mailer","Microsoft Outlook Express 6.00.2900.2869");
	            
	            message.setFrom(new InternetAddress(from));
	            
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	            
	            //�ʼ�����
	            message.setSubject("This is the subject line!");
	            
	            BodyPart bodyPart = new MimeBodyPart();
	            
	            bodyPart.setText("�ҷ������ļ�����");
	            
	            Multipart multipart = new MimeMultipart();
	            
	            multipart.addBodyPart(bodyPart);
	            
	            message.setContent(multipart);
	            
	            Transport.send(message);
	            System.out.println("mail transports successfully");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

}

