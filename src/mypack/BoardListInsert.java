package mypack;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shop.BoardSiteSession;

/**
 * Servlet implementation class BoardListInsert
 */
@WebServlet("/BoardListInsert")
public class BoardListInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListInsert() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);

		request.setCharacterEncoding("UTF-8");

		//追加情報
		// セッション情報からカートの情報を取得する
		HttpSession session = request.getSession();
        BoardSiteSession boardSiteSession  = (BoardSiteSession)session.getAttribute("boardSiteSession");
        // カート情報がない場合は、新たに作る
        if (boardSiteSession == null) {
        	boardSiteSession = new BoardSiteSession();
            // 作ったカートオブジェクトを、セッション情報に登録する
            session.setAttribute("boardSiteSession", boardSiteSession);
        }
        //ここまで

		try ( DataBase database = new DataBase();  ) {
    		BoardtypeDAO boardtypeDAO = database.getBoardtypeDAO();

    		Boardtype b = new Boardtype();
    		b.setTitle(request.getParameter("title"));
    		b.setDetails(request.getParameter("details"));
    		b.setUserID(boardSiteSession.getUserID());


    		//1件追加
    		boardtypeDAO.insertBoardtype(b);

    		//追加後トップページへリダイレクト
    	    response.sendRedirect(request.getContextPath() + "/ListProcessServlet");


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
