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
import mypack.ProcessErrorException;
import mypack.User;
import mypack.UserDAO;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SignUpServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        String resultPage = "/ErrorPage.jsp"; //forward先jsp名

        // セッション情報からカートの情報を取得する
        HttpSession session = request.getSession();
        BoardSiteSession boardSiteSession  = (BoardSiteSession)session.getAttribute("boardSiteSession");
        // カート情報がない場合は、新たに作る
        if (boardSiteSession == null) {
        	boardSiteSession = new BoardSiteSession();
            // 作ったカートオブジェクトを、セッション情報に登録する
            session.setAttribute("boardSiteSession", boardSiteSession);
        }

        request.setCharacterEncoding( "UTF-8" );
        String userID = request.getParameter( "userID" );
        String password = request.getParameter( "password" );

        //ログイン処理を行うためにユーザがキー入力した
        //ユーザID、パスワードをもとに、
        //そのユーザIDが登録されているかを調べ
        //ログイン成功したかどうかを調べます。

        boolean signOn = false;
        //DB接続する。
        try (DataBase database = new DataBase(); ){
            UserDAO userDAO = database.getUserDAO();

            //クエリ文字列で指定されたユーザIDが登録されているかを調べます。
            signOn = userDAO.userCertify(userID, password);
            //上記のsignOnが
            //(1)true ：ログイン成功
            //    ログイン処理成功、つまり
            //　　　キー入力された、ユーザIDがすでに存在し、
            //　　　パスワードが正しいとき、この状態となります。
            //(2)false：ログイン失敗
            //    ログイン処理失敗、つまり
            //　　　キー入力された、ユーザIDが存在しないか、
            //　　　ユーザIDが存在するが、パスワードが正しくないとき、
            //　　この状態となります。

            //ログイン成功したか？
            int error = 1;
            if(signOn ) {
            	boardSiteSession.setSignOn(userID);  //①
            	resultPage = "/UserRegistrationProcess.jsp"; //forward先jsp名

            	boolean addUser_success = false;
            	User user =new User();
//            	UserDAO userDAO = new UserDAO();
            	user = userDAO.getUser(userID);
            	request.setAttribute("user", user);

                request.setAttribute("addUser_success", addUser_success);
            } else {
                //ログイン処理失敗ですので、
                //ログイン未処理状態にします。
                //これにより、
                //ログインされていない状態となります。★
            	boardSiteSession.setSignOff();
            	error = 1;
            	resultPage = "/SignUp.jsp"; //forward先jsp名
            	request.setAttribute("error", error);
            }

        } catch (ProcessErrorException e) { //上記①で発生する可能性があります。
            request.setAttribute("exception", e);
            resultPage = "/ErrorPage.jsp";
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
