<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<div class="accordion" id="menu">
	<c:set var="firstMenu" value="true"/>
	<c:set var="firstMenuId" value=""/>
	<c:forEach items="${menus}" var="menu" varStatus="idxStatus">
		<div class="accordion-group">
		    <div class="accordion-heading">
		    	<a class="accordion-toggle" data-toggle="collapse" data-parent="#menu" href="#collapse${menu.id}"><i class="icon-chevron-${firstMenu?'down':'right'}"></i>&nbsp;&nbsp;${menu.name}</a>
		    </div>
		    <div id="collapse${menu.id}" class="accordion-body collapse ${firstMenu?'in':''}">
		    <c:if test="${firstMenu}"><c:set var="firstMenuId" value="${menu.id}"/><c:set var="firstMenu" value="false"/></c:if>
				<div class="accordion-inner">
					<ul class="nav nav-list">
					<c:forEach items="${fns:getMenusByParent(firstMenuId)}" var="menuChild">
						<li><a href="${fn:indexOf(menuChild.href, '://') eq -1?ctx:''}${menuChild.href}" target="right"><i class="icon-${not empty menuChild.icon?menuChild.icon:'circle-arrow-right'}"></i>&nbsp;&nbsp;${menuChild.name}</a></li>
					</c:forEach>
					<c:set var="firstMenu" value="false"/>
					</ul>
				</div>
		    </div>
		</div>
	</c:forEach>
</div>