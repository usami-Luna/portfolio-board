<%@page import="shop.BoardSiteSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
	//このjspファイルで、登録済みのユーザ等のオブジェクトを、
	//　　無効にします。
	//このjspファイルは、「デバック」用、「プログラム開発」用で、
	//一般公開しません。■■■

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
<hr>
<h2>「ログインユーザの削除（動作確認・デバッグ用）」ボタン押下後の画面</h2>
	このjspファイルで、<br>
	　　ログイン処理をまだ行っていない状態にします。★★<br>
	このjspファイルルは、「デバック」用、「プログラム開発」用で、<br>
	一般公開しません。■■■<br>
<hr>
<!-- %@ include file="Return.jsp" % -->
</body>
</html>
