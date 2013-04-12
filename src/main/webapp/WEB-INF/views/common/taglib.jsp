<%-- <%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %> --%>
<%-- <%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%-- <%@ taglib prefix="fnc" uri="/WEB-INF/tlds/fnc.tld" %> --%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%-- <c:set var="ctx" value="${pageContext.request.contextPath}${fns:getAdminPath()}"/> --%>
<c:set var="ctx" value="${pageContext.request.contextPath}/z"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static"/>
<%  
    response.addHeader("Pragma","no-cache");  
    response.setHeader("Cache-Control","no-cache");  
    response.setDateHeader("Expires",0);  
%>