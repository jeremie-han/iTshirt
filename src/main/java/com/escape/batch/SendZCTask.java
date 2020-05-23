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
	
	final static String SUBJECT = "한국의 신앙인들에게"; 
	
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
		content += "	<title>시온 선교 센터</title>                                                                                                                                                                                                                                                                                                                                                                        ";
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
		content += "	<p style='font-family:돋움,Dotum,sans-serif;font-size:20px;color:#000;line-height:28px;padding:0;margin:0;'>                                                                                                                                                                                                                                                                                               ";
		content += "	<strong>안녕하세요, <span style='color:#e34156;'>"	+ user_id + "</span>님!</strong> <br>    <br>                                                                                                                                                                                                                                                                                                                   ";
		content += "	</p>                                                                                                                                                                                                                                                                                                                                                                                                       ";
		content += "	<p style='font-family:돋움,Dotum,sans-serif;font-size:20px;color:#000;line-height:28px;padding:22px 0 0 0;margin:0;'>";
		content += "	"
				+ getContent()
				+ "     ";
		content += "	</p> ";
		content += "	<p style='font-family:돋움,Dotum,sans-serif;font-size:18px;color:#000;line-height:28px;padding:22px 0 0 0;margin:0;'>";
		content += "	<br/><br/> 이제 뜻 모르고 읽던 성경 10독은 그만 뜻 제대로 알고 성경 1독 도전하자.";
		content += "	<br/><br/> 어린아이부터 장성한자까지 진리를 만나는 [<a herf='"+url+"'>인터넷시온기독교센터</a>]";
		content += "	<br/><br/> 인터넷시온선교센터 수강해하기";
		content += "	<br/><br/> 오프라인신청번호-24086 (http://bit.ly/2q8Hvuz)";
		content += "	<br/><br/> 온라인 신청번호-24086(http://www.eduzion.org/ref/24086 )";
		content += "	</p> ";
		content += "	<p style='padding:21px 0 0 0;margin:0;text-align:center'>";
		content += "	<a href='"+ url+"'><img src='"+img_url2+"' width='400px' alt='시온선교센터' style='vertical-align:top;' border='0'></a>";
		content += "    <a href='"+ url+"'><img src='"+img_url3+"' width='400px' alt='시온선교센터' style='vertical-align:top;margin-left: 30px;' border='0'></a>";
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
		sb.append("(신천지 예수교 증거장막성전 대표님)나는 하늘의 지시에 따라 교회를 창설하였으며, 모세같이 성경대로 했고, 교명도, 인명도, 조직도 하늘의 지시대로 하였다. 신약 계시록의 열린 책을 받고, 계시록이 이루어진 것을 계시록 1장에서 22장까지 보았고, 지시에 의해 오늘날까지 증거해 왔다.");
		sb.append("<br/>");
		sb.append("이 사실은 한국의 모든 교회 목자들과 교인들이 듣고 알고 있을 것이다. 하나 나에게 와서 내가 증거하는 말씀에 대해 반대 질문이나 따져 묻는 자는 단 한 사람도 없었다. 이와는 반대로 각 교회에서 성도들이 나(신천지예수교회)에게로 많이 몰려간다 하여 무조건 이단이라고 몰아 핍박만 일삼았다. 예수님 초림 때와 같이 하였다.");
		sb.append("<br/>");
		sb.append("해서 나는 목자들이 계시록의 참뜻을 보지도 듣지도 알지도 못하면서 자기 위신을 세우려고 약속의 말씀을 다른 뜻으로 왜곡하고 거짓말을 지어내어 가르친 것 200여 가지를 참 증거로 알린 바 있다. 신약 성경에 참 진리는 하나님의 씨요, 하나님의 씨로 된 자는 하나님의 아들이며, 거짓말은 마귀의 씨이며, 마귀의 씨로 된 자는 마귀의 아들이라 하였다. 해서 거짓말을 가르친 죄가 얼마나 크다는 것을 성경은 말하고 있다.");
		sb.append("<br/><br/>");
		sb.append("하나님의 법은 세상과 하나 되지 말라 하셨고, 우리나라 법은 종교와 정치는 분리된다고 하였다. 지금 한국 신앙인들은 하늘의 법과 세상의 법, 이 두 가지를 다 범하고 있는 것이다.");
		sb.append("<br/><br/>");
		sb.append("목자와 성도들이 하나님과 성경과 신앙과 천국에 뜻이 있다면 먼저 성경의 뜻대로 해야 하고, 무조건 이단이라 하지 말고 잘못된 것이 있다면 성경으로 증거해야 할 것이다.");
		sb.append("<br/><br/>");
		sb.append("이단이라며 핍박, 저주하고 편파적 무법한 행동을 하기보다 성경으로 와서 확인해야 할 것이다. 그 옛날 예루살렘의 목자들이 하나님의 약속 성경을 무시하고 자기들의 권위만 내세워, 오신 하나님과 예수님을 무조건 핍박한 것같이, 오늘날 재림 때도 또 그때와 같이 분별없이 편파적 감정을 앞세워 핍박과 저주부터 하는 못난 신앙이 되어서는 안 될 것이다.");
		sb.append("<br/><br/>");
		sb.append("나는 누구나 다 성경으로 대화하기를 원한다.");
		sb.append("<br/><br/>");
		sb.append("종교 깡패가 되지 말고 성경에 입각한 질서 있는 대화가 필요할 것이다. 우리 신천지예수교회(약칭 신천지)는 성경적 대화라면 마다하지 않는다. 우리 신천지는 신약 성경 계시록을 가감 없이 통달하고 있다. 확인하라.");
		sb.append("<br/><br/>");
		sb.append("구약 이사야 1장에 하나님께서 말씀하시기를 “오라. 우리가 서로 변론하자. 너희 죄가 주홍 같을지라도 눈과 같이 희어질 것이요, 진홍같이 붉을지라도 양털같이 되리라. 너희가 즐겨 순종하면 땅의 아름다운 소산을 먹을 것이요, 너희가 거절하여 배반하면 칼에 삼키우리라.” 하셨다.");
		sb.append("<br/><br/>");
		sb.append("이 말은 대화를 통해 어떤 죄도 사함 받고, 눈같이 양털같이 희어지고 깨끗해지며 복을 받는다는 말씀이다. 해서 신천지도 대화를 청하고 있다. 하나 아직 단 한 사람도 대화의 광장에 나오지 않고 있다. 이는 교만과 권위와 자부심에 사로잡히어 자기의 부족을 알지 못함이다.");
		sb.append("<br/><br/>");
		sb.append("신천지(약속의 목자)는 계시록의 하나님의 지시에 따라 교회들에게 보냄을 받았고, 열린 계시 책을 받아 예언과 성취와 실상을 전하고 있다. 또 예수님의 피와 증거하는 말로 용의 무리와 거짓 목자들과 싸워 이겼다.");
		sb.append("<br/><br/>");
		sb.append("신앙인 여러분들, 성경에서 영생을 얻는 줄 생각하고 성경을 상고한다면, 신약 성경에 예수님이 약속한 목자를 성경으로 찾아 그의 증거를 받아야 목적을 이룰 것이다. “하나님 하나님, 예수님 예수님” 한다고 해서 구원받는 것이 아니요, 성경의 약속을 믿고 그 뜻대로 해야 구원이 있게 된다.");
		sb.append("<br/><br/>");
		sb.append("나는 한국의 기독교인들 단 한 사람도 구원받지 못한다고 했다. 이것이 거짓말이고 이단의 말인지 계시록 22장 18-19절을 보고 말해야 할 것이다. 참으로 신약 계시록을 가감하지 않았는가? 기록된 성경에게 물어 보라.");
		sb.append("<br/><br/>");
		sb.append("예수님이 유대인들에게 “너희 아비는 거짓말쟁이고 너희도 너희 아비같이 거짓말한다.” 함과 같이, 한국 교회들은 거짓말 그만하고 대화의 광장으로 나오기 바란다.");
		sb.append("<br/><br/>");
		sb.append("그대들이 대화의 광장에 나오지 못하는 이유는 자기들의 거짓말이 드러나고 망신당할 것이 걱정이 되어 못 나오는 것으로 알고 있다. 나와서 대화를 하면 주홍 같은 죄도 눈같이 희어질 것이다. 이렇게 될까 하여 마귀는 못 나가게 하려고 갖은 생각을 다하게 하는 것이나, 참 신앙인은 이기고 나올 것이다.");
		sb.append("<br/><br/>");
		sb.append("신천지는 신약 성경 계시록을 통달한다. 이 말은 참말이다. 이는 하나님이 옛 비밀한 것 곧 계시록을 6천 년간 감추어 두었다가 오늘날 신천지에 주어 증거하게 하셨기 때문이다.");
		sb.append("<br/><br/>");
		sb.append("각 교회들은 신약에 약속한 목자(계 22:16)를 만나, 계시록에 대한 증거를 받아 약속의 나라 신천지 12지파에 등록되어야, 약속의 민족 천민이 된다. 아멘.");
		sb.append("<br/><br/>");
		return sb.toString();
	}
}
