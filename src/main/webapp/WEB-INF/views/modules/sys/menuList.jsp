<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
	<title>菜单管理</title>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/menu/">菜单列表</a></li>
		<li><a href="${ctx}/sys/menu/form">菜单添加</a></li>
	</ul>
<%-- 	<tags:message content="${message}"/> --%>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<tr><th>名称</th><th>链接</th><th>排序</th><th>可见</th><th>操作</th></tr>
		<c:forEach items="${list}" var="menu">
			<tr id="${menu.id}" pId="${menu.parent.id ne 1?menu.parent.id:'0'}">
				<td><a href="${ctx}/sys/menu/form?id=${menu.id}">${menu.name}</a></td>
				<td>${menu.href}</td>
				<td>${menu.sort}</td>
				<td>${menu.isShow eq 1?'显示':'隐藏'}</td>
				<td>
					<a href="${ctx}/sys/menu/form?id=${menu.id}">修改</a>
					<a href="${ctx}/sys/menu/delete?id=${menu.id}" onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">删除</a>
					<a href="${ctx}/sys/menu/form?parent.id=${menu.id}">添加下级菜单</a> 
				</td>
			</tr>
		</c:forEach>
	</table>
