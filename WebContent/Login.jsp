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
      <div id="loginbox">
        <p style="font-size:large; font-weight:bold; color:red;">${sessionScope.LoginMessage}</p>
        <form id="form1" name="form1" method="post" action="Login">
          <p>身份：
            <input type="text" name="account" />
          </p>
          <p>密碼：
            <input type="password" name="passwd" />
          </p>
          <p>
            <input type="submit" name="Submit" value="送出" />
          </p>
        </form>
      </div>
    </div>
  </div>
  <jsp:include page="Footer.jsp" />
</div>
</body>
</html>
