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
<script type="text/javascript">
	jQuery(document).ready(function() {
		$("#type").change(function() { //事件發生
			//alert("======");
			$('#form1').submit();
		});
	});
</script>
</head>
<body>
	<div id="wrapper">
		<jsp:include page="include/Header.jsp" />
		<div id="inner-wrap">
			<jsp:include page="include/SideBar.jsp" />
			<div id="main">
				<h1>
					<%-- ${job.niandu} 學年度 --  --%>${job.title}</h1>
				<p>${job.comment}</p>
				<p>投稿開始日期： ${job.starttime}</p>
				<p>投稿結束日期： ${job.finishtime}</p>
				<br />
				<form id="form1" name="form1" method="get" action="">
					<input type="text" style="display: none" name="id"
						value="${job.id}" /> 選擇： <select name="type" id="type">
						<option value="">請選擇組別...</option>
						<option value="">全部</option>
						<option value="高中新詩">高中新詩</option>
						<option value="高中散文">高中散文</option>
						<option value="高中小說">高中小說</option>
						<option value="國中新詩">國中新詩</option>
						<option value="國中散文">國中散文</option>
						<option value="國中小說">國中小說</option>
					</select>
				</form>
				<br /> <br />
				<table style="width: 90%;">
					<tr>
						<th width="10%">編號</th>
						<th width="60%">題目</th>
						<th width="15%">分類</th>
						<th width="15%">操作</th>
					</tr>
					<c:forEach var="article" items="${articles}" varStatus="varstatus">
						<tr>
							<td width="5%" title="${article.id}">${fn:length(articles)-varstatus.count+1}</td>
							<td width="10%" style="vertical-align: top"><strong>${article.id}:
									${article.title}</strong></td>
							<td width="12%"><span style="font-size: x-small">${article.type}</span></td>
							<td width="12%"><a href="DeleteArticle.api?id=${article.id}">刪除</a></td>
						</tr>
						<tr>
							<td width="5%" style="vertical-align: top">&nbsp;</td>
							<td style="vertical-align: top;" colspan="5"><a
								href="mailto:${article.email}">${article.nianji}
									${article.classname}: ${article.author}</a><br /> <span
								style="color: #555555;"> 簡介：<br /> ${article.comment}<br />
									<br /> <c:forEach var="upfile" items="${article.upfiles}">
              
            檔案：<a href="./Download?articleid=${article.id}">${upfile.filename}</a>
										<span style="font-size: 9px">(${article.postdate})</span>
										<br />
									</c:forEach> <c:if test="${article.id<1410}">            
            檔案：<a href="./Download?articleid=${article.id}">${article.filename}</a>
										<span style="font-size: 9px">(${article.postdate})</span>
										<br />
									</c:if></span> <br /> <br /></td>
						</tr>
					</c:forEach>
				</table>
				<br /> <br /> <br /> <br /> <br /> <br />
			</div>
		</div>
		<jsp:include page="include/Footer.jsp" />
	</div>
</body>
</html>
