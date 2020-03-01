package shop;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import mypack.Boardtype;
import mypack.DataBase;
import mypack.ProcessErrorException;
import mypack.User;
import mypack.UserDAO;
///////////////////////////////////////
//  本インスタンスが、Sessionオブジェクトに登録されます。★★★
///////////////////////////////////////
public class BoardSiteSession {
    private Map<Integer, Integer>  cart_books =
            new LinkedHashMap<Integer, Integer>();  //①
    private String userID = null;  //②
    private String userName = null;
    private String backPageURL;  //③
    private int cno = 0;

    //下記に書き換える？
	private User user = null;
	private Boardtype boardtype =null;

    //上記の①、②、③について、以下で説明します。
    /*
    ①について：
      「問題5-session-ショッピングサイト問題その１ .docx」の解答
     cartList.jspでは、
        //カートオブジェクトの作成
		List<Shoseki> cartList = new ArrayList<Shoseki>();
	    // 作ったカートオブジェクトを、セッション情報に登録する
	    session.setAttribute("cartList", cartList);
     と、ローカル変数 cartList を セッション・オブジェクトに
     cartList という名前で、登録していました。
     ここでは、上記の様に cartList.jspで登録しません。代わりに、
     本クラスで、上記①のようなフィールド
            private Map<Integer, Integer>  cart_books =  ・・・;
     を定義します。
     このように、本課題で使用する「「カート（買い物籠）」は、
     上記の   Map<Integer, Integer>  で定義される
     コレクションです。このコレクションに、
     購入したい書籍の
       ・書籍番号(sno)をキーとして登録し、
       ・購入冊数を値としてMapに登録します。
	 例：購入書籍が
		・sno： 2 を 3 冊購入
		・sno： 1 を 2 冊購入
		のとき、上記カート用コレクション①に以下のように設定します。
			cart_books.put(2,  3); //sno： 2 を 3 冊購入します。
			cart_books.put(1,  2); //sno： 1 を 2 冊購入します。

	注：上記①の右辺の「LinkedHashMap」について
		(1)Javaテキストでは、HashMapを使用していました。
			この場合、拡張forループでキーの全要素を取得するとき、
			要素を追加(put)した順番ではありません。
		(2)ところが、HashMapの代わりに、LinkedHashMapを使用すると、
			拡張forループでキーの全要素を取得するとき、
			要素を追加(put)した順番で取得します。

    ②について：
    　以下のmainメソッドのリストと実行結果を見た後、
    　下記の「boolean isSignOn()」メソッドのソースリストを見てください。
    　なお、
    　(1) Javaの一般的な規約としてboolean型のフィールド、
    　　たとえば、「boolean SignOn」が存在したとき
    　　（戻り値が boolean） のゲッターメソッド名は、
           getSignOn() ではなく、
           isSignOn()  という名前が一般的な名前です。
        このため、Eclipseでゲッターを自動生成したときも
           isSignOn()  という名前のメソッドが生成されます。★★
      (2)一方、以下では
           public boolean isSignOn()
         というメソッドは存在しますが、
         フィールド「boolean SignOn」は存在しません。
      (3)上記②のフィールドString userID を定義する代わりに、
              private User user;
        を定義する方法もありますが、
     　 　・一般的にUserクラスには、住所など多数の項目が格納されることを
     　　　　考えると、実行時のメモリ量が大きくなる可能性があること。
     　 　・セッションオブジェクトに登録するものを、
     　 　　　できるだけ少ないくしたいこと。
        から、ここでは、上記userフィールドを定義しません。
        最小限必要な　userIDだけをフィールドで定義します。

    ③について：
        「TopServlet.java」が起動し→forward→「Top.jsp」で
        「Top.jsp」のページが表示され、そのページの
        「ログイン（ユーザ登録）」ボタンを押下すると、
        　・ログイン処理
        を行います。そのログイン処理が完了するためには
        複数のページが必要になる時があります。
        　　・このログイン処理の途中
        または、
        　　・ログイン処理が終了した後
        「ログイン（ユーザ登録）」ボタンを押下した「Top.jsp」ページに「戻る」ために、
        ログイン処理を行う複数のページに、「戻る」ための「アンカータグ」を
        定義しますが、その「アンカータグ」に設定するURLを、
        本フィールドである
             String backPageURL  に
        予め、代入しておきます。■■■■■■■■■■■■
        shoppingSiteSession.setBackPageURL("ListBookServlet?bno=" + bno);

          このbackPageURLフィールドの扱いは少々わかりずらいです。★★★
        このbackPageURLフィールドについては、
        問題文「問題5-session-ショッピングサイト問題その２.docx」の
            補足１  を参照してください。
        また、ListBookServlet.javaファイルの
          「shoppingSiteSession.setBackPageURL("・・・・");」
          の部分のコメントを参照してください。
    */

