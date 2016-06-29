<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<jsp:include page="CommonHead.jsp" />

<script type="text/javascript" src="./jscripts/jquery-1.2.6.min.js"></script>
<script type="text/javascript" src="jscripts/js_date.js"></script>
<script type="text/javascript"
	src="jscripts/jquery.timeout.interval.idle.js"></script>
<script language="javascript">
jQuery(document).ready(function(){
   mytime(parseInt(${now.time}) );
});

function mytime(nowtime){
   var nowdate = new Date();
   nowdate.setTime(nowtime);
   jQuery("#now").text( formatDate( nowdate, "y-MM-dd HH:mm:ss") );
   jQuery.interval(
   function(){
   var nowdate = new Date();
   nowtime = nowtime + 1000;
 //  alert("nowtime="+nowtime);
   nowdate.setTime(nowtime);
   jQuery("#now").text( formatDate( nowdate, "y-MM-dd HH:mm:ss") );
   },1000);
}
</script>

</head>
<jsp:useBean id="now" class="java.util.Date" />
<jsp:useBean id="articleBean" class="jiangsir.eshine.Beans.ArticleBean" />
<jsp:setProperty name="articleBean" property="id" value="0" />
<body>
	<div id="wrapper">
		<jsp:include page="Header.jsp" />
		<div id="inner-wrap">
			<jsp:include page="SideBar.jsp" />
			<div id="main">
				<h1>${job.title}</h1>
				<h2>
					請注意!! <br /> 每個人每一個組別投稿上限為 3 篇!!
				</h2>
				<h3>
					現在時間: <span id="now"></span>
				</h3>
				<p></p>
				<form action="" method="post" enctype="multipart/form-data"
					name="form1" id="form1">
					<p>
						姓名： <input name="author" type="text" id="author" /> <br /> 班級： <select
							name="grade">
							<option value="7">國一</option>
							<option value="8">國二</option>
							<option value="9">國三</option>
							<option value="10">高一</option>
							<option value="11">高二</option>
							<option value="12">高三</option>
						</select> <select name="classname">
							<option value="仁">仁</option>
							<option value="義">義</option>
							<option value="禮">禮</option>
							<option value="智">智</option>
							<option value="信">信</option>
							<option value="忠">忠</option>
							<option value="孝">孝</option>
							<option value="和">和</option>
						</select> 班
					</p>
					<p>
						投稿組別： <select name="type" id="type">
							<option value="高中新詩">高中新詩</option>
							<option value="高中散文">高中散文</option>
							<option value="高中小說">高中小說</option>
							<option value="國中新詩">國中新詩</option>
							<option value="國中散文">國中散文</option>
							<option value="國中小說">國中小說</option>
						</select>
					</p>
					<p>
						投稿題目： <input name="title" type="text" id="title" size="45" />
					</p>
					<p>
						聯絡 Email： <input name="email" type="text" id="email" size="45" />
					</p>
					<p>
						作品簡介：<br />
						<textarea name="comment" cols="50" rows="10" id="comment"></textarea>
					</p>
					<p>
						<br /> 檔案(上限: ${articleBean.maxFilesize})： <input type="file"
							name="file" />
					</p>
					<p>&nbsp;</p>
					<p>
						<input name="jobid" type="hidden" value="${job.id}" /> <input
							type="submit" name="Submit" value="送出" />
					</p>
				</form>
				<br /> <br /> <br /> <br /> <br /> <br />
			</div>
		</div>
		<jsp:include page="Footer.jsp" />
	</div>
</body>
</html>
