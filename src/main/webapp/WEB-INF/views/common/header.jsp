<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<div id="header" class="row-fluid">
	<div id="title">
		<span class="pull-right">你好, <a href="${ctx}/sys/user/info"
			target="mainFrame">xxx</a> | <a href="${ctx}/logout">退出</a> | <a
			target="_blank">访问网站</a> &nbsp;&nbsp;&nbsp;
		</span>
		<ul class="nav nav-pills" style="margin: 0;" id="menu">
			<li class="title"><h1>
					${fns:getConfig('productName')}<small></small>
				</h1></li>
			<li style="width: 18px;">&nbsp;</li>
			<c:set var="firstMenu" value="true" />
			<c:forEach items="${fns:getTopMenus()}" var="menu"
				varStatus="idxStatus">
				<c:if test="${menu.parent.id eq 1}">
					<li class="menu ${firstMenu?' active':''}"><a class="menu"
						href="${ctx}/sys/menu/tree?parentId=${menu.id}" >${menu.name}</a></li>
					<c:if test="${firstMenu}">
						<c:set var="firstMenuId" value="${menu.id}" />
					</c:if>
					<c:set var="firstMenu" value="false" />
				</c:if>
			</c:forEach>
		</ul>
	</div>
</div>