package mypack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoardDAO {

	private Connection con;

    private BoardDAO() {
        super();
    }

    public BoardDAO(DataBase database) {
        super();
        this.con = database.getCon();
    }

  //OrderItemオブジェクトの取得（orderItemID指定）
    //指定された注文明細が見つからなかったとき　null　が返る。
//    public Board getBoard(int board) throws SQLException {
//    	Board result = null;
//        String sql = "select *" +
//                " from board  where name = ?";
//        PreparedStatement stmt = con.prepareStatement(sql);
//        // 入力パラメータに値をセットする ----
//        stmt.setName(1, board);
//        ResultSet rs = stmt.executeQuery();
//        if (rs.next()) {
//            result = makeInstanceFromRow(rs);
//        }
//        rs.close();
//        stmt.close();
//        return result;
//    }

  //オブジェクトの取得（全行）
    public List<Board> selectAllBoard() throws SQLException {
        String sql = "select * from board order by id desc";
        PreparedStatement stmt = con.prepareStatement(sql);
        List<Board> list = queryAndMakeList(stmt);
        stmt.close();
        return list;
    }
  //オブジェクトの取得（cnoごと）
    public List<Board> selectCnoBoard(int cno) throws SQLException {
        String sql = "select * from board where cno= ? order by id desc";
        PreparedStatement stmt = con.prepareStatement(sql);
     // 入力パラメータに値をセットする ----
        stmt.setInt(1, cno);

        List<Board> list = queryAndMakeList(stmt);
        stmt.close();
        return list;
    }

    private List<Board> queryAndMakeList(PreparedStatement stmt) throws SQLException {
        // SQL文を実行する
        ResultSet rs = stmt.executeQuery();
        List<Board> list = new ArrayList<Board>();
        while (rs.next()) {
        	Board result = makeInstanceFromRow(rs);
            list.add(result);
        }
        rs.close();
        return list;
    }
    private Board makeInstanceFromRow(ResultSet rs) throws SQLException {

    	Board result = new Board();
        result.setId(rs.getInt("id"));
        result.setUserID(rs.getString("userID"));
        result.setContent(rs.getString("content"));
        result.setBi(rs.getString("bi"));
        result.setCno(rs.getInt("cno"));
        result.setGazou(rs.getString("gazou"));
        return result;
    }

  //1件登録用メソッド
    public void insertBoard(Board board) throws SQLException {

    	Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
    	board.setBi(format.format(today));

    	String sql = "insert into board (userID, content, bi,cno ,gazou)  " +
                " values(?, ?, ?, ?, ?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        // 入力パラメータに値をセットする ----
        stmt.setString(1, board.getUserID());
        stmt.setString(2, board.getContent());
        stmt.setString(3, board.getBi());
        stmt.setInt(4, board.getCno());
        stmt.setString(5, board.getGazou());
        // SQL文を実行する
        stmt.executeUpdate();
        stmt.close();
	}

    public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
    	//テスト用

    	Date today = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        System.out.println(format.format(today));//[4]


//        System.out.println(sdf.format(date.toString()));
//    	System.out.println(sdf.format(cl.getTime()));


    	try ( DataBase database = new DataBase();  ) {
    		BoardDAO boardDAO = database.getBoardDAO();

    		Board board = new Board();
    		board.setUserID("adf");
    		board.setContent("コメント2");
    		board.setGazou("aa.png");

    		//1件追加
    	    boardDAO.insertBoard(board);

    		List<Board> users = boardDAO.selectAllBoard();
            System.out.println(users);

    		for (Board board1 : users) {
                System.out.println(board1);
                System.out.println(board1.getUserID());
                System.out.println(board1.getContent());
                System.out.println(board1.getBi());
            }

    		System.out.println("test");
    		System.out.println();
    		System.out.println();
    		for (int i = 0; i < users.size(); i++) {
    			System.out.println(users.get(i).getUserID
    					());
    			System.out.println(users.get(i).getContent());
    			System.out.println(users.get(i).getBi());
			}

    		System.out.println();
    		System.out.println("testdesu");
    		List<Board> users2 = boardDAO.selectCnoBoard(1);
    		for (Board board1 : users2) {
                System.out.println(board1);
                System.out.println(board1.getUserID());
                System.out.println(board1.getContent());
                System.out.println(board1.getBi());
            }


//            user.setUserID("user04");
//            user.setPassword("pass04");
//            System.out.println("\"user04\", \"pass04\" のユーザ登録は成功か？　" +
//                userDAO.addUser(user));
//
//            user.setUserID("user01");
//            System.out.println("\"user01\" のユーザ登録は成功か？　" +
//                userDAO.addUser(user));
//
//            user.setUserID("user99");
//            user.setPassword(null);
//            System.out.println("\"user99\" でパスワード null のユーザ登録は成功か？　" +
//                userDAO.addUser(user));
//
//            user.setUserID(null);
//            user.setPassword("pass99");
//            System.out.println("ユーザID  null  でパスワード \"pass99\"  の" +
//                "ユーザ登録は成功か？　" +
//                userDAO.addUser(user));
//
//            System.out.println("\"user04\"のユーザ削除処理の該当行数は（1 ならＯＫ） " +
//                userDAO.deleteUser("user04"));
//
//            System.out.println("\"user99\"のユーザ削除処理の該当行数は（0 なら該当ユーザが存在しないため\n" +
//                "該当ユーザの削除が出来なかった） " +
//                    userDAO.deleteUser("user99"));
//
//            System.out.println("------------------------------");
//            user = userDAO.getUser("user01");
//            System.out.println("userDAO.getUser(\"user01\");");
//            System.out.println(user);
//
//            user = userDAO.getUser("userXXX");
//            System.out.println("userDAO.getUser(\"userXXX\");");
//            System.out.println(user);
//
//            List<User> users = userDAO.getAllUsers();
//            System.out.println("userDAO.getAllUser();");
//            for (User user1 : users) {
//                System.out.println(user1);
//            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

	}



/////////////////////////////////////
//	// データベース接続と結果取得のための変数
//	  private Connection con;
//	  private PreparedStatement pstmt;
//	  private ResultSet rs;
//
//	  //全件取得用メソッド
//	  public List<Board> selectAllBoard() {
//	    // メソッドの結果として返すリスト
//	    List<Board> results = new ArrayList<Board>();
//
//	    try {
//	      //ドライバを読み込み、DBに接続
//	      this.getConnection();
//
//	      // Statementオブジェクトの作成
//	      pstmt = con.prepareStatement("select * from board");
//
//	      //Select文実行
//	      rs = pstmt.executeQuery();
//
//	      // 6. 結果を表示する
//	      while (rs.next()) {
//	        // 1件ずつCountryオブジェクトを生成して結果を詰める
//	        Board Board = new Board();
//	        Board.setId(rs.getInt("id"));
//	        Board.setName(rs.getString("name"));
//	        Board.setContent(rs.getString("content"));
//
//	        // リストに追加
//	        results.add(Board);
//	      }
//	    } catch (SQLException e) {
//	      e.printStackTrace();
//	    } catch (ClassNotFoundException e) {
//	      e.printStackTrace();
//	    } finally {
//	      this.close();
//	    }
//
//	    return results;
//	  }
//
//	  //1件登録用メソッド
//	  public void insertBoard(Board board) {
//
//	    try {
//	      //ドライバを読み込み、DBに接続
//	      this.getConnection();
//
//	      // Statementオブジェクトの作成
//	      pstmt = con.prepareStatement("insert into board(name,content) values(?,?)");
//
//	      pstmt.setString(1, board.getName());
//	      pstmt.setString(2, board.getContent());
//
//
//
//	      //Select文実行
//	      pstmt.executeUpdate();
//
//
//
//	    } catch (SQLException e) {
//	      e.printStackTrace();
//	    } catch (ClassNotFoundException e) {
//	      e.printStackTrace();
//	    } finally {
//	      this.close();
//	    }
//	  }
//
//
//
//	  public void getConnection() throws SQLException, ClassNotFoundException {
//	    //ドライバクラス読込
//	    Class.forName("com.mysql.jdbc.Driver");
//
//	    // DBと接続
//	    con = DriverManager.getConnection("jdbc:mysql://localhost/sample?useSSL=false&characterEncoding=utf8", "ユーザ", "パスワード");
//	    // ※ユーザとパスワードはMySQLに設定済みのものを記載してください。
//	  }
//
//	  private void close() {
//	    // 接続を閉じる
//	    if (rs != null) {
//	      try {
//	        rs.close();
//	      } catch (SQLException e) {
//	        e.printStackTrace();
//	      }
//	    }
//	    if (pstmt != null) {
//	      try {
//	        pstmt.close();
//	      } catch (SQLException e) {
//	        e.printStackTrace();
//	      }
//	    }
//	    if (con != null) {
//	      try {
//	        con.close();
//	      } catch (SQLException e) {
//	        e.printStackTrace();
//	      }
//	    }
//	  }




}
