<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Exception exception = (Exception)request.getAttribute("exception");
%>
<html>
<head>
<meta charset="UTF-8">
<title>ErrorPage.jsp</title>
</head>
<body>
<!-- %@ include file="Title.jsp" % -->
<hr>
■エラー<br>
<%= exception %>
<% exception.printStackTrace(); %>
<br><br>
<hr>
<!-- %@ include file="Return.jsp" % -->
</body>
</html>
