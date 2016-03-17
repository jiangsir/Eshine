<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${initParam.TITLE}</title>
<link href="style.css" rel="stylesheet" type="text/css"/>
<jsp:useBean id="now" class="java.util.Date"></jsp:useBean>
</head>
<script type="text/javascript" src="./jscripts/jquery-1.2.6.min.js"></script>
<script language="javascript">
jQuery(document).ready(function(){
	$("#type").change(function(){ //事件發生
		//alert("======");
		$('#form1').submit();
	});
	
	jQuery("span[id=deleteJob]").click(function(){
		if(!confirm("確定刪除這個投稿作業？")){
			return;
		}
		location="DeleteJob?id="+$(this).attr("jobid");
	});
	
});
</script>
<body>
<div id="wrapper">
  <jsp:include page="Header.jsp" />
  <div id="inner-wrap">
    <jsp:include page="SideBar.jsp" />
    <div id="main">
	<h1><a href="./InsertJob">建立新的投稿作業</a></h1>
	<c:if test="${job.id>0}">
      <h1>現正進行的投稿作業：</h1>
        <h2><a href="./ShowJob?id=${job.id}">${job.niandu} 學年度 -- ${job.title}</a></h2>
	  <br />
	  <br />
	  </c:if>
      <h1>所有投稿作業：</h1>
      <c:forEach var="job" items="${jobs}">
        <h2><a href="./ShowJob?id=${job.id}">${job.niandu} 學年度 -- ${job.title}</a>
		<c:if test="${job.starttime.time > now.time}"> - 準備進行！</c:if>(<span><a href="./EditJob?id=${job.id}">修改</a></span> | <span id="deleteJob" jobid="${job.id}">刪除</span>)
		</h2>
      </c:forEach>
      <br />
      <br />
      <br />
      <br />
    </div>
  </div>
  <jsp:include page="Footer.jsp" />
</div>
</body>
</html>
