<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ page import="java.util.*"%>
<%@ page import="com.escape.model.ChurchMember"%>
<%@ page import="com.escape.common.Pager"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.escape.common.CommonUtil" %>
<%
	@SuppressWarnings("unchecked")
	List<ChurchMember> chMemList = (List<ChurchMember> )request.getAttribute("list");
	if(chMemList == null){
		chMemList = new ArrayList<ChurchMember>();
	}
	
	Pager pager = (Pager)request.getAttribute("pager");
	int index = pager.startItem;
	String cnt = CommonUtil.ckNullStr((String)request.getAttribute("totalCnt"),"0");
	
%>

<div class="table-responsive" style="width: 100%">
	<table cellpadding="0" cellspacing="0" border="0" class="table table-striped table-bordered table-hover" id="example">
		<thead>
			<tr class="table-dark">
				<th class="text-center table-dark">NO.</th>
				<th class="text-center table-dark">이름</th>
				<th class="text-center table-dark">메일 주소</th>
				<th class="text-center table-dark">구분</th>
				<th class="text-center table-dark">소속 교회</th>
				<th class="text-center table-dark">교회</th>
				<th class="text-center table-dark">등록날자</th>
				<th class="text-center table-dark">삭제여부</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(int i =0 ; i< chMemList.size() ; i++ ){
	            	ChurchMember obj = chMemList.get(i);
	            	if(obj == null) continue;
			%>
			<tr style="text-align: center;" <% if( i%2 ==0) {%> class ="odd gradeX" <%} else { %> class ="even gradeC" <%}%>>
				<td><%=(index++) %></td>
				<td><%=obj.getName() %></td>
				<td><%=obj.getEmail() %></td>
				<td>
					<%if(obj.getChurchCategory() !=null){ %> <%=obj.getChurchCategory() %>
					<%}else {%> 알수 없음 <%}%>
				</td>
				<td><%=obj.getChurchName() %></td>
				<td><%=obj.getChurchRoot() %></td>
				<td><%=new SimpleDateFormat("yyyy-MM-dd hh:mm").format(obj.getInsDate()) %></td>
				<td>
					<button>
						<img style="width: 28px" class="border border-primary rounded-circle" src="resources/images/trash-can-filled2.svg" onclick="alert('<%=obj.getId() %>')" />
					</button>
				</td>
			</tr>
			<%} %>
		</tbody>
	</table>
	<nav class="text-center pt20">
		<ul class="pagination">
			<%if(null!=pager){%>
				<%=pager.getPagingPrintString("goPage", "<span aria-hidden=\"true\">&lt;</span>", "<span aria-hidden=\"true\">&gt;</span>")%>
			<%} %>
		</ul>
	</nav>
</div>
<script type="text/javascript">
function goPage(page){	
   var param = "&curPageNum="+page;
   reload(param)
}
</script>