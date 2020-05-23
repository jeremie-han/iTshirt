package com.escape.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.escape.batch.SendZCTask;

/**
 * Project Name   : KTH API Store
 * Developer      : JongSeong Park
 * Create Date    : 2012. 10. 29.
 * 2012. 10. 29./Famz   : created
 *
 * @author (주)famz communication (www.famz.co.kr)
 * @number 02-784-0972
 * @version 1.0
 * KTH메일 서버를 통하여 메일을 전송
 */

public class MailAdaptor {
	
	static Log log = LogFactory.getLog(MailAdaptor.class);

	public static int sendMail( String from, String to, String subject, String content) 
			throws IOException	{

		HttpClient client = new HttpClient();
		client.getParams().setParameter("http.useragent", "Mozilla/4.0");

//		String sUrl = "http://mail.als.kthcorp.com:8082/1/email/outbound/request";
		String sUrl = "https://mailapi.dev.kthcorp.com:8443/1/email/outbound/request";
		PostMethod method = new PostMethod(sUrl);
		method.addParameter("from", from);
		method.addParameter("to", to);
		method.addParameter("Subject", subject);
		method.addParameter("Contents", content);

		method.setRequestHeader("Authorization", "Basic YXBpZ2lzOjU4NzJmOWQ2LTI1MjgtNDU0OC1iMGY0LTM1Nzc5OTRkYTQ1MQ==");
		method.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		int rCode = 0;
		try {
			rCode = client.executeMethod(method);
			if (rCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				method.getResponseBodyAsString();
			} else {
				br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
				String line;
				while ((line = br.readLine()) != null){ 
					sb.append(line);
				}
			}
			method.releaseConnection();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(rCode + "::" + e.toString());
		}
		if (rCode == 200 || rCode == 201) {
			log.info("[from : " + from + ", to : " + to + "] 메일 발송이 완료되었습니다.");
		}else{//200외의 결과값
			log.info("[from : " + from + ", to : " + to + "] 메일 발송이 실패되었습니다.");
		}
		return rCode;
	}
	
	public static  void main(String[] args){
//		try {
//			sendMail("awatcher35@naver.com","galmang2015@gmail.com","테스트","테스트 내용",null);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String content = SendZCTask.makeContent("이방원");//makeContent("이방원");
		
		try {
			MailAdaptor.sendMail("awatcher35@naver.com","galmang2015@gmail.com","한국의 신앙인들에게"+new Date(),content,null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	//메일에 파일 전송 추가 
	public static int sendMail( String from, String to, String subject, String content, MultipartFile file) 
			throws IOException	{
		
		int rCode = 0;
		String host = "smtp.naver.com";
		String port = "465";
		Properties props = System.getProperties();
        props.put("mail.smtp.host", host);  
        props.put("mail.smtp.port", port);  
        props.put("mail.smtp.auth", "true");  
        props.put("mail.smtp.ssl.enable", "true");  
        props.put("mail.smtp.ssl.trust", host);  
        
        final String username = "awatcher35"; //네이버 아이디를 입력해주세요. @nave.com은 입력하지 마시구요.  

        final String password = "yustian2006"; //네이버 이메일 비밀번호를 입력해주세요.  
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {  
        	String un=username;  
        	String pw=password;  
        	protected javax.mail.PasswordAuthentication getPasswordAuthentication() {  
        	return new javax.mail.PasswordAuthentication(un, pw);  
        	}  
        });  
        
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.setSubject(subject);
			Multipart multipart = new MimeMultipart();
			MimeBodyPart bodyPart = new MimeBodyPart();
			bodyPart.setContent(content, "text/html;charset=UTF-8");
			multipart.addBodyPart(bodyPart);
			message.setContent(multipart);
	        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	        
	        if (file != null && file.getSize() > 0) {
	        	MimeBodyPart bodyPartFile = new MimeBodyPart();
		        FileDataSource fds = new FileDataSource(convertFile(file));
		        bodyPartFile.setDataHandler(new DataHandler(fds));
		        bodyPartFile.setFileName(MimeUtility.encodeText(fds.getName(), "KSC5601", "B"));
		        multipart.addBodyPart(bodyPartFile);
	        }
	        Transport.send(message);
	        rCode = 200;
		} catch (AddressException e) {
			e.printStackTrace();
			rCode = 300;
		} catch (MessagingException e) {
			e.printStackTrace();
			rCode = 300;
		}
		
		if (rCode == 200 || rCode == 201) {
			log.debug("[from : " + from + ", to : " + to + "] 메일 발송이 완료되었습니다.");
			System.out.println("[from : " + from + ", to : " + to + "] 메일 발송이 완료되었습니다.");
		}else{//200외의 결과값
			log.debug("[from : " + from + ", to : " + to + "] 메일 발송이 실패되었습니다.");
			System.out.println("[from : " + from + ", to : " + to + "] 메일 발송이 실패되었습니다.");
		}
		return rCode;
	}
	
	public static File convertFile(MultipartFile file) {
		File convFile = new File(file.getOriginalFilename());
        try {
			file.transferTo(convFile);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return convFile;
	}
	
	
	
}
