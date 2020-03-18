<%@page import="mypack.User"%>
<%@page import="shop.BoardSiteSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン処理結果</title>
<link href="css/login.css" rel="stylesheet">
</head>
<%
    //このjspファイルで、ログイン処理を行います。■■■

	// セッション情報からユーザ等の情報を取得する
	BoardSiteSession boardSiteSession = (BoardSiteSession)session.getAttribute("boardSiteSession");
	if (boardSiteSession == null) {
		boardSiteSession = new BoardSiteSession();
	    // 作ったユーザ等のオブジェクトを、セッション情報に登録する
	    session.setAttribute("boardSiteSession", boardSiteSession);
	}

	int error = (int)request.getAttribute("error");
%>
<body>
<!-- %@ include file="Title.jsp" % -->
<div class="auth-form-header">
        <p padding-bottom="30px"><img alt="" src="img/logo.png" width="100"></p>

        <h1>会員様向け掲示板</h1>
</div>
<div class="form-wrapper">
<h2>ログイン結果画面</h2>
<%
    if(error == 1) {

%>

<p>ログインに失敗しました。<br>
　・キー入力された ユーザIDが登録されていないか？<br>
　・キー入力されたパスワードが間違っています。<br>
再度、ログインしなおしてください。</p>
<p><A href='Login.jsp'>ログインページジに戻る</A></p>

<%
    } else if(error == 2){
        //ログイン失敗
%>
<p>いらっしゃいませ<Br>
ユーザ登録に失敗しました。<br>
既に同じ名前が登録されています。<br>
再度、新規ユーザ登録をしなおすため、<br>
以下の「ユーザ登録ページに戻る」ボタンを押下してください。<br><br>
<A href='UserEntry.jsp'>ユーザ登録ページに戻る</A>
</p>

</div>
<%
    } else if(error == 3){
        //ユーザー名変更失敗
%>
<p>
ユーザ変更に失敗しました。<br>
お手数ですが再度、ログイン画面より登録しなおしてください。
<br><br>
<A href='Login.jsp'>ユーザ登録ページに戻る</A>
</p>

</div>
<%
    } else {
        //ユーザー名変更失敗
%>
<p>
ログインに失敗しました。<br>
お手数ですが再度、ログイン画面より登録しなおしてください。
<br><br>
<A href='Login.jsp'>ユーザ登録ページに戻る</A>
</p>

</div>
<%
    }
%>


<!--%
	User user = (User)request.getAttribute("user");

      out.println("(1)Userテーブルに追加された行");
      out.println(" " + user);

      boolean addUser_success = (Boolean)request.getAttribute("addUser_success");
      out.println(" " + addUser_success);
--%>
<!-- %@ include file="Return.jsp" % -->
</body>
</html>
