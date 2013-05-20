<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<html>
<head>
	<title>人员管理</title>
	<%@include file="/WEB-INF/views/common/include.jsp"%>
	<script type="text/javascript" src="${ctxStatic}/My97DatePicker/WdatePicker.js"></script> 
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/uc/employee/list">人员列表</a></li>
		<li class="active"><a href="${ctx}/uc/employee/form?id=${employee.id}">人员${not empty employee.id?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="employee" action="${ctx}/uc/employee/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div class="control-group">
			<label class="control-label">员工姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
				<div class="control-group">
			<label class="control-label">员工编号:</label>
			<div class="controls">
				<form:input path="staffNum" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别:</label>
			<div class="controls">
				<form:radiobuttons path="sex" itemLabel="label" itemValue="value" htmlEscape="false" class="required" items="${fns:getDictList('sex_type')}" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址:</label>
			<div class="controls">
				<form:input path="phoneURL" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">座机:</label>
			<div class="controls">
				<form:input path="officePhone" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">转正时间:</label>
			<div class="controls">
				<form:input path="positiveTime" htmlEscape="false" maxlength="50" onClick="WdatePicker()" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">合同时长:</label>
			<div class="controls">
				<form:input path="contractDuration" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
