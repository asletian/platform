<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<html>
<head>
	<title>人员管理</title>
	<%@include file="/WEB-INF/views/common/include.jsp"%>
	<script type="text/javascript">
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/uc/employee/list">人员列表</a></li>
		<li><a href="${ctx}/uc/employee/form">人员添加</a></li>
	</ul>
	<tags:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<tr><th>员工姓名</th><th>员工编号</th><th>性别</th><th>地址</th><th>座机</th><th>手机</th><th>转正日期</th><th>合同时长</th><th>操作</th></tr>
		<c:forEach items="${page.result}" var="employee">
			<tr id="${employee.id}"">
				<td>${employee.name}</td>
				<td>${employee.staffNum}</td>
				<td>${employee.sex eq 'male'?'男':'女'}</td>
				<td>${employee.phoneURL}</td>
				<td>${employee.officePhone}</td>
				<td>${employee.mobile}</td>
				<td>${employee.positiveTime}</td>
				<td>${employee.contractDuration}</td>
				<td>
					<a href="${ctx}/uc/employee/form?id=${employee.id}">修改</a>
					<a href="${ctx}/uc/employee/delete?id=${employee.id}"  onclick="return confirmx('确认要删除该人员吗？', this.href)">删除</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	<tags:paginationByMyBatis page="${page}" />
</body>
</html>

