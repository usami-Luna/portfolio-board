<%@page import="shop.BoardSiteSession"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規ユーザ登録</title>
<link href="css/login.css" rel="stylesheet">
</head>
<%
	//このjspファイルで、新規ユーザ登録ための、
	//　ユーザID、パスワードをキー入力する画面を表示します。■■■
	// セッション情報からユーザ等の情報を取得する
	BoardSiteSession boardSiteSession = (BoardSiteSession)session.getAttribute("boardSiteSession");
	if (boardSiteSession == null) {
		boardSiteSession = new BoardSiteSession();
	    // 作ったユーザ等のオブジェクトを、セッション情報に登録する
	    session.setAttribute("boardSiteSession", boardSiteSession);
	}
%>
<body>
<!-- %@ include file="Title.jsp" % -->

<div class="auth-form-header">
        <p padding-bottom="30px"><img alt="" src="img/logo.png" width="48"></p>

        <h1>会員様向け掲示板</h1>
</div>

<div class="form-wrapper" style="max-width: 900px">

  <h2>新規ユーザ登録画面</h2>
  <p>ユーザ登録されていない方は、下記のユーザID、
  パスワード、name、Email Addressをキー入力後、「登録」ボタンを押下して、ユーザ登録をしてください。</p>

  <form action="UserRegistrationProcessServlet" method="POST">
    <div class="form-item">
      <label for="email">User ID</label>
      <input type="text" name="userID" required="required"></input>
    </div>
    <div class="form-item">
      <label for="password">Password</label>
      <input type="text" name="password" required="required"></input>
    </div>
    <div class="form-item">
      <label for="name">name （無記名OK）</label>
      <input type="text" name="name"></input>
    </div>
    <div class="form-item">
      <label for="email">Email Address （無記名OK）</label>
      <input type="text" name="mailaddress"></input>
    </div>
    <div class="button-panel" style="width: 250px; margin: 2em auto auto;">
      <input type="submit" class="button" title="Sign In" value="新規登録"></input>
    </div>
  </form>
  <div class="form-footer">
	<p>ここで、「ユーザ登録」作業を中断するときは、
下記をクリックしてください。</p>
	<p><A href='Login.jsp'>ログインページに戻る</A></p>
  </div>
</div>


<!-- h2>新規ユーザ登録画面</h2>
ユーザ登録されていない方は、<br>
　　下記のユーザID、パスワードをキー入力後、<br>
　　「登録」ボタンを押下して、<br>
　　ユーザ登録をしてください。<br>
　なお、user01, pass01<br>
　　　　user02, pass02<br>
　　　　user03, pass03は既に登録済みです。<br>
<form action="UserRegistrationProcessServlet" method="POST">
ユーザID<input type="text" name="userID" size=20 maxlength="18" /><br>
パスワード<input type="text" name="password"  size=20 maxlength="18" /><br>
名前<input type="text" name="name"  size=20 maxlength="18" />未記入OK<br>
メールアドレス<input type="text" name="mailaddress"  size=20 maxlength="18" />未記入OK<br>
<input type="submit" value="登録" />
</form>
<br><br><br>
ここで、「ユーザ登録」作業を中断するときは、<br>
下記をクリックしてください。<BR>
<A href='<%= boardSiteSession.getBackPageURL() %>'>ログインページに戻る</A>
<hr-->
<!-- %@ include file="Return.jsp" % -->
</body>
</html>
