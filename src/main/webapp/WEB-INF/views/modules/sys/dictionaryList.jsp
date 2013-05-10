<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<%@include file="/WEB-INF/views/common/include.jsp"%>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/dictionary/">字典列表</a></li>
		<li><a href="${ctx}/sys/dictionary/form">字典添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="filterRules" action="${ctx}/sys/dictionary/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.number}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.size}"/>
		<form:hidden path="rules[0].name" value="label" />
		<form:hidden path="rules[0].operator" value="${EQ}" />
		<form:hidden path="rules[1].name" value="type" />
		<form:hidden path="rules[1].operator" value="${EQ}" />
		&nbsp;&nbsp;<label>标签：</label><form:input path="rules[0].value" class="input-medium"/>
		&nbsp;&nbsp;<label>类型 ：</label><form:input path="rules[1].value" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered ">
		<thead><tr><th>标签</th><th>键值</th><th>类型</th><th>描述</th><th>排序</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.content}" var="dictionary">
			<tr>
				<td><a href="${ctx}/sys/dictionary/form?id=${dictionary.id}">${dictionary.label}</a></td>
				<td>${dictionary.value}</td>
				<td><a href="javascript:" onclick="$('#type').val('${dictionary.type}');$('#searchForm').submit();return false;">${dictionary.type}</a></td>
				<td>${dictionary.desciption}</td>
				<td>${dictionary.sort}</td>
				<td>
    				<a href="${ctx}/sys/dictionary/form?id=${dictionary.id}">修改</a>
					<a href="${ctx}/sys/dictionary/delete?id=${dictionary.id}" onclick="return confirmx('确认要删除该字典吗？', this.href)">删除</a>
<%--     				<a href="<c:url value='${fns:getAdminPath()}/sys/dictionary/form?type=${dictionary.type}'><c:param name='desciption' value='${dictionary.desciption}'/></c:url>">添加键值</a> --%>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<tags:pagination page="${page}" />
</body>
</html>
