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
import mypack.UserDAO;

/**
 * Servlet implementation class UserDeleteServlet
 */
@WebServlet("/UserDeleteServlet")
public class UserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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


        //DB接続する。
        try (DataBase database = new DataBase(); ){
            //新規ユーザ登録を行うためにユーザがキー入力した
            //ユーザID、パスワードをもとに、
            //そのユーザIDが未登録かどうかを調べます。

            UserDAO userDAO = database.getUserDAO();


            userDAO.deleteUser(userID);

            resultPage = "/UserDelete.jsp"; //forward先jsp名




        } catch (Exception e) {
            request.setAttribute("exception", e);
            resultPage = "/ErrorPage.jsp";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
