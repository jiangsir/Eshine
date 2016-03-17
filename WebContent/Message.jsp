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
<link rel="stylesheet" href="style.css" type="text/css"/>
</head>
<body>
<div id="wrapper">
  <jsp:include page="Header.jsp" />
  <div id="inner-wrap">
    <jsp:include page="SideBar.jsp" />
    <div id="main">
<table width="70%" border="0" style="margin:auto; margin-top:30px;">
        <tr>
          <td scope="col" style="vertical-align:top; text-align:center; width:20%;"><img src="images/${message.type}.png" width="64" height="64">
            </th>
          <td scope="col" style="vertical-align:middle; text-align:left;"><c:forEach var="resource" items="${ResourceMessage}" varStatus="varstatus">
              <fmt:message key="${fn:trim(resource)}">
                <c:forEach var="paramitem" items="${ResourceMessage_param}" varStatus="paramcount">
                  <fmt:param value="${paramitem}" />
                </c:forEach>
              </fmt:message>
            </c:forEach>
            <h1>${message.plainTitle}</h1>
            <br />
            <c:if test="${message.plainMessage!=''}">
              <h3>${message.plainMessage}</h3>
            </c:if>
            <c:if test="${message.resourceMessage!=''}">
              <h3>${message.resourceMessage}</h3>
            </c:if>
            <p></p>
            <c:forEach var="link" items="${message.links}" varStatus="varstatus">
              <c:if test="${varstatus.count!=1}"> | </c:if>
              <a href="${link.key}">${link.value}</a> </c:forEach>
            <br />
            </th>
        </tr>
      </table>
    </div>
  </div>
  <jsp:include page="Footer.jsp" />
</div>
</body>
</html>
