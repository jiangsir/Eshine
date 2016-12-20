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
<script src="InsertJob.js?${applicationScope.built }"></script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="include/Header.jsp" />
		<div id="inner-wrap">
			<jsp:include page="include/SideBar.jsp" />
			<div id="main">
				<h2>&nbsp;</h2>
				<p></p>
				<form action="" method="post" name="form1" id="form1">
					<%-- 					<p>
						年度: <select name="niandu" id="niandu">
							<option value="${niandu }">${niandu}</option>
							<option value="99">99</option>
							<option value="100">100</option>
							<option value="101">101</option>
							<option value="102">102</option>
							<option value="103">103</option>
							<option value="104">104</option>
						</select>
					</p>
 --%>
					<p>
						標題： <input name="title" type="text" value="${job.title}" size="50" />
					</p>
                    <p>
                        投稿作業說明：<br />
                        <textarea name="comment" cols="80%" rows="5" id="comment">${job.comment}</textarea>
                    </p>
					<div>
						<p>開放投稿日期：(格式: %Y-%m-%d %H:%M:%S)</p>
						<input name="starttime" type="text"
							value="<fmt:formatDate value="${job.starttime}" pattern="yyyy-MM-dd HH:mm:ss" />"
							size="30" />
					</div>
					<div style="margin-top: 1em;">
						<p>結束日期：</p>
						<input name="finishtime" type="text"
							value="<fmt:formatDate value="${job.finishtime}" pattern="yyyy-MM-dd HH:mm:ss" />"
							size="30" />
					</div>
					<br></br>
					<p>
						<input name="id" type="hidden" id="id" value="${job.id}" /> <input
							type="submit" name="Submit" value="送出" />
					</p>
				</form>
				<br /> <br /> <br /> <br />
				<h1>所有投稿作業：</h1>
				<c:forEach var="job" items="${jobs}">
					<h2>
						<a href="./ShowJob?id=${job.id}"> <%-- ${job.niandu} 學年度 -- --%>
							${job.title}
						</a>
						<c:if test="${job.starttime.time > now.time}"> - 準備進行！</c:if>
					</h2>
				</c:forEach>

				<br /> <br />
			</div>
		</div>
		<jsp:include page="include/Footer.jsp" />
	</div>
</body>
</html>
