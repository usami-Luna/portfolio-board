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
 * Servlet implementation class ListProcessServlet
 */
@WebServlet("/ListProcessServlet")
public class ListProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListProcessServlet() {
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
		User user  = (User)session.getAttribute("user");
		// カート情報がない場合は、新たに作る
		if (boardSiteSession == null) {
		 	boardSiteSession = new BoardSiteSession();
		 	// 作ったカートオブジェクトを、セッション情報に登録する
		    session.setAttribute("boardSiteSession", boardSiteSession);
		}
		//ここまで


		try ( DataBase database = new DataBase();  ) {
    		BoardtypeDAO boardtypeDAO = database.getBoardtypeDAO();

//    		Board board = new Board();

    		List<Boardtype> list = boardtypeDAO.selectAllBoardtype();

    		request.setAttribute("list", list);
    		request.setAttribute("user", user);
    	    RequestDispatcher rd = request.getRequestDispatcher("/boardtop.jsp");
    	    rd.forward(request, response);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
