<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}/z"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<%@ page import="com.crazy.pss.common.persistence.FilterRule.RuleOperator"%>
<%  
    response.addHeader("Pragma","no-cache");  
    response.setHeader("Cache-Control","no-cache");  
    response.setDateHeader("Expires",0);  
%>