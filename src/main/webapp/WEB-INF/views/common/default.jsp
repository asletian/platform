<%@ include file="/WEB-INF/views/common/taglib.jsp"%>
<html>
<head>
<title>${fns:getConfig('productName')}</title>
<%@include file="/WEB-INF/views/common/include.jsp"%>
</head>
<body>
	<div id="main" class="container-fluid">
		<tiles:insertAttribute name="header" ignore="true" />
		<div id="content" class="row-fluid">
			<div id="left">
				<tiles:insertAttribute name="menu" ignore="true" />
			</div>
			<div id="openClose" class="close">&nbsp;</div>
			<div id="right">
				<tiles:insertAttribute name="body" ignore="true" />
			</div>
		</div>
		<tiles:insertAttribute name="footer" ignore="true" />
	</div>
	<script type="text/javascript"> 
		var lw = "14.89%", rw = "82.97%"; // 左侧窗口展开大小
		var lwClose = "0%", rwClose = "97.58%"; // 左侧窗口折叠大小
		function wSize(){
			var strs=getWindowSize().toString().split(",");
			if(strs[0]<550){
				$("#main").css("height",550);
				$("#openClose").height(strs[0]);
			}else{
				$("#menuFrame, #mainFrame, #openClose").height(strs[0]-$("#header").height() - $("#footer").height() - 80);
				$("#openClose").height($("#openClose").height()-6);
			}
			if(strs[1]<980){
				$("#main").css("width",970);
				$("html").css("overflow-x","auto");
			}else{
				$("#main").css("width","auto");
				$("html").css("overflow-x","hidden");
			}
		}
		
		$("#left").width(lw);$("#right").width(rw);
		$("#openClose").click(function(){
			if($(this).hasClass("close")){
				$(this).removeClass("close");
				$(this).addClass("open");
				$("#left").animate({width:lwClose,opacity:"hide"});
				$("#right").animate({width:rwClose});
			}else{
				$(this).addClass("close");
				$(this).removeClass("open");
				$("#left").animate({width:lw,opacity:"show"});
				$("#right").animate({width:rw});
			}
		});
		if(!Array.prototype.map)
			Array.prototype.map = function(fn,scope) {
			var result = [],ri = 0;
			for (var i = 0,n = this.length; i < n; i++){
			  if(i in this){
			    result[ri++]  = fn.call(scope ,this[i],i,this);
			  }
			}
			return result;
		};
		var getWindowSize = function(){
			return ["Height","Width"].map(function(name){
			  return window["inner"+name] ||
				document.compatMode === "CSS1Compat" && document.documentElement[ "client" + name ] || document.body[ "client" + name ];
			});
		};
		window.onload = function (){
			if(!+"\v1" && !document.querySelector) { // for IE6 IE7
			  document.body.onresize = resize;
			} else { 
			  window.onresize = resize;
			}
			function resize() {
				wSize();
				return false;
			}
		};
		wSize(); // 在主窗体中定义，设置调整目标
	</script>
</body>
</html>
