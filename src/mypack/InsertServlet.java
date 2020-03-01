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
 * Servlet implementation class InsertServlet
 */
@WebServlet("/insert")
public class InsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertServlet() {
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
//		doGet(request, response);

		request.setCharacterEncoding("UTF-8");

		int bordlist;

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


			bordlist = Integer.parseInt(request.getParameter("blist"));


    		BoardDAO boardDAO = database.getBoardDAO();

    		Board board = new Board();
//    		b.setName(request.getParameter("name"));
    		board.setUserID(boardSiteSession.getUserID());
    		board.setContent(request.getParameter("content"));
    		board.setCno(bordlist);
//    		board.setCno(Integer.parseInt(request.getParameter("blist")));

    		//1件追加
    		boardDAO.insertBoard(board);

    		session.setAttribute("boardSiteSession", boardSiteSession);

    		//追加後トップページへリダイレクト
//    		request.setAttribute("bordlist", bordlist);
    	    response.sendRedirect(request.getContextPath() + "/toppage?bordlist=" + bordlist);




        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//	    Board b = new Board();
//
//	    b.setName(request.getParameter("name"));
//	    b.setContent(request.getParameter("content"));
//
//	    BoardDAO boardDAO = new BoardDAO();
//
//	    //1件追加
//	    boardDAO.insertBoard(b);


	}

}
