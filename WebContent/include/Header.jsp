<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>

<div id="header">
	<div id="logo">
		<a href="./"><img src="./images/eshineTITLE.png" /></a>
	</div>
	<jsp:include page="SystemNow.jsp" />
	<div id="navigation">
		<c:if test="${sessionScope.onlineUser!=null}"> | ${sessionScope.onlineUser.account } <a
				href="./Logout">登出</a> | </c:if>
	</div>
</div>
