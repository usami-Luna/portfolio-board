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
	request.setAttribute("user", user);
	//


	List<Board> boards = (List<Board>)request.getAttribute("list");
	int bordlist;

	for (int i = 0; i < boards.size(); i++) {
		System.out.println(boards.get(i).getUserID());
		System.out.println(boards.get(i).getContent());
		System.out.println(boards.get(i).getBi());
	}
//	session.setAttribute("bordlist", bordlist);

	try {
			bordlist = Integer.parseInt(request.getParameter("bordlist"));
	}
	catch (NumberFormatException e) {
	    bordlist = 0;
	}

	boardSiteSession.setCno(bordlist);

	String getTitle = (String)request.getAttribute("getTitle");

	Boardtype boardtype = (Boardtype)request.getAttribute("boardtype");

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
<li><a href="UserRegistrationProcess.jsp">ユーザー情報</a></li>
<li><a href="Login.jsp">ログアウト</a></li>
</ul>
</nav>





<div class="form-wrapper" style="max-width: 900px">
<h2><%= boardtype.getTitle() %> 掲示板</h2>


<h3><%= boardtype.getDetails() %> ( <%= boardtype.getUserID() %> )</h3>

<p><%--= bordlist --%><br><%--= boardSiteSession.getCno() --%></p>

<p>こんにちは！！<%=boardSiteSession.getUserID()%>様</p>

<form method="POST" action="insert">
	<input type="hidden" name="blist" value="<%= bordlist %>"> <!--  -->
    <!-- label for="name">名前</label><br />
    <input type="text" name="name"/>
    <br /><br /> -->
    <label for="content">メッセージ</label><br />
    <textarea name="content" class="area" ></textarea>
    <br /><br />
    <button type="submit">投稿</button>
  </form>
<%-- session.setAttribute("boardSiteSession", boardSiteSession); --%>


<br><br>




<%
	for(Board boards1  : boards) {
%>
<table BORDER="1"  CELLSPACING="1" CELLPADDING="8" BORDERCOLOR="#006699" WIDTH="100%">
<tr><td BGCOLOR="#ffffff">
<p>投稿者 <b><%= boards1.getUserID() %></b> さん　<font SIZE="2"> .... <%= boards1.getBi() %></font></p>
<pre><p><%= boards1.getContent() %></p>
</pre></td></tr>
</table>

<%
    }
%>




<br /><br />

<%

	out.println(" " + user);

%>

</div>

</div>

</body>
</html>