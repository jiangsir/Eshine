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

<script type="text/javascript" src="jscripts/js_date.js"></script>
<script type="text/javascript"
	src="jscripts/jquery.timeout.interval.idle.js"></script>
<script language="javascript">
jQuery(document).ready(function(){
   var stoptime = parseInt(${job.finishtime.time});
   var nowtime = parseInt(${now.time});
   countdown( stoptime-nowtime );
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

function countdown(countdown){
   var secs = (countdown-(countdown%1000))/1000;
   var mins = (secs-(secs%60))/60;
   var hours = (mins-(mins%60))/60;
   var days = (hours-(hours%24))/24;
   var str = days+" 天 " + hours%24 + " 小時 " + mins%60 + " 分鐘 " + secs%60 + " 秒";
   jQuery("#countdown").text( str );
   jQuery.interval(
   function(){
   countdown = countdown - 1000;
   var secs = (countdown-(countdown%1000))/1000;
   var mins = (secs-(secs%60))/60;
   var hours = (mins-(mins%60))/60;
   var days = (hours-(hours%24))/24;
   var str = days+" 天 " + hours%24 + " 小時 " + mins%60 + " 分鐘 " + secs%60 + " 秒";
   jQuery("#countdown").text( str );
   },1000);
}

</script>

</head>
<jsp:useBean id="now" class="java.util.Date" />
<body>
	<div id="wrapper">
		<jsp:include page="Header.jsp" />
		<div id="inner-wrap">
			<jsp:include page="SideBar.jsp" />
			<div id="main">
				<c:choose>
					<c:when test="${job.id==0}">
						<h1>目前沒有任何投稿作業！</h1>
					</c:when>
					<c:otherwise>
						<h1>${job.title}</h1>
						<p>${job.comment}</p>
						<h3>
							現在時間: <span id="now"></span>
						</h3>
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
		<jsp:include page="Footer.jsp" />
	</div>
</body>
</html>
