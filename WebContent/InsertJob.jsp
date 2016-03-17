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
</head>
<jsp:useBean id="now" class="java.util.Date" />
<jsp:useBean id="articleBean" class="jiangsir.eshine.Beans.ArticleBean" />
<jsp:setProperty name="articleBean" property="id" value="0" />
<script type="text/javascript" src="./jscripts/jquery-1.2.6.min.js"></script>
<script type="text/javascript" src="jscripts/js_date.js"></script>
<script type="text/javascript" src="jscripts/jquery.timeout.interval.idle.js"></script>
<script language="javascript">
jQuery(document).ready(function(){

    $("select#niandu").children().each(function(){
        if ( ${job.niandu} == $(this).val()) {
            //alert("the same");
            $(this).attr("selected", "true");
            return;
        }
    });
});

</script>
<body>
<div id="wrapper">
  <jsp:include page="Header.jsp" />
  <div id="inner-wrap">
    <jsp:include page="SideBar.jsp" />
    <div id="main">
      <h2>&nbsp;</h2>
      <p></p>
      <form action="" method="post" name="form1" id="form1">
        <p>年度: 
          <select name="niandu" id="niandu">
            <option value="98">98</option>
            <option value="99">99</option>
            <option value="100">100</option>
            <option value="101">101</option>
            <option value="102">102</option>
            <option value="103">103</option>
            <option value="104">104</option>
          </select>
        </p>
        <p>標題： 
          <input name="title" type="text" value="${job.title}" size="50" />
        </p>
        <p>&nbsp;</p>
        <p>開放投稿日期：(格式: %Y-%m-%d %H:%M:%S)</p>
        <input name="starttime" type="text" value="${job.starttime}" size="30" />
        <p>結束日期：<br />
          <input name="finishtime" type="text" value="${job.finishtime}" size="30" />
        </p>
        <p>&nbsp;</p>
        <p>
          <input name="id" type="hidden" id="id" value="${job.id}" />
          <input type="submit" name="Submit" value="送出" />
        </p>
      </form>
      <br />
      <br />
      <br />
      <br />
	<h1>所有投稿作業：</h1>
      <c:forEach var="job" items="${jobs}">
        <h2><a href="./ShowJob?id=${job.id}">${job.niandu} 學年度 -- ${job.title}</a>
		<c:if test="${job.starttime.time > now.time}"> - 準備進行！</c:if>
		</h2>
      </c:forEach>
	  
      <br />
      <br />
    </div>
  </div>
  <jsp:include page="Footer.jsp" />
</div>
</body>
</html>
