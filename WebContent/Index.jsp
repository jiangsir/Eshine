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
<jsp:useBean id="now" class="java.util.Date" />
<body>
	<div id="wrapper">
		<jsp:include page="include/Header.jsp" />
		<div id="inner-wrap">
			<jsp:include page="include/SideBar.jsp" />
			<div id="main">
				<c:choose>
					<c:when test="${job.id==0}">
						<h1>目前沒有任何投稿作業！</h1>
					</c:when>
					<c:otherwise>
						<h1>${job.title}</h1>
						<p>${job.comment}</p>
						<br />
						<br />
						<c:choose>
							<c:when test="${now.time > job.finishtime.time}">
								<p>投稿截止日期: ${job.finishtime}</p>
								<p>投稿日期已過，下次請早！</p>
							</c:when>
							<c:otherwise>
								<p>請注意!! 投稿截止日期: ${job.finishtime}</p>
								<p>
									投稿截止倒數：<span id="countdown"></span>
								</p>
								<br />
								<br />
								<p>請按右側 『我要投稿！』 進行投稿</p>
								<br />

								<p>
									若因為擁擠而上傳失敗<br /> 請直接 Email 至 <a
										href="mailto:eshine@tea.nknush.kh.edu.tw">eshine@tea.nknush.kh.edu.tw</a>
								</p>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				<br /> <br /> <br /> <br /> <br /> <br />
			</div>
		</div>
		<jsp:include page="include/Footer.jsp" />
	</div>
</body>
</html>