    public BoardSiteSession() {
    }

    public Map<Integer, Integer> getCart_books() {
        return cart_books;
    }
    public void setCart_books(Map<Integer, Integer> cart_books) {
        this.cart_books = cart_books;
    }


    public String getUserID() {
        return userID;
    }
    //以下のセッターがないことに注意してください。
    //代わりに、「setSignOn(String userID)」という
    //メソッドを以降で定義しています。★
//    public void setUserID(String userID) {
//        this.userID = userID;
//    }




    // signOn というフィールドは存在しません★
    //ログイン済みかを調べます
    //true：ログイン済み
    //false：ログインしていない状態
	public boolean isSignOn() {
	    if(userID != null) return true;
            return false;
	}

	//注意★★★★
	// signOff というフィールドは存在しません★
	//ログインしていない状態にします。
	public void setSignOff() {
        userID = null;;
    }

    //注意★★★★
    // signOn というフィールドは存在しません★
	//引数のユーザIDでログインします。
	//引数のユーザIDが登録済みかどうかはここでは、調べません。
	//ユーザIDが登録済みかどうかはUserDAOクラスのメソッドで
	//調べます。
	//なお、引数に null が設定されたときは、
	//ProcessErrorExceptionの例外が発生します。

	//useridとuserNameをsetします。
    public void setSignOn(String userID) throws ProcessErrorException {
        if(userID == null) {
            throw new ProcessErrorException("ShoppingSiteSession:setSignOn " +
                    "　引数が不正です（nullが指定されました）。 文字列を指定してください。");
        } else {
            this.userID = userID;
        }

        try ( DataBase database = new DataBase();  ) {
            UserDAO userDAO = database.getUserDAO();

            User user = new User();
            user = userDAO.getUser(userID);

            this.userName = user.getName();

	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

    }


    // signOn というフィールドは存在しませんので
    //以下のセッターも存在しません。★
//	public void setSignOn(boolean signOn) {
//		SignOn = signOn;
//	}

	public String getUserName() {
		return userName;
	}




	public int getCno() {
		return cno;
	}

	public void setCno(int cno) {
		this.cno = cno;
	}

	public String getBackPageURL() {
	    if(backPageURL == null) {
	        return "TopServlet";
	    } else {
	        return backPageURL;
	    }

    }
    public void setBackPageURL(String backPageURL) {
        this.backPageURL = backPageURL;
    }


    public static void main(String[] args) {
        try {
            BoardSiteSession boardSiteSession =
                    new BoardSiteSession();

            //(1)ユーザ user01 でログインします。
            System.out.println("(1)ユーザ user01 でログインします。");
            boardSiteSession.setSignOn("user01");

            //(2)本ショッピンッグサイトにおいて、
            //ログイン済みかを調べます。
            //このとき、ログインユーザ名はどんなユーザ名でもよく
            //ログインが完了しているかどうかだけを調べます。
            System.out.println("(2)ログイン済みか？" +
            		boardSiteSession.isSignOn());

            //(3)上記でログイン済みと判明したので、
            //ログインユーザ名を取得します。
            System.out.println("(3)ログインユーザ名を取得します。" +
            		boardSiteSession.getUserID() + boardSiteSession.getUserName());

            //(4)ログイン済みの状態を
            //ログインしていない状態にします。
            System.out.println("(4)ログインしていない状態にします。");
            boardSiteSession.setSignOff();

            //(5)上記(2)のログインが完了しているかどうかだけを調べます。
            System.out.println("(5)ログイン済みか？" +
            		boardSiteSession.isSignOn());

            //(6)ユーザID  null でログインします。
            //  null  のとき
            //ProcessErrorExceptionの例外が発生します。
            System.out.println("(6)ユーザID null でログインします。");
            boardSiteSession.setSignOn( null);
        } catch (ProcessErrorException e) {
            e.printStackTrace();
        }
    }
}

/* 実行結果
(1)ユーザ user01 でログインします。
(2)ログイン済みか？true
(3)ログインユーザ名を取得します。user01
(4)ログインしていない状態にします。
(5)ログイン済みか？false
(6)ユーザID null でログインします。
mypack.ProcessErrorException: ShoppingSiteSession:setSignOn 　引数が不正です（nullが指定されました）。 文字列を指定してください。
    at shop.ShoppingSiteSession.setSignOn(ShoppingSiteSession.java:127)
    at shop.ShoppingSiteSession.main(ShoppingSiteSession.java:182)

*/
