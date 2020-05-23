<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Natural</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%@include file="../menu/meta.jsp"%>
</head>
<body>
	<%@include file="../menu/top_header.jsp"%>
	<div class="page-content">
		<div class="row">
			<div class="col-md-2">
				<%@include file="../menu/left_header.jsp"%>
			</div>
			<div class="col-md-10">
				<div class="content-box-large">
					<div class="panel-heading">
						<div class="row" style="padding:15px">
							<div class="form-group">
								<div class="col-lg-7" style="margin-left: -30px;">
									<label class="col-lg-1 control-label">ZCV</label>
									<label class="checkbox-inline"> 
										<input type="radio" checked="checked"	name="radio_zcv" value="all" onclick="onSearch()"> 전체
									</label> 
									<label class="checkbox-inline"> 
										<input type="radio"	name="radio_zcv" value="Y" onclick="onSearch()"> 발송
									</label> 
									<label class="checkbox-inline"> 
										<input type="radio" name="radio_zcv" value="N" onclick="onSearch()"> 미발송
									</label>
								</div>
							</div>
						</div>
						<div class="row" style="padding:15px;padding-top: 0px">
							<div class="form-group">
								<div class="col-lg-7" style="margin-left: -30px;">
									<label class="col-lg-1 control-label">EMAIL</label>
									<label class="checkbox-inline"> 
										<input type="radio" checked="checked"	name="radio_email" value="all" onclick="onSearch()"> 전체
									</label> 
									<label class="checkbox-inline"> 
										<input type="radio"	name="radio_email" value="Y" onclick="onSearch()"> 발송
									</label> 
									<label class="checkbox-inline"> 
										<input type="radio" name="radio_email" value="N" onclick="onSearch()"> 미발송
									</label>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-1" style="padding-right: 0px"> 
								<select class="form-control" id="selCat" style="float: left;">
									<option value="all">전체</option>
									<option value="name">이름</option>
									<option value="email">메일</option>
									<option value="catetory">구분</option>
									<option value="church">교회명</option>
								</select> 
							</div>
							<div class="col-lg-6" style="padding-left: 0px">
								<div class="input-group form">
									<input type="text" class="form-control" placeholder="Search..." id="txtContenxt" name="txtContenxt"> 
									<span class="input-group-btn">
										<button class="btn btn-primary" type="button" onclick="onSearch()">Search</button>
									</span>
								</div>
							</div>
							<div class="col-lg-5" style="padding-left: 0px">
								<a href="/church/excelDownload.do" style="float: right;margin-right: 50px">엑셀</a>
							</div>
						</div>
					</div>
					<div class="panel-body">
						<div id="divList"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<footer>
		<div class="container">
			<div class="copy text-center">
				Copyright 2014 <a href='#'>Website</a>
			</div>
		</div>
	</footer>
</body>

<script type="text/javascript">
onSearch();

function onSearch(){
	var param ="";
	reload(param);
}

function getParam(param){
	var content = $("#txtContenxt").val();
	if(content != null && content.trim() != ''){
		param += "&searchType="+$("#selCat").val();
		param += "&searchContent="+$("#txtContenxt").val();
	}
	param += "&zcv_yn="+$(":input:radio[name=radio_zcv]:checked").val();
	param += "&email_yn="+$(":input:radio[name=radio_email]:checked").val();
	return param;
}

function reload(param){
	param = getParam(param);
	var request = new Array();
	ajaxCall("<%=request.getContextPath()%>/chMemListTpl.do",param, innerHTML,"divList");
}

</script>
</html>