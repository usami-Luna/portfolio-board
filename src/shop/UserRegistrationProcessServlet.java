package shop;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mypack.DataBase;
import mypack.User;
import mypack.UserDAO;

@WebServlet("/UserRegistrationProcessServlet")
public class UserRegistrationProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserRegistrationProcessServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {

        String resultPage = "/ErrorPage.jsp"; //forward先jsp名

        // セッション情報からカートの情報を取得する
        HttpSession session = request.getSession();
        BoardSiteSession boardSiteSession = (BoardSiteSession)session.getAttribute("boardSiteSession");
        // カート情報がない場合は、新たに作る
        if (boardSiteSession == null) {
        	boardSiteSession = new BoardSiteSession();
            // 作ったカートオブジェクトを、セッション情報に登録する
            session.setAttribute("boardSiteSession", boardSiteSession);
        }

        request.setCharacterEncoding( "UTF-8" );
        String userID = request.getParameter( "userID" );
        String password = request.getParameter( "password" );
        String name = request.getParameter( "name" );
        String mailaddress = request.getParameter( "mailaddress" );

        boolean addUser_success = false;
        User user = null;
        //DB接続する。
        try (DataBase database = new DataBase(); ){
            //新規ユーザ登録を行うためにユーザがキー入力した
            //ユーザID、パスワードをもとに、
            //そのユーザIDが未登録かどうかを調べます。

            UserDAO userDAO = database.getUserDAO();
            //ユーザがキー入力したユーザID、パスワードなどをもとに、
            //Userインスタンスを生成します
            user = new User();
            user.setUserID( userID );
            user.setPassword( password );
            user.setName(name);
            user.setMailaddress(mailaddress);
            //ユーザ登録します。
            //以下の addUser_success が
            //　trueのとき、ユーザ登録成功
            //　falseのとき、ユーザ登録失敗
            addUser_success = userDAO.addUser(user);
            //ユーザ登録成功か？
            if(addUser_success) {
                //ログイン済みの状態にします。
            	boardSiteSession.setSignOn(userID);
            	request.setAttribute("user", user);
                request.setAttribute("addUser_success", addUser_success);
                resultPage = "/UserRegistrationProcess.jsp"; //forward先jsp名
            } else {
                //ログインされていない状態にします。
            	boardSiteSession.setSignOff();
            	resultPage = "/SignUp.jsp"; //forward先jsp名
            	int error = 2;
            	request.setAttribute("error", error);
            }


        } catch (Exception e) {
            request.setAttribute("exception", e);
            resultPage = "/ErrorPage.jsp";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
