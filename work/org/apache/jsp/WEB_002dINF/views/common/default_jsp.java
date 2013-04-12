package org.apache.jsp.WEB_002dINF.views.common;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class default_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

static private org.apache.jasper.runtime.ProtectedFunctionMapper _jspx_fnmap_0;

static {
  _jspx_fnmap_0= org.apache.jasper.runtime.ProtectedFunctionMapper.getMapForFunction("fns:getConfig", com.crazy.pss.common.config.GlobalConfiguration.class, "getConfig", new Class[] {java.lang.String.class});
}

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList(3);
    _jspx_dependants.add("/WEB-INF/views/common/taglib.jsp");
    _jspx_dependants.add("/WEB-INF/views/common/include.jsp");
    _jspx_dependants.add("/WEB-INF/tlds/fns.tld");
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.AnnotationProcessor _jsp_annotationprocessor;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_annotationprocessor = (org.apache.AnnotationProcessor) getServletConfig().getServletContext().getAttribute(org.apache.AnnotationProcessor.class.getName());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\n');
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write('\n');
      if (_jspx_meth_c_005fset_005f0(_jspx_page_context))
        return;
      out.write('\n');
      if (_jspx_meth_c_005fset_005f1(_jspx_page_context))
        return;
      out.write('\n');
  
    response.addHeader("Pragma","no-cache");  
    response.setHeader("Cache-Control","no-cache");  
    response.setDateHeader("Expires",0);  

      out.write("\n");
      out.write("<html>\n");
      out.write("<head>\n");
      out.write("<title>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${fns:getConfig('productName')}", java.lang.String.class, (PageContext)_jspx_page_context, _jspx_fnmap_0, false));
      out.write("</title>\n");
      out.write("<meta http-equiv=\"Cache-Control\" content=\"no-store\" />\n");
      out.write("<meta http-equiv=\"Pragma\" content=\"no-cache\" />\n");
      out.write("<meta http-equiv=\"Expires\" content=\"0\" />\n");
      out.write("<meta name=\"author\" content=\"http://www.crazy/xy.com\"/>\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=7,IE=9\" />\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/jquery/jquery-base/jquery-1.9.1.min.js\" type=\"text/javascript\"></script>\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/jquery/jquery-base/jquery-migrate-1.1.1.min.js\" type=\"text/javascript\"></script>\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/jquery/jquery-validation/1.11.0/jquery.validate.min.css\" type=\"text/css\" rel=\"stylesheet\" />\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/jquery/jquery-validation/1.11.0/jquery.validate.min.js\" type=\"text/javascript\"></script>\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/jquery/jquery-validation/1.11.0/jquery.validate.method.min.js\" type=\"text/javascript\"></script>\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/jquery/jquery-jbox/2.3/Skins/Bootstrap/jbox.css\" rel=\"stylesheet\" />\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/jquery/jquery-jbox/2.3/jquery.jBox-2.3.min.js\" type=\"text/javascript\"></script>\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/jquery/jquery-jbox/2.3/i18n/jquery.jBox-zh-CN.min.js\" type=\"text/javascript\"></script>\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/jquery/jquery-jqGrid/js/jquery.jqGrid.src.js\" type=\"text/javascript\"></script>\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap/2.0.4/css/bootstrap.min.css\" type=\"text/css\" rel=\"stylesheet\" />\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap/2.0.4/js/bootstrap.min.js\" type=\"text/javascript\"></script>\n");
      out.write("<!--[if IE 6]><link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap/2.0.4/css/ie6.min.css\" rel=\"stylesheet\">\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/bootstrap/2.0.4/js/ie6.min.js\" type=\"text/javascript\"></script><![endif]-->\n");
      out.write("<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/common.min.js\" type=\"text/javascript\"></script>\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${ctxStatic}", java.lang.String.class, (PageContext)_jspx_page_context, null, false));
      out.write("/style.min.css\" type=\"text/css\" rel=\"stylesheet\" />\n");
      out.write("<style type=\"text/css\">\n");
      out.write("\t.nav li{margin-top:8px;}.nav li.title{margin-top:0;}.nav li.menu,.nav li.dropdown{margin:8px 3px 0 3px}\n");
      out.write("\t.nav li.menu a{padding:5px 6px;*padding:4px 5px 3px 5px;}.nav li.dropdown a{padding:5px 6px;*padding:1px 5px 3px 5px;}\n");
      out.write("\t.nav li a{font-size:14px;padding:6px 8px;*padding:3px 8px;}\n");
      out.write("</style>\n");
      out.write("<script type=\"text/javascript\"> \n");
      out.write("\t$(document).ready(function() {\n");
      out.write("\t\t$(\"#menu a.menu\").click(function(){\n");
      out.write("\t\t\t$(\"#menu li.menu\").removeClass(\"active\");\n");
      out.write("\t\t\t$(this).parent().addClass(\"active\");\n");
      out.write("\t\t});\n");
      out.write("\t\t\n");
      out.write("\t\t$(\".accordion-heading a\").click(function(){\n");
      out.write("\t\t\t$('.accordion-toggle i').removeClass('icon-chevron-down');\n");
      out.write("\t\t\t$('.accordion-toggle i').addClass('icon-chevron-right');\n");
      out.write("\t\t\tif(!$($(this).attr('href')).hasClass('in')){\n");
      out.write("\t\t\t\t$(this).children('i').removeClass('icon-chevron-right');\n");
      out.write("\t\t\t\t$(this).children('i').addClass('icon-chevron-down');\n");
      out.write("\t\t\t}\n");
      out.write("\t\t});\n");
      out.write("\t\t\n");
      out.write("\t\t$(\".accordion-body a\").click(function(){\n");
      out.write("\t\t\t$(\"#menu li\").removeClass(\"active\");\n");
      out.write("\t\t\t$(\"#menu li i\").removeClass(\"icon-white\");\n");
      out.write("\t\t\t$(this).parent().addClass(\"active\");\n");
      out.write("\t\t\t$(this).children(\"i\").addClass(\"icon-white\");\n");
      out.write("\t\t});\n");
      out.write("\t\t\n");
      out.write("\t\t//$(\".accordion-body a:first i\").click();\n");
      out.write("\t});\n");
      out.write("</script>");
      out.write("\n");
      out.write("</head>\n");
      out.write("<body>\n");
      out.write("\t<div id=\"main\" class=\"container-fluid\">\n");
      out.write("\t\t");
      if (_jspx_meth_tiles_005finsertAttribute_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t<div id=\"content\" class=\"row-fluid\">\n");
      out.write("\t\t\t<div id=\"left\">\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_tiles_005finsertAttribute_005f1(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t\t<div id=\"openClose\" class=\"close\">&nbsp;</div>\n");
      out.write("\t\t\t<div id=\"right\">\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_tiles_005finsertAttribute_005f2(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t\t\t</div>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t");
      if (_jspx_meth_tiles_005finsertAttribute_005f3(_jspx_page_context))
        return;
      out.write("\n");
      out.write("\t</div>\n");
      out.write("\t<script type=\"text/javascript\"> \n");
      out.write("\t\tvar lw = \"14.89%\", rw = \"82.97%\"; // å·¦ä¾§çªå£å±å¼å¤§å°\n");
      out.write("\t\tvar lwClose = \"0%\", rwClose = \"97.58%\"; // å·¦ä¾§çªå£æå å¤§å°\n");
      out.write("\t\tfunction wSize(){\n");
      out.write("\t\t\tvar strs=getWindowSize().toString().split(\",\");\n");
      out.write("\t\t\tif(strs[0]<550){\n");
      out.write("\t\t\t\t$(\"#main\").css(\"height\",550);\n");
      out.write("\t\t\t\t$(\"#openClose\").height(strs[0]);\n");
      out.write("\t\t\t}else{\n");
      out.write("\t\t\t\t$(\"#menuFrame, #mainFrame, #openClose\").height(strs[0]-$(\"#header\").height() - $(\"#footer\").height() - 80);\n");
      out.write("\t\t\t\t$(\"#openClose\").height($(\"#openClose\").height()-6);\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\tif(strs[1]<980){\n");
      out.write("\t\t\t\t$(\"#main\").css(\"width\",970);\n");
      out.write("\t\t\t\t$(\"html\").css(\"overflow-x\",\"auto\");\n");
      out.write("\t\t\t}else{\n");
      out.write("\t\t\t\t$(\"#main\").css(\"width\",\"auto\");\n");
      out.write("\t\t\t\t$(\"html\").css(\"overflow-x\",\"hidden\");\n");
      out.write("\t\t\t}\n");
      out.write("\t\t}\n");
      out.write("\t\t\n");
      out.write("\t\t$(\"#left\").width(lw);$(\"#right\").width(rw);\n");
      out.write("\t\t$(\"#openClose\").click(function(){\n");
      out.write("\t\t\tif($(this).hasClass(\"close\")){\n");
      out.write("\t\t\t\t$(this).removeClass(\"close\");\n");
      out.write("\t\t\t\t$(this).addClass(\"open\");\n");
      out.write("\t\t\t\t$(\"#left\").animate({width:lwClose,opacity:\"hide\"});\n");
      out.write("\t\t\t\t$(\"#right\").animate({width:rwClose});\n");
      out.write("\t\t\t}else{\n");
      out.write("\t\t\t\t$(this).addClass(\"close\");\n");
      out.write("\t\t\t\t$(this).removeClass(\"open\");\n");
      out.write("\t\t\t\t$(\"#left\").animate({width:lw,opacity:\"show\"});\n");
      out.write("\t\t\t\t$(\"#right\").animate({width:rw});\n");
      out.write("\t\t\t}\n");
      out.write("\t\t});\n");
      out.write("\t\tif(!Array.prototype.map)\n");
      out.write("\t\t\tArray.prototype.map = function(fn,scope) {\n");
      out.write("\t\t\tvar result = [],ri = 0;\n");
      out.write("\t\t\tfor (var i = 0,n = this.length; i < n; i++){\n");
      out.write("\t\t\t  if(i in this){\n");
      out.write("\t\t\t    result[ri++]  = fn.call(scope ,this[i],i,this);\n");
      out.write("\t\t\t  }\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\treturn result;\n");
      out.write("\t\t};\n");
      out.write("\t\tvar getWindowSize = function(){\n");
      out.write("\t\t\treturn [\"Height\",\"Width\"].map(function(name){\n");
      out.write("\t\t\t  return window[\"inner\"+name] ||\n");
      out.write("\t\t\t\tdocument.compatMode === \"CSS1Compat\" && document.documentElement[ \"client\" + name ] || document.body[ \"client\" + name ];\n");
      out.write("\t\t\t});\n");
      out.write("\t\t};\n");
      out.write("\t\twindow.onload = function (){\n");
      out.write("\t\t\tif(!+\"\\v1\" && !document.querySelector) { // for IE6 IE7\n");
      out.write("\t\t\t  document.body.onresize = resize;\n");
      out.write("\t\t\t} else { \n");
      out.write("\t\t\t  window.onresize = resize;\n");
      out.write("\t\t\t}\n");
      out.write("\t\t\tfunction resize() {\n");
      out.write("\t\t\t\twSize();\n");
      out.write("\t\t\t\treturn false;\n");
      out.write("\t\t\t}\n");
      out.write("\t\t};\n");
      out.write("\t\twSize(); // å¨ä¸»çªä½ä¸­å®ä¹ï¼è®¾ç½®è°æ´ç®æ \n");
      out.write("\t</script>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else log(t.getMessage(), t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_005fset_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f0.setParent(null);
    // /WEB-INF/views/common/taglib.jsp(12,0) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f0.setVar("ctx");
    // /WEB-INF/views/common/taglib.jsp(12,0) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}/z", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fset_005f0 = _jspx_th_c_005fset_005f0.doStartTag();
    if (_jspx_th_c_005fset_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f0);
    return false;
  }

  private boolean _jspx_meth_c_005fset_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_005fset_005f1 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_005fset_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fset_005f1.setParent(null);
    // /WEB-INF/views/common/taglib.jsp(13,0) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f1.setVar("ctxStatic");
    // /WEB-INF/views/common/taglib.jsp(13,0) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fset_005f1.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}/static", java.lang.Object.class, (PageContext)_jspx_page_context, null, false));
    int _jspx_eval_c_005fset_005f1 = _jspx_th_c_005fset_005f1.doStartTag();
    if (_jspx_th_c_005fset_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
      return true;
    }
    _005fjspx_005ftagPool_005fc_005fset_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005fset_005f1);
    return false;
  }

  private boolean _jspx_meth_tiles_005finsertAttribute_005f0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insertAttribute
    org.apache.tiles.jsp.taglib.InsertAttributeTag _jspx_th_tiles_005finsertAttribute_005f0 = new org.apache.tiles.jsp.taglib.InsertAttributeTag();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_tiles_005finsertAttribute_005f0);
    _jspx_th_tiles_005finsertAttribute_005f0.setJspContext(_jspx_page_context);
    // /WEB-INF/views/common/default.jsp(9,2) name = name type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005finsertAttribute_005f0.setName("header");
    // /WEB-INF/views/common/default.jsp(9,2) name = ignore type = boolean reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005finsertAttribute_005f0.setIgnore(true);
    _jspx_th_tiles_005finsertAttribute_005f0.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_tiles_005finsertAttribute_005f0);
    return false;
  }

  private boolean _jspx_meth_tiles_005finsertAttribute_005f1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insertAttribute
    org.apache.tiles.jsp.taglib.InsertAttributeTag _jspx_th_tiles_005finsertAttribute_005f1 = new org.apache.tiles.jsp.taglib.InsertAttributeTag();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_tiles_005finsertAttribute_005f1);
    _jspx_th_tiles_005finsertAttribute_005f1.setJspContext(_jspx_page_context);
    // /WEB-INF/views/common/default.jsp(12,4) name = name type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005finsertAttribute_005f1.setName("menu");
    // /WEB-INF/views/common/default.jsp(12,4) name = ignore type = boolean reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005finsertAttribute_005f1.setIgnore(true);
    _jspx_th_tiles_005finsertAttribute_005f1.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_tiles_005finsertAttribute_005f1);
    return false;
  }

  private boolean _jspx_meth_tiles_005finsertAttribute_005f2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insertAttribute
    org.apache.tiles.jsp.taglib.InsertAttributeTag _jspx_th_tiles_005finsertAttribute_005f2 = new org.apache.tiles.jsp.taglib.InsertAttributeTag();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_tiles_005finsertAttribute_005f2);
    _jspx_th_tiles_005finsertAttribute_005f2.setJspContext(_jspx_page_context);
    // /WEB-INF/views/common/default.jsp(16,4) name = name type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005finsertAttribute_005f2.setName("body");
    // /WEB-INF/views/common/default.jsp(16,4) name = ignore type = boolean reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005finsertAttribute_005f2.setIgnore(true);
    _jspx_th_tiles_005finsertAttribute_005f2.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_tiles_005finsertAttribute_005f2);
    return false;
  }

  private boolean _jspx_meth_tiles_005finsertAttribute_005f3(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  tiles:insertAttribute
    org.apache.tiles.jsp.taglib.InsertAttributeTag _jspx_th_tiles_005finsertAttribute_005f3 = new org.apache.tiles.jsp.taglib.InsertAttributeTag();
    org.apache.jasper.runtime.AnnotationHelper.postConstruct(_jsp_annotationprocessor, _jspx_th_tiles_005finsertAttribute_005f3);
    _jspx_th_tiles_005finsertAttribute_005f3.setJspContext(_jspx_page_context);
    // /WEB-INF/views/common/default.jsp(19,2) name = name type = java.lang.String reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005finsertAttribute_005f3.setName("footer");
    // /WEB-INF/views/common/default.jsp(19,2) name = ignore type = boolean reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_tiles_005finsertAttribute_005f3.setIgnore(true);
    _jspx_th_tiles_005finsertAttribute_005f3.doTag();
    org.apache.jasper.runtime.AnnotationHelper.preDestroy(_jsp_annotationprocessor, _jspx_th_tiles_005finsertAttribute_005f3);
    return false;
  }
}
