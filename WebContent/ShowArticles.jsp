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
<script type="text/javascript" src="./jscripts/jquery-1.2.6.min.js"></script>
<script language="javascript">
jQuery(document).ready(function(){
	$("#type").change(function(){ //事件發生
		//alert("======");
		$('#form1').submit();
	});
});
</script>
<body>
<div id="wrapper">
  <jsp:include page="Header.jsp" />
  <div id="inner-wrap">
    <jsp:include page="SideBar.jsp" />
    <div id="main">
      <h1>${job.title}</h1>
      <p>${job.comment}</p>
      <p>
      <form id="form1" name="form1" method="get" action="">
        選擇：
        <select name="type" id="type">
          <option value="">請選擇組別...</option>
          <option value="">全部</option>
          <option value="高中新詩">高中新詩</option>
          <option value="高中散文">高中散文</option>
          <option value="高中小說">高中小說</option>
          <option value="國中新詩">國中新詩</option>
          <option value="國中散文">國中散文</option>
          <option value="國中小說">國中小說</option>
        </select>
        </select>
      </form>
      </p>	  
	  <br />
      <table style="width:90%;">
        <tr>
          <th>編號</th>
          <th>主題</th>
          <th>類別</th>
        </tr>
        <c:forEach var="article" items="${articles}" varStatus="varstatus">
          <tr>
            <td>${fn:length(articles)-varstatus.count+1}</td>
            <td>${article.title} <span style="font-size:x-small">(${article.postdate})</span></td>
            <td>${article.type}</td>
          </tr>
        </c:forEach>
      </table>
      <br />
      <br />
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
