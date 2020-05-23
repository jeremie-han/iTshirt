package com.escape.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.escape.common.MailAdaptor;
import com.escape.model.ChurchMember;
import com.escape.service.ChurchMemberService;

@Component
public class SendZCTask {

	Log log = LogFactory.getLog(this.getClass());

	@Autowired
	ChurchMemberService churchMemberService;
	
	final static String SUBJECT = "�ѱ��� �ž��ε鿡��"; 
	
	final static String FROM_EMAIL_ADDRESS = "awatcher35@naver.com"; 
	
	static int EMAIL_CNT =0;

	//@Scheduled(cron = "0 0/2 * * * *")
	public void scheduleRun() {
		try {
			ChurchMember chMember = churchMemberService.getChurchMemberForZCV();
			if (chMember != null) {
				String content = makeContent(chMember.getName());
				int rCode =MailAdaptor.sendMail(FROM_EMAIL_ADDRESS,chMember.getEmail(), SUBJECT,content, null);
				if (rCode == 200 || rCode == 201) {
					chMember.setSendZcYn("Y");
					EMAIL_CNT++;
				}else{
					chMember.setSendZcYn("F");
				}
				churchMemberService.update(chMember);
				log.info("EMAIL_CNT="+EMAIL_CNT);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public static String makeContent(String user_id) {
		String content = "";
		String url = "http://www.eduzion.org/ref/24086";
		String img_url1= "https://pbs.twimg.com/media/D7yDyzYUcAEz_JJ.jpg";
		String img_url2= "https://pbs.twimg.com/media/D7yIki_U8AACZ4j.jpg";
		String img_url3= "https://pbs.twimg.com/media/D7yJR3aU0AIGQlq.jpg";

		content += "	<html>                                                                                                                                                                                                                                                                                                                                                                                                     ";
		content += "	<head>                                                                                                                                                                                                                                                                                                                                                                                                     ";
		content += "	<meta http-equiv='Content-Type' content='text/html; charset=euc-kr'>                                                                                                                                                                                                                                                                                                                                       ";
		content += "	<title>�ÿ� ���� ����</title>                                                                                                                                                                                                                                                                                                                                                                        ";
		content += "	</head>                                                                                                                                                                                                                                                                                                                                                                                                    ";
		content += "	                                                                                                                                                                                                                                                                                                                                                                                                           ";
		content += "	<body style='margin:10px;padding:0;'>                                                                                                                                                                                                                                                                                                                                                                      ";
		content += "	                                                                                                                                                                                                                                                                                                                                                                                                           ";
		content += "	<!--  EMS LETTER -->                                                                                                                                                                                                                                                                                                                                                                                       ";
		content += "	<div style='width:100%;'>                                                                                                                                                                                                                                                                                                                                                                  ";
		content += "	<table cellpadding='0' cellspacing='0' border='0' width='100%'>                                                                                                                                                                                                                                                                                                                                            ";
		content += "	<tr valign='top'>                                                                                                                                                                                                                                                                                                                                                                                          ";
		content += "	<td style='line-height:0px;' align='left'>                                                                                                                                                                                                                                                                                                                                                                 ";
		content += "	<!-- header -->                                                                                                                                                                                                                                                                                                                                                                                            ";
		content += "	<table cellpadding='0' cellspacing='0' border='0' width='100%'>                                                                                                                                                                                                                                                                                                                                            ";
		content += "	<tr height='50' style='background:#434446; text-align:center'>                                                                                                                                                                                                                                                                                                                      ";
		content += "	<td style='line-height:0px;text-align:center' height='50'>"
				+  "    <img src='"+ img_url1 + "'  width='35%' border='0' alt='API STORE' /></td>                                                                                                                                                                                                                                                                  ";                                                                                                                                                                                                                                    
		content += "	</tr>                                                                                                                                                                                                                                                                                                                                                                                                           ";
		content += "	</table>                                                                                                                                                                                                                                                                                                                                                                                                   ";
		content += "	<!-- /header -->                                                                                                                                                                                                                                                                                                                                                                                           ";
		content += "	<!-- contents -->                                                                                                                                                                                                                                                                                                                                                                                          ";
		content += "	<table cellpadding='0' cellspacing='0' border='0' width='100%'>                                                                                                                                                                                                                                                                                                                                            ";
		content += "	<tr valign='top'>                                                                                                                                                                                                                                                                                                                                                                                          ";
		content += "	<td bgcolor='#474747' width='1'></td>                                                                                                                                                                                                                                                                                                                                                                      ";
		content += "	<td style='padding:34px 30px 50px 30px;background:#fff url("
				+ url
				+ "/images/ems/bg_dot.gif) repeat;'>                                                                                                                                                                                                                                                                                                          ";
		content += "	<p style='font-family:����,Dotum,sans-serif;font-size:20px;color:#000;line-height:28px;padding:0;margin:0;'>                                                                                                                                                                                                                                                                                               ";
		content += "	<strong>�ȳ��ϼ���, <span style='color:#e34156;'>"	+ user_id + "</span>��!</strong> <br>    <br>                                                                                                                                                                                                                                                                                                                   ";
		content += "	</p>                                                                                                                                                                                                                                                                                                                                                                                                       ";
		content += "	<p style='font-family:����,Dotum,sans-serif;font-size:20px;color:#000;line-height:28px;padding:22px 0 0 0;margin:0;'>";
		content += "	"
				+ getContent()
				+ "     ";
		content += "	</p> ";
		content += "	<p style='font-family:����,Dotum,sans-serif;font-size:18px;color:#000;line-height:28px;padding:22px 0 0 0;margin:0;'>";
		content += "	<br/><br/> ���� �� �𸣰� �д� ���� 10���� �׸� �� ����� �˰� ���� 1�� ��������.";
		content += "	<br/><br/> ����̺��� �强���ڱ��� ������ ������ [<a herf='"+url+"'>���ͳݽÿ±⵶������</a>]";
		content += "	<br/><br/> ���ͳݽÿ¼������� �������ϱ�";
		content += "	<br/><br/> �������ν�û��ȣ-24086 (http://bit.ly/2q8Hvuz)";
		content += "	<br/><br/> �¶��� ��û��ȣ-24086(http://www.eduzion.org/ref/24086 )";
		content += "	</p> ";
		content += "	<p style='padding:21px 0 0 0;margin:0;text-align:center'>";
		content += "	<a href='"+ url+"'><img src='"+img_url2+"' width='400px' alt='�ÿ¼�������' style='vertical-align:top;' border='0'></a>";
		content += "    <a href='"+ url+"'><img src='"+img_url3+"' width='400px' alt='�ÿ¼�������' style='vertical-align:top;margin-left: 30px;' border='0'></a>";
		content += "	</p>   ";
		content += "	</td>                                                                                                                                                                                                                                                                                                                                                                                                      ";
		content += "	<td bgcolor='#474747' width='1'></td>                                                                                                                                                                                                                                                                                                                                                                      ";
		content += "	</tr>                                                                                                                                                                                                                                                                                                                                                                                                      ";
		content += "	</table>                                                                                                                                                                                                                                                                                                                                                                                                   ";
		content += "	                                                                                                                                                                                                                                                                                                                                                                                                           ";
		content += "	<!--  //EMS LETTER -->                                                                                                                                                                                                                                                                                                                                                                                     ";
		content += "	                                                                                                                                                                                                                                                                                                                                                                                                           ";
		content += "	</body>                                                                                                                                                                                                                                                                                                                                                                                                    ";
		content += "	</html>                                                                                                                                                                                                                                                                                                                                                                                                    ";

		return content;
	}
	
	private static String getContent(){
		StringBuffer sb = new StringBuffer();
		sb.append("(��õ�� ������ �����帷���� ��ǥ��)���� �ϴ��� ���ÿ� ���� ��ȸ�� â���Ͽ�����, �𼼰��� ������ �߰�, ����, �θ�, ������ �ϴ��� ���ô�� �Ͽ���. �ž� ��÷��� ���� å�� �ް�, ��÷��� �̷���� ���� ��÷� 1�忡�� 22����� ���Ұ�, ���ÿ� ���� ���ó����� ������ �Դ�.");
		sb.append("<br/>");
		sb.append("�� ����� �ѱ��� ��� ��ȸ ���ڵ�� ���ε��� ��� �˰� ���� ���̴�. �ϳ� ������ �ͼ� ���� �����ϴ� ������ ���� �ݴ� �����̳� ���� ���� �ڴ� �� �� ����� ������. �̿ʹ� �ݴ�� �� ��ȸ���� �������� ��(��õ��������ȸ)���Է� ���� �������� �Ͽ� ������ �̴��̶�� ���� �̹ڸ� �ϻ�Ҵ�. ������ �ʸ� ���� ���� �Ͽ���.");
		sb.append("<br/>");
		sb.append("�ؼ� ���� ���ڵ��� ��÷��� ������ ������ ������ ������ ���ϸ鼭 �ڱ� ������ ������� ����� ������ �ٸ� ������ �ְ��ϰ� �������� ����� ����ģ �� 200�� ������ �� ���ŷ� �˸� �� �ִ�. �ž� ���濡 �� ������ �ϳ����� ����, �ϳ����� ���� �� �ڴ� �ϳ����� �Ƶ��̸�, �������� ������ ���̸�, ������ ���� �� �ڴ� ������ �Ƶ��̶� �Ͽ���. �ؼ� �������� ����ģ �˰� �󸶳� ũ�ٴ� ���� ������ ���ϰ� �ִ�.");
		sb.append("<br/><br/>");
		sb.append("�ϳ����� ���� ����� �ϳ� ���� ���� �ϼ̰�, �츮���� ���� ������ ��ġ�� �и��ȴٰ� �Ͽ���. ���� �ѱ� �ž��ε��� �ϴ��� ���� ������ ��, �� �� ������ �� ���ϰ� �ִ� ���̴�.");
		sb.append("<br/><br/>");
		sb.append("���ڿ� �������� �ϳ��԰� ����� �žӰ� õ���� ���� �ִٸ� ���� ������ ���� �ؾ� �ϰ�, ������ �̴��̶� ���� ���� �߸��� ���� �ִٸ� �������� �����ؾ� �� ���̴�.");
		sb.append("<br/><br/>");
		sb.append("�̴��̶�� �̹�, �����ϰ� ������ ������ �ൿ�� �ϱ⺸�� �������� �ͼ� Ȯ���ؾ� �� ���̴�. �� ���� ����췽�� ���ڵ��� �ϳ����� ��� ������ �����ϰ� �ڱ���� ������ ������, ���� �ϳ��԰� �������� ������ �̹��� �Ͱ���, ���ó� �縲 ���� �� �׶��� ���� �к����� ������ ������ �ռ��� �̹ڰ� ���ֺ��� �ϴ� ���� �ž��� �Ǿ�� �� �� ���̴�.");
		sb.append("<br/><br/>");
		sb.append("���� ������ �� �������� ��ȭ�ϱ⸦ ���Ѵ�.");
		sb.append("<br/><br/>");
		sb.append("���� ���а� ���� ���� ���濡 �԰��� ���� �ִ� ��ȭ�� �ʿ��� ���̴�. �츮 ��õ��������ȸ(��Ī ��õ��)�� ������ ��ȭ��� �������� �ʴ´�. �츮 ��õ���� �ž� ���� ��÷��� ���� ���� ����ϰ� �ִ�. Ȯ���϶�.");
		sb.append("<br/><br/>");
		sb.append("���� �̻�� 1�忡 �ϳ��Բ��� �����Ͻñ⸦ ������. �츮�� ���� ��������. ���� �˰� ��ȫ �������� ���� ���� ����� ���̿�, ��ȫ���� �������� ���а��� �Ǹ���. ���� ��� �����ϸ� ���� �Ƹ��ٿ� �һ��� ���� ���̿�, ���� �����Ͽ� ����ϸ� Į�� ��Ű�츮��.�� �ϼ̴�.");
		sb.append("<br/><br/>");
		sb.append("�� ���� ��ȭ�� ���� � �˵� ���� �ް�, ������ ���а��� ������� ���������� ���� �޴´ٴ� �����̴�. �ؼ� ��õ���� ��ȭ�� û�ϰ� �ִ�. �ϳ� ���� �� �� ����� ��ȭ�� ���忡 ������ �ʰ� �ִ�. �̴� ������ ������ �ںνɿ� ��������� �ڱ��� ������ ���� �����̴�.");
		sb.append("<br/><br/>");
		sb.append("��õ��(����� ����)�� ��÷��� �ϳ����� ���ÿ� ���� ��ȸ�鿡�� ������ �޾Ұ�, ���� ��� å�� �޾� ����� ����� �ǻ��� ���ϰ� �ִ�. �� �������� �ǿ� �����ϴ� ���� ���� ������ ���� ���ڵ�� �ο� �̰��.");
		sb.append("<br/><br/>");
		sb.append("�ž��� �����е�, ���濡�� ������ ��� �� �����ϰ� ������ ����Ѵٸ�, �ž� ���濡 �������� ����� ���ڸ� �������� ã�� ���� ���Ÿ� �޾ƾ� ������ �̷� ���̴�. ���ϳ��� �ϳ���, ������ �����ԡ� �Ѵٰ� �ؼ� �����޴� ���� �ƴϿ�, ������ ����� �ϰ� �� ���� �ؾ� ������ �ְ� �ȴ�.");
		sb.append("<br/><br/>");
		sb.append("���� �ѱ��� �⵶���ε� �� �� ����� �������� ���Ѵٰ� �ߴ�. �̰��� �������̰� �̴��� ������ ��÷� 22�� 18-19���� ���� ���ؾ� �� ���̴�. ������ �ž� ��÷��� �������� �ʾҴ°�? ��ϵ� ���濡�� ���� ����.");
		sb.append("<br/><br/>");
		sb.append("�������� �����ε鿡�� ������ �ƺ�� ���������̰� ���� ���� �ƺ��� �������Ѵ�.�� �԰� ����, �ѱ� ��ȸ���� ������ �׸��ϰ� ��ȭ�� �������� ������ �ٶ���.");
		sb.append("<br/><br/>");
		sb.append("�״���� ��ȭ�� ���忡 ������ ���ϴ� ������ �ڱ���� �������� �巯���� ���Ŵ��� ���� ������ �Ǿ� �� ������ ������ �˰� �ִ�. ���ͼ� ��ȭ�� �ϸ� ��ȫ ���� �˵� ������ ����� ���̴�. �̷��� �ɱ� �Ͽ� ���ʹ� �� ������ �Ϸ��� ���� ������ ���ϰ� �ϴ� ���̳�, �� �ž����� �̱�� ���� ���̴�.");
		sb.append("<br/><br/>");
		sb.append("��õ���� �ž� ���� ��÷��� ����Ѵ�. �� ���� �����̴�. �̴� �ϳ����� �� ����� �� �� ��÷��� 6õ �Ⱓ ���߾� �ξ��ٰ� ���ó� ��õ���� �־� �����ϰ� �ϼ̱� �����̴�.");
		sb.append("<br/><br/>");
		sb.append("�� ��ȸ���� �ž࿡ ����� ����(�� 22:16)�� ����, ��÷Ͽ� ���� ���Ÿ� �޾� ����� ���� ��õ�� 12���Ŀ� ��ϵǾ��, ����� ���� õ���� �ȴ�. �Ƹ�.");
		sb.append("<br/><br/>");
		return sb.toString();
	}
}
