<%@page import="shop.BoardSiteSession"%>
<%@page import="mypack.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザ管理画面</title>
<link href="css/login.css" rel="stylesheet">
</head>
<%
	//このjspファイルで、新規ユーザ登録処理を行います。■■■

	// セッション情報からユーザ等の情報を取得する
	BoardSiteSession boardSiteSession = (BoardSiteSession)session.getAttribute("boardSiteSession");
	if (boardSiteSession == null) {
		boardSiteSession = new BoardSiteSession();
	    // 作ったユーザ等のオブジェクトを、セッション情報に登録する
	    session.setAttribute("boardSiteSession", boardSiteSession);
	}
	boolean addUser_success = (Boolean)request.getAttribute("addUser_success");
	User user = (User)request.getAttribute("user");
	if (user == null) {
		user = new User();
	    // 作ったユーザ等のオブジェクトを、セッション情報に登録する
		mypack.DataBase database =  new mypack.DataBase();

	      mypack.UserDAO userDAO = database.getUserDAO();
	      user = userDAO.getUser(boardSiteSession.getUserID());
	}

	request.setAttribute("user", user);
%>
<body>
<!-- %@ include file="Title.jsp" % -->
<hr>
<div class="auth-form-header">
        <p padding-bottom="30px"><img alt="" src="img/logo.png" width="100"></p>

        <h1>会員様向け掲示板</h1>
</div>

<%--
    //新規ユーザ登録が成功したか？
    if(addUser_success) {  //①if文
        //新規ユーザ登録が成功した時の処理を以下に記述します。
--%>

<div class="form-wrapper" style="max-width: 900px">
	<form action="UserChangeProcessServlet" method="POST">
		<h2>ユーザ管理画面</h2>
		<p>いらっしゃいませ、<%=user.getUserID()%>様</p>
		<p>変更がありましたら下記にて対応してください。<br>
		* userIDは変更できません</p>


		<div class="form-item">
			<label for="i_example">User ID</label>
			<input id="i_example" type="text" name="userID" value="<%= user.getUserID() %>" readonly ></input><!--  -->
		</div>
	    <div class="form-item">
	      <label for="password">Password</label>
	      <input type="text" name="password" required="required" placeholder="Password" value="<%= user.getPassword() %>"></input>
	    </div>
	    <div class="form-item">
	      <label for="name">name （無記名OK）</label>
	      <input type="text" name="name" placeholder="name （無記名OK）" value="<%= user.getName() %>"></input>
	    </div>
	    <div class="form-item">
	      <label for="email">Email Address （無記名OK）</label>
	      <input type="text" name="mailaddress" placeholder="Email Address （無記名OK）" value="<%= user.getMailaddress() %>"></input>
	    </div>
	    <div class="button-panel" style="width: 250px; margin: 2em auto auto;">
	      <input type="submit" class="button" title="Sign In" value="変更"></input>
	    </div>
  </form>
  <div class="form-footer">

  </div>
  <form action="UserDeleteServlet" method="POST">
	    <input type="hidden" name="userID" value="<%= user.getUserID() %>">
	    <div class="button-panel" style="width: 250px; margin: 2em auto auto;">
	      <input type="submit" class="button" title="Sign In" value="退会する"></input>
	    </div>
  </form>

  <div class="form-footer">
    <p><A href="ListProcessServlet">一覧画面へ</a></p>
    <!-- p><a href="#">Forgot password?</a></p -->
  </div>



</div>


<pre>
<%



      mypack.DataBase database =  new mypack.DataBase();

      mypack.UserDAO userDAO = database.getUserDAO();
      user = userDAO.getUser(user.getUserID());
      out.println("(1)Userテーブルに追加された行");
      out.println(" " + user);


%>
</pre>
<!-- ここでデバッグ用処理は終了します。 -->
<%--
    } else {  //上記①のif文のelse文
        //新規ユーザ登録が失敗した時の処理を以下に記述します。
--%>
<!-- div class="form-wrapper" style="max-width: 900px">
<p>いらっしゃいませ、<%= user.getUserID() %>様<Br>
ユーザ登録に失敗しました。<br>
既に同じ名前が登録されています。<br>
再度、新規ユーザ登録をしなおすため、<br>
以下の「ユーザ登録ページに戻る」ボタンを押下してください。<br><br>
<A href='UserEntry.jsp'>ユーザ登録ページに戻る</A>
</p>
</div-->
<%--
    }  //上記①のif文の閉じカッコ
--%>

<!-- %@ include file="Return.jsp" % -->
</body>
</html>
