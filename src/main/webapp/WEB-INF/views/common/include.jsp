<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta name="author" content="http://www.crazy/xy.com"/>
<meta http-equiv="X-UA-Compatible" content="IE=7,IE=9" />
<script src="${ctxStatic}/jquery/jquery-base/jquery-1.9.1.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-base/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/jquery/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-validation/1.11.0/jquery.validate.method.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/jquery/jquery-jbox/2.3/Skins/Bootstrap/jbox.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/jquery/jquery-jqGrid/js/jquery.jqGrid.src.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/2.0.4/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script src="${ctxStatic}/bootstrap/2.0.4/js/bootstrap.min.js" type="text/javascript"></script>
<!--[if IE 6]><link href="${ctxStatic}/bootstrap/2.0.4/css/ie6.min.css" rel="stylesheet">
<script src="${ctxStatic}/bootstrap/2.0.4/js/ie6.min.js" type="text/javascript"></script><![endif]-->
<script src="${ctxStatic}/common.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/style.min.css" type="text/css" rel="stylesheet" />
<style type="text/css">
	.nav li{margin-top:8px;}.nav li.title{margin-top:0;}.nav li.menu,.nav li.dropdown{margin:8px 3px 0 3px}
	.nav li.menu a{padding:5px 6px;*padding:4px 5px 3px 5px;}.nav li.dropdown a{padding:5px 6px;*padding:1px 5px 3px 5px;}
	.nav li a{font-size:14px;padding:6px 8px;*padding:3px 8px;}
</style>
<script type="text/javascript"> 
	$(document).ready(function() {
		$("#menu a.menu").click(function(){
			$("#menu li.menu").removeClass("active");
			$(this).parent().addClass("active");
		});
		
		$(".accordion-heading a").click(function(){
			$('.accordion-toggle i').removeClass('icon-chevron-down');
			$('.accordion-toggle i').addClass('icon-chevron-right');
			if(!$($(this).attr('href')).hasClass('in')){
				$(this).children('i').removeClass('icon-chevron-right');
				$(this).children('i').addClass('icon-chevron-down');
			}
		});
		
		$(".accordion-body a").click(function(){
			$("#menu li").removeClass("active");
			$("#menu li i").removeClass("icon-white");
			$(this).parent().addClass("active");
			$(this).children("i").addClass("icon-white");
		});
		
		//$(".accordion-body a:first i").click();
	});
</script>