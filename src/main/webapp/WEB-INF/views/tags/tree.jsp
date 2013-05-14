<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<!DOCTYPE html>
<html style="overflow-x:hidden;overflow-y:auto;">
<head>
	<title>数据选择</title>
	<%@include file="/WEB-INF/views/common/include.jsp"%>
	<script type="text/javascript">
		var tree, setting = {view:{selectedMulti:false},check:{enable:"${checked}",nocheckInherit:true},
				data:{simpleData:{enable:true}},
				view:{
					fontCss:function(treeId, treeNode) {
						return (!!treeNode.highlight) ? {"font-weight":"bold"} : {"font-weight":"normal"};
					}
				},
				callback:{beforeClick:function(id, node){
					if("${checked}" == "true"){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}
				}, 
				onDblClick:function(){
					top.$.jBox.getBox().find("button[value='ok']").trigger("click");
				}}};
		$(document).ready(function(){
			$.get("${ctx}${url}${fn:indexOf(url,'?')==-1?'?':'&'}&extId=${extId}&module=${module}&t="+new Date().getTime(), function(zNodes){
				// 初始化树结构
				tree = $.fn.zTree.init($("#tree"), setting, eval("("+zNodes+")"));
				
				// 默认展开一级节点
				var nodes = tree.getNodesByParam("level", 0);
				for(var i=0; i<nodes.length; i++) {
					tree.expandNode(nodes[i], true, false, false);
				}
				// 默认选择节点
				var ids = "${selectIds}".split(",");
				for(var i=0; i<ids.length; i++) {
					var node = tree.getNodeByParam("id", ids[i]);
					if("${checked}" == "true"){
						try{tree.checkNode(node, true, true);}catch(e){}
						tree.selectNode(node, false);
					}else{
						tree.selectNode(node, true);
					}
				}
			});
		});
	</script>
</head>
<body>
	<div id="tree" class="ztree" style="padding:15px 20px;"></div>
</body>