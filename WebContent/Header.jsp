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
	<div id="navigation">
		<c:if test="${sessionScope.session_account!=null}"> | <a
				href="./Logout">離開管理</a> | </c:if>
	</div>
</div>
<jsp:include page="include/SystemNow.jsp" />