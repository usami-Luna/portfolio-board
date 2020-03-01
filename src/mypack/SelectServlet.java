package mypack;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shop.BoardSiteSession;

/**
 * Servlet implementation class SelectServlet
 */
@WebServlet("/toppage")
public class SelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

		int bordlist;

		try ( DataBase database = new DataBase();  ) {

			try {
				bordlist = Integer.parseInt(request.getParameter("bordlist"));
			} catch (NumberFormatException e) {
			    bordlist = 0;
			}

			//
			BoardtypeDAO boardtypeDAO = database.getBoardtypeDAO();


			//test
			Boardtype boardtype = new Boardtype();
			boardtype.setCno(boardtypeDAO.getBoardtype(bordlist).getCno());
			boardtype.setTitle(boardtypeDAO.getBoardtype(bordlist).getTitle());
			boardtype.setDetails(boardtypeDAO.getBoardtype(bordlist).getDetails());
			boardtype.setUserID(boardtypeDAO.getBoardtype(bordlist).getUserID());

			request.setAttribute("boardtype", boardtype);

			//

    		BoardDAO boardDAO = database.getBoardDAO();


    		//全体を表示したい時用
//    		List<Board> list = boardDAO.selectAllBoard();
    		//タイトル事
    		List<Board> list = boardDAO.selectCnoBoard(bordlist);

    		request.setAttribute("list", list);
    		request.setAttribute("bordlist", bordlist);



    	    RequestDispatcher rd = request.getRequestDispatcher("/board.jsp");
    	    rd.forward(request, response);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//	    BoardDAO boardDAO = new BoardDAO();
//
//	    //全件取得
//	    List<Board> list = boardDAO.selectAllBoard();


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
