<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="include/CommonHead.jsp" />
</head>
<body>
	<div id="wrapper">
		<jsp:include page="include/Header.jsp" />
		<div id="inner-wrap">
			<jsp:include page="include/SideBar.jsp" />
			<div id="main">
				<table style="margin: auto; margin-top: 30px; width: 70%;">
					<tr>
						<td scope="col"
							style="vertical-align: top; text-align: center; width: 20%;"><img
							src="images/${message.type}.png" width="64" height="64"></td>
						<td scope="col" style="vertical-align: middle; text-align: left;"><c:forEach
								var="resource" items="${ResourceMessage}" varStatus="varstatus">
								<fmt:message key="${fn:trim(resource)}">
									<c:forEach var="paramitem" items="${ResourceMessage_param}"
										varStatus="paramcount">
										<fmt:param value="${paramitem}" />
									</c:forEach>
								</fmt:message>
							</c:forEach>
							<h1>${message.plainTitle}</h1> <br /> <c:if
								test="${message.plainMessage!=''}">
								<h3>${message.plainMessage}</h3>
							</c:if> <c:if test="${message.resourceMessage!=''}">
								<h3>${message.resourceMessage}</h3>
							</c:if>
							<p></p> <c:forEach var="link" items="${message.links}"
								varStatus="varstatus">
								<c:if test="${varstatus.count!=1}"> | </c:if>
								<a href="${link.key}">${link.value}</a>
							</c:forEach> <br />
					</tr>
				</table>
			</div>
		</div>
		<jsp:include page="include/Footer.jsp" />
	</div>
</body>
</html>
