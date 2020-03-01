<%@page import="shop.BoardSiteSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
<link href="css/login.css" rel="stylesheet">
</head>
<%
    //このjspファイルで、ユーザ登録されている方のログインのために、
    //　ユーザID、パスワードをキー入力する画面を表示します。■■■

	// セッション情報からユーザ等の情報を取得する
	BoardSiteSession boardSiteSession = (BoardSiteSession)session.getAttribute("boardSiteSession");
	if (boardSiteSession == null) {
		boardSiteSession = new BoardSiteSession();
	    // 作ったユーザ等のオブジェクトを、セッション情報に登録する
	    session.setAttribute("boardSiteSession", boardSiteSession);
	}
	//ログイン処理をまだ行っていない状態にします。★★
    boardSiteSession.setSignOff();
%>
<body>
<!-- %@ include file="Title.jsp" % -->

<div class="auth-form-header">
        <p padding-bottom="30px"><img alt="" src="img/logo.png" width="100"></p>

        <h1>会員様向け掲示板</h1>
</div>
<div class="form-wrapper">

  <h2>Sign In</h2>
  <form action="SignUpServlet" method="POST">
    <div class="form-item">
      <label for="email"></label>
      <input type="text" name="userID" required="required" placeholder="User ID"></input>
    </div>
    <div class="form-item">
      <label for="password"></label>
      <input type="text" name="password" required="required" placeholder="Password"></input>
    </div>
    <div class="button-panel">
      <input type="submit" class="button" title="Sign In" value="Sign In"></input>
    </div>
  </form>
  <div class="form-footer">
    <p><A href="UserEntry.jsp">Create an account</a></p>
    <!-- p><a href="#">Forgot password?</a></p -->
  </div>
</div>


<!--
<h2>ログイン画面</h2>
(1)ユーザ登録されている方は、<br>
　　下記のユーザID、パスワードをキー入力後、<br>
　　「ログイン」ボタンを押下してください。<br>
　なお、(a)下記のextBoxに表示されているユーザID、パスワードは<br>
　　　　　すでに登録済みですので、この状態で、「ログイン」ボタンを<br>
　　　　　押下すると、ログイン可能です。<br>
　　　　(b) user02, pass02<br>
　　　　　　user03, pass03も登録済みです。<br><br>

<form action="SignUpServlet" method="POST">
ユーザID<input type="text" name="userID" value ="user01" size=20 maxlength="18" /><br>
パスワード<input type="text" name="password" value ="pass01" size=20 maxlength="18" /><br>
<input type="submit" value="ログイン" />
</form>
<br>
(2)ユーザ登録されていない方は、<br>
　　下記の「新規ユーザ登録」を押下して、<br>
　　　「新規ユーザ登録」をしてください。<br>
<A href="UserEntry.jsp">新規ユーザ登録</A>
<br><br><br>
ここで、「ログイン」作業を中断するときは、<br>
下記をクリックしてください。<BR>

<hr-->
<!-- %@ include file="Return.jsp" % -->
</body>
</html>
