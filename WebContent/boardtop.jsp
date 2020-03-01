<%@page import="mypack.User"%>
<%@page import="mypack.Boardtype"%>
<%@page import="shop.BoardSiteSession"%>
<%@page import="mypack.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	//セッション情報からユーザ等の情報を取得する
	BoardSiteSession boardSiteSession = (BoardSiteSession)session.getAttribute("boardSiteSession");
	if (boardSiteSession == null) {
		boardSiteSession = new BoardSiteSession();
	    // 作ったユーザ等のオブジェクトを、セッション情報に登録する
	    session.setAttribute("boardSiteSession", boardSiteSession);
	}
	User user = (User)request.getAttribute("user");
	if (user == null) {
		user = new User();
	    // 作ったユーザ等のオブジェクトを、セッション情報に登録する
		request.setAttribute("user", user);
	}


	//


	List<Boardtype> boardtype = (List<Boardtype>)request.getAttribute("list");


	for (int i = 0; i < boardtype.size(); i++) {
		System.out.println(boardtype.get(i).getCno());
		System.out.println(boardtype.get(i).getTitle());
		System.out.println(boardtype.get(i).getDetails());
		System.out.println(boardtype.get(i).getUserID());
	}
%>
<html>
<head>
<meta charset="UTF-8">
<title>会員様向け掲示板</title>
<!-- link href="css/login.css" rel="stylesheet"-->
<link rel="stylesheet" href="css/style.css">
<script type="text/javascript" src="js/openclose.js"></script>
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>

<div id="container">

<header>
<h1 id="logo"><a href="ListProcessServlet"><img src="img/logo.png" alt="Simple Site" max-width="50px"> 会員様向け掲示板</a></h1>
</header>

<nav id="menubar">
<ul>
<li><a href="ListProcessServlet">掲示板Top</a></li>
<!-- li><a href="UserRegistrationProcessServlet">ユーザー情報</a></li -->
<li><a href="UserRegistrationProcess.jsp">ユーザー情報</a></li>
<li><a href="Login.jsp">ログアウト</a></li>
</ul>
</nav>

<h2>掲示板TOP</h2>

<p>こんにちは！！<%=boardSiteSession.getUserID()%>様</p>



<table class="ta1">
<tr>
<th class="tamidashi" width="10%">No.</th><th class="tamidashi" width="30%">タイトル</th><th class="tamidashi" width="40%">詳細</th><th class="tamidashi" width="20%">スレ主</th>
</tr>
<%
	for(Boardtype boardtype1  : boardtype) {
%>
<tr>
<th><%= boardtype1.getCno() %></th>
<td>
	<form action="toppage?bordlist=<%= boardtype1.getCno() %>" method="POST">
		<input type="submit" value="<%= boardtype1.getTitle() %>" />
	</form>
</td>
<td><%= boardtype1.getDetails() %></td>
<td><%= boardtype1.getUserID() %></td>
</tr>

<%
    }
%>
</table>

<%



	out.println(" " + user);

%>

<h3 style="margin-top:80px;">新規スレッド</h3>
<p style="">新規でスレッド立てたい方は下記へ追加してください。</p>

<form method="POST" action="BoardListInsert">
    <!-- label for="name">名前</label><br />
    <input type="text" name="name"/>
    <br /><br /> -->
    <label for="content">タイトル</label><br />
    <textarea name="title"  ></textarea><br />
    <label for="content">詳細</label><br />
    <textarea name="details" class="area" ></textarea>
    <br /><br />
    <button type="submit">投稿</button>
  </form>
<% session.setAttribute("boardSiteSession", boardSiteSession); %>

</div>

</body>
</html>