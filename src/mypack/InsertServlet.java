package mypack;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import shop.BoardSiteSession;


/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/insert")
@MultipartConfig(location="/tmp", maxFileSize=1048576)
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

        //
		String value[] = new String[3];
		int bordlist=0;
		String name=null;


		try ( DataBase database = new DataBase();  ) {

			//test
			// ファクトリー生成
			DiskFileItemFactory factory = new DiskFileItemFactory();
//			factory.setSizeThreshold(1426);
//			factory.setRepository(new File("C:\\temp\\file_up")); //一時的に保存する際のディレクトリ

			ServletFileUpload upload = new ServletFileUpload(factory);
//			upload.setSizeMax(20 * 1024);
//			upload.setFileSizeMax(10 * 1024);
			List items;
			try {
				items = upload.parseRequest(request);
				} catch (FileUploadException e) {
				// エラー処理
				throw new ServletException(e);
			}
			// 全フィールドに対するループ
			for (Object val : items) {
				FileItem item = (FileItem) val;
				if (item.isFormField()) {
					// type="file"以外のフィールド
//					value[0]=item.getFieldName();

//					String value2 = item.getString("utf-8");
//					System.out.println("string" + value2);
					if((item.getFieldName()).equals("blist")) {
						value[0] = item.getString("utf-8");
					} else {
						value[1] = item.getString("utf-8");

					}
//					for(int i=0;i<value.length;i++) {
//						value[i]=item.getFieldName();
//						System.out.println("確認" + value[i]);
//					}


					try {
						System.out.println(item.getString("MS932"));
					} catch (UnsupportedEncodingException e) {
						throw new ServletException(e);
					}
				}
				else if (!item.isFormField()) {
					// type="file"のフィールド
//					String fieldName = item.getFieldName();
				    String fileName = item.getName();
//				    String contentType = item.getContentType();
//				    InputStream fileContent = item.getInputStream();

				    File f = new File(fileName);
				    name = f.getName();


					try {
						item.write(new File(getServletContext().getRealPath("/upload") + "/" + name));
					} catch (Exception e) {
						// TODO 自動生成された catch ブロック
						name = null;
					}

//					try {
//						Part part = getFileName(item.getName());
//						name = this.getFileName(part);
//				        part.write(getServletContext().getRealPath("/upload") + "/" + name);
//
//
//					} catch (FileNotFoundException e) {
//						name = null;
//			    	} catch (Exception e) {
//			    		name = null;
//			        }
				}
			}
			//test[

			System.out.println("value[0] " + value[0]);
			System.out.println("value[1] " + value[1]);
			System.out.println("name " + name);

//			bordlist = Integer.parseInt(request.getParameter("blist"));
			bordlist= Integer.parseInt(value[0]);


    		BoardDAO boardDAO = database.getBoardDAO();

    		Board board = new Board();
//    		b.setName(request.getParameter("name"));
    		board.setUserID(boardSiteSession.getUserID());
//    		board.setContent(request.getParameter("content"));
    		board.setContent(value[1]);
    		board.setCno(bordlist);
    		board.setGazou(name);
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


	}
	private String getFileName(Part part) {
        String name = null;
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
                name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
                name = name.substring(name.lastIndexOf("\\") + 1);
                break;
            }
        }
        return name;
    }



}
