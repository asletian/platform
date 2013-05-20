<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true"%>
<!DOCTYPE html>
<script type="text/javascript">
function searchForm(pageNo, pageSize) {
	if($("#pageNo")) {
		$("#pageNo").val(pageNo);
	} else {
		$("#messageBox").text("此页面缺少pageNO!");
		return false;
	}
	
	if($("#pageSize")) {
		$("#pageSize").val(pageSize);
	} else {
		$("#messageBox").text("此页面缺少pageSize!");
		return false;
	}
	
	//强制页面也许有条件查询的form
	if($("#searchForm")) {
		$("#searchForm").submit();
	} else {
		$("#searchForm").text("此页面缺少searchForm!");
		return false;
	}
	return false;
}
</script>

<%
int paginationSize = 5;
int current =  page.getNumber() + 1;
int begin = Math.max(1, current - paginationSize/2);
int end = Math.min(begin + (paginationSize - 1), page.getTotalPages());

request.setAttribute("current", current);
request.setAttribute("begin", begin);
request.setAttribute("end", end);
%>

<c:choose>

	<c:when test="${not empty page.content }">
		<div class="pagination" style="margin-top: 0px; margin-bottom: 0px;">
			<ul>
			
				 <% if (page.hasPreviousPage()){%>
		               	<li><a href="javascript:searchForm(0, ${page.size})">&lt;&lt;首页</a></li>
		                <li><a href="javascript:searchForm(${current-2}, ${page.size})">&lt;上一页</a></li>
		         <%}else{%>
		                <li class="disabled"><a href="#">&lt;&lt;首页</a></li>
		                <li class="disabled"><a href="#">&lt;上一页</a></li>
		         <%} %>
		 
				<c:forEach var="i" begin="${begin}" end="${end}">
		            <c:choose>
		                <c:when test="${i == current}">
		                    <li class="active"><a href="javascript:searchForm(${i-1}, ${page.size})">${i}</a></li>
		                </c:when>
		                <c:otherwise>
		                    <li><a href="javascript:searchForm(${i-1}, ${page.size})">${i}</a></li>
		                </c:otherwise>
		            </c:choose>
		        </c:forEach>
			  
			  	 <% if (page.hasNextPage()){%>
		               	<li><a href="javascript:searchForm(${current}, ${page.size})">下一页&gt;</a></li>
		                <li><a href="javascript:searchForm(${page.totalPages-1}, ${page.size})">尾页&gt;&gt;</a></li>
		         <%}else{%>
		                <li class="disabled"><a href="#">下一页&gt;</a></li>
		                <li class="disabled"><a href="#">尾页&gt;&gt;</a></li>
		         <%} %>
		         
				<li class="disabled"><a href='#'>共${page.totalElements}条 ${current}/${page.totalPages}页</a></li>
				
			</ul>
		</div>
	</c:when>
	
	<c:otherwise>
		<div class="pagination" style="margin-top: 0px; margin-bottom: 0px;">
			<ul>
				<li class="disabled"><a href="#">&lt;&lt;</a></li>
		        <li class="disabled"><a href="#">&lt;</a></li>
		        <li class="disabled"><a href="#">&gt;</a></li>
		        <li class="disabled"><a href="#">&gt;&gt;</a></li>
				<li class="disabled"><a href='#'>共0条 0页</a></li>
			</ul>
		</div>
	</c:otherwise>
	
</c:choose>